package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.search.searches.AllClassesSearch
import com.intellij.psi.util.InheritanceUtil.isInheritor
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.util.ProcessingContext


class RoutesCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.VERB).withLanguage(RoutesLanguage.INSTANCE),
                httpVerbsCompletionProvider
        )
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.CALL).withLanguage(RoutesLanguage.INSTANCE),
                callsCompletionProvider
        )
    }

    companion object {
        private val httpVerbs =
                listOf("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS")
                        .map { verb ->
                            LookupElementBuilder.create(verb)
                        }
        private val httpVerbsCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        resultSet.addAllElements(httpVerbs)
                    }
                }

        private val callsCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        val searchScope = ProjectScope.getContentScope(project)
                        val query = AllClassesSearch.search(searchScope, project)

                        val methods = query
                                .flatMap { cls -> cls.methods.toList() }
                                .filter { method ->
                                    val returnType = PsiTypesUtil.getPsiClass(method.returnType)
                                    isInheritor(returnType, "play.api.mvc.Action")
                                }

                        val methodLookupElements = methods.mapNotNull { method ->
                            val containingClass = method.containingClass
                            if (containingClass != null) {
                                LookupElementBuilder.create("${containingClass.qualifiedName}.${method.name}")
                            } else null
                        }

                        resultSet.addAllElements(methodLookupElements)
                    }
                }

    }
}
