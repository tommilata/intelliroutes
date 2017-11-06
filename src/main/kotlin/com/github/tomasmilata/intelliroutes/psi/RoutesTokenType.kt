package com.github.tomasmilata.intelliroutes.psi

import com.intellij.psi.tree.IElementType
import com.github.tomasmilata.intelliroutes.RoutesLanguage
import com.github.tomasmilata.intelliroutes.psi.RoutesTypes.*
import org.jetbrains.annotations.*

class RoutesTokenType(@NonNls debugName: String) : IElementType(debugName, RoutesLanguage.INSTANCE) {

    override fun toString(): String {
        return when (this) {
            COLON -> ":"
            COMMA -> ","
            OPENING_PARENTHESIS -> "("
            CLOSING_PARENTHESIS -> ")"
            ARGUMENT_NAME -> "argument name"
            ARGUMENT_TYPE -> "argument type"
            ARGUMENT_VALUE -> "argument value"
            COMMENT -> "comment"
            CONTROLLER_METHOD -> "controller method"
            EOL -> "new line"
            EQ -> "="
            PATH_PARAMETER -> "path parameter"
            SLASH -> "/"
            STATIC_PATH_SEGMENT -> "static path segment"
            VERB -> "HTTP verb"
            WILDCARD_PARAMETER -> "wildcard parameter"
            else -> super.toString()
        }
    }
}