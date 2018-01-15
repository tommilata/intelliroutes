package com.github.tomasmilata.intelliroutes

import com.intellij.psi.PsiClass

object ControllerFilter {

    fun filterByClassPrefix(classSuggestion: PsiClass, entered: String): Boolean {
        val className = classSuggestion.qualifiedName ?: return false
        val enteredSegments = entered.trimEnd('.').split('.')
        val suggestionPackageAndClassSegments = className.split('.')

        val prefixLength = minOf(
                suggestionPackageAndClassSegments.size,
                enteredSegments.size
        )

        val suggestionPrefix = suggestionPackageAndClassSegments.take(prefixLength)

        val suggestionExceptLast = suggestionPrefix.dropLast(1)
        val enteredPrefixExceptLast = enteredSegments.take(suggestionExceptLast.size)

        return (suggestionExceptLast == enteredPrefixExceptLast
                && suggestionPrefix[prefixLength - 1].startsWith(enteredSegments[prefixLength - 1]))
    }
}