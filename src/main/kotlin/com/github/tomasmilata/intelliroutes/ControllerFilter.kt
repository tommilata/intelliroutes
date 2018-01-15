package com.github.tomasmilata.intelliroutes

object ControllerFilter {

    fun filterByClassPrefix(classSuggestion: String, entered: String): Boolean {
        val enteredSegments = entered.trimEnd('.').split('.')
        val suggestionPackageAndClassSegments = classSuggestion.split('.')

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