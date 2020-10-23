package com.github.tomasmilata.intelliroutes.psi

import com.github.tomasmilata.intelliroutes.RoutesLanguage
import com.github.tomasmilata.intelliroutes.psi.RoutesTypes.*
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class RoutesTokenType(@NonNls debugName: String) : IElementType(debugName, RoutesLanguage.INSTANCE) {

    companion object {
        const val CONTROLLER_METHOD_STR: String = "controller method"
        const val ROUTER_REFERENCE_STR: String = "router reference"
    }

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
            CONTROLLER_METHOD -> CONTROLLER_METHOD_STR
            EOL -> "new line"
            ARGUMENT_EQUAL -> "="
            PATH_PARAMETER -> "path parameter"
            PATH_REGEX_PARAM -> "path regex parameter"
            SLASH -> "/"
            STATIC_PATH_SEGMENT -> "static path segment"
            VERB -> "HTTP verb"
            WILDCARD_PARAMETER -> "wildcard parameter"
            else -> super.toString()
        }
    }

    fun isControllerMethod(): Boolean {
        return this.toString() == CONTROLLER_METHOD_STR
    }

    fun isRouterReference(): Boolean {
        return this.toString() == ROUTER_REFERENCE_STR
    }
}