package com.github.tomasmilata.intelliroutes.psi

import com.github.tomasmilata.intelliroutes.RoutesLanguage
import com.github.tomasmilata.intelliroutes.psi.RoutesTypes.*
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class RoutesTokenType(@NonNls debugName: String) : IElementType(debugName, RoutesLanguage.INSTANCE) {

    override fun toString(): String {
        return when (this) {
            COLON -> ":"
            COMMA -> ","
            OPENING_PARENTHESIS -> "("
            CLOSING_PARENTHESIS -> ")"
            ARROW -> "->"
            ROUTER_REFERENCE -> "router reference"
            ARGUMENT_NAME -> "argument name"
            ARGUMENT_TYPE -> "argument type"
            ARGUMENT_VALUE -> "argument value"
            COMMENT -> "comment"
            CONTROLLER_METHOD -> "controller method"
            EOL -> "new line"
            ARGUMENT_EQUAL -> "="
            PATH_PARAMETER -> "path parameter"
            PATH_REGEX_PARAM -> "path regex parameter"
            ROUTER_REFERENCE -> "router reference"
            SLASH -> "/"
            STATIC_PATH_SEGMENT -> "static path segment"
            VERB -> "HTTP verb"
            WILDCARD_PARAMETER -> "wildcard parameter"
            else -> super.toString()
        }
    }
}