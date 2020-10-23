package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.*
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

class RoutesSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return RoutesLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            RoutesTypes.VERB -> VERB_KEYS
            RoutesTypes.ARROW -> ARROW_KEYS
            RoutesTypes.PLUS -> PLUS_KEYS
            RoutesTypes.MODIFIER -> MODIFIER_KEYS
            RoutesTypes.STATIC_PATH_SEGMENT -> STATIC_PATH_SEGMENT_KEYS
            RoutesTypes.PATH_PARAMETER -> PATH_PARAMETER_KEYS
            RoutesTypes.PATH_REGEX_PARAM -> PATH_REGEX_PARAM_KEYS
            RoutesTypes.WILDCARD_PARAMETER -> WILDCARD_PARAMETER_KEYS
            RoutesTypes.CONTROLLER_METHOD -> CONTROLLER_METHOD_KEYS
            RoutesTypes.ARGUMENT_NAME -> ARGUMENT_NAME_KEYS
            RoutesTypes.ARGUMENT_VALUE -> ARGUMENT_VALUE_KEYS
            RoutesTypes.COMMENT -> COMMENT_KEYS
            RoutesTypes.ROUTER_REFERENCE -> ROUTER_REFERENCE_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }

    companion object {
        val VERB = createTextAttributesKey("PLAY_ROUTES_VERB", DefaultLanguageHighlighterColors.KEYWORD)
        val ARROW = createTextAttributesKey("PLAY_ROUTES_ARROW", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val PLUS = createTextAttributesKey("PLAY_ROUTES_PLUS", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val MODIFIER = createTextAttributesKey("PLAY_ROUTES_MODIFIER", DefaultLanguageHighlighterColors.METADATA)
        val STATIC_PATH_SEGMENT = createTextAttributesKey("PLAY_ROUTES_STATIC_PATH_SEGMENT", DefaultLanguageHighlighterColors.STRING)
        val PATH_PARAMETER = createTextAttributesKey("PLAY_ROUTES_PATH_PARAMETER", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val PATH_REGEX_PARAM = createTextAttributesKey("PLAY_ROUTES_PATH_REGEX_PARAM", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR)
        val WILDCARD_PARAMETER = createTextAttributesKey("PLAY_ROUTES_WILDCARD_PARAMETER", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val CONTROLLER_METHOD = createTextAttributesKey("PLAY_ROUTES_CONTROLLER_METHOD", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val ARGUMENT_NAME = createTextAttributesKey("PLAY_ROUTES_ARGUMENT_NAME", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val ARGUMENT_VALUE = createTextAttributesKey("PLAY_ROUTES_ARGUMENT_VALUE", DefaultLanguageHighlighterColors.STRING)
        val COMMENT = createTextAttributesKey("PLAY_ROUTES_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val ROUTER_REFERENCE = createTextAttributesKey("PLAY_ROUTES_ROUTER_REFERENCE", DefaultLanguageHighlighterColors.CLASS_REFERENCE)
        val BAD_CHARACTER = createTextAttributesKey("PLAY_ROUTES_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val VERB_KEYS = arrayOf(VERB)
        private val ARROW_KEYS = arrayOf(ARROW)
        private val PLUS_KEYS = arrayOf(PLUS)
        private val MODIFIER_KEYS = arrayOf(MODIFIER)
        private val STATIC_PATH_SEGMENT_KEYS = arrayOf(STATIC_PATH_SEGMENT)
        private val PATH_PARAMETER_KEYS = arrayOf(PATH_PARAMETER)
        private val PATH_REGEX_PARAM_KEYS = arrayOf(PATH_REGEX_PARAM)
        private val WILDCARD_PARAMETER_KEYS = arrayOf(WILDCARD_PARAMETER)
        private val CONTROLLER_METHOD_KEYS = arrayOf(CONTROLLER_METHOD)
        private val ARGUMENT_NAME_KEYS = arrayOf(ARGUMENT_NAME)
        private val ARGUMENT_VALUE_KEYS = arrayOf(ARGUMENT_VALUE)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val ROUTER_REFERENCE_KEYS = arrayOf(ROUTER_REFERENCE)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}