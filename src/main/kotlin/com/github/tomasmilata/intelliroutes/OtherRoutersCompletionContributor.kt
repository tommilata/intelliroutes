package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext


class OtherRoutersCompletionContributor : CompletionContributor() {
    init {
        val elementPattern = PlatformPatterns.psiElement(RoutesTypes.ROUTER_REFERENCE)
                .withLanguage(RoutesLanguage.INSTANCE)
        extend(CompletionType.BASIC, elementPattern, otherRoutersCompletionProvider)
    }

    companion object {
        private val otherRoutersCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        ProjectFileIndex.routesFiles(project).forEach {
                            val routerName = it.name.replace("\\.routes$".toRegex(), ".Routes")
                            val suggestion = LookupElementBuilder.create(routerName)
                            resultSet.addElement(suggestion)
                        }

                    }
                }

    }
}
