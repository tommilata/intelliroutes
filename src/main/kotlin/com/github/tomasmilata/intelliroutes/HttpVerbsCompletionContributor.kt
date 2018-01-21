package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext


class HttpVerbsCompletionContributor : CompletionContributor() {

    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.VERB)
                        .withLanguage(RoutesLanguage.INSTANCE),
                httpVerbCompletionProvider
        )
    }

    companion object {
        private val httpVerbs =
                listOf("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS")
                        .map { LookupElementBuilder.create(it) }

        private val httpVerbCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        resultSet.addAllElements(httpVerbs)
                    }
                }

    }
}
