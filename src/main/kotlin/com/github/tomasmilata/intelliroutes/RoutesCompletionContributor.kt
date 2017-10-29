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
                httpVerbCompletionProvider
        )
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.CONTROLLER_METHOD).withLanguage(RoutesLanguage.INSTANCE),
                controllerMethodCompletionProvider
        )
    }

    companion object {
        private val httpVerbs =
                listOf("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS")
                        .map { verb ->
                            LookupElementBuilder.create(verb)
                        }
        private val httpVerbCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        resultSet.addAllElements(httpVerbs)
                    }
                }

        private val controllerMethodCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        val searchScope = ProjectScope.getContentScope(project)
                        val query = AllClassesSearch.search(searchScope, project)

                        query
                            .flatMap { cls -> cls.methods.toList() }
                            .forEach { method ->
                                val returnType = PsiTypesUtil.getPsiClass(method.returnType)
                                if (isInheritor(returnType, "play.api.mvc.Action")) {
                                    val containingClass = method.containingClass
                                    if (containingClass != null) {
                                        val lookupElement = LookupElementBuilder.create("${containingClass.qualifiedName}.${method.name}")
                                        resultSet.addElement(lookupElement)
                                    }
                                }
                            }

                    }
                }

    }
}
