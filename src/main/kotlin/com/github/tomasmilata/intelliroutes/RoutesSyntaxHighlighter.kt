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
            RoutesTypes.STATIC_PATH_SEGMENT -> STATIC_PATH_SEGMENT_KEYS
            RoutesTypes.PATH_PARAMETER -> PATH_PARAMETER_KEYS
            RoutesTypes.PATH_REGEX_PARAM -> PATH_REGEX_PARAM_KEYS
            RoutesTypes.WILDCARD_PARAMETER -> WILDCARD_PARAMETER_KEYS
            RoutesTypes.ARGUMENT_NAME -> ARGUMENT_NAME_KEYS
            RoutesTypes.ARGUMENT_VALUE -> ARGUMENT_VALUE_KEYS
            RoutesTypes.COMMENT -> COMMENT_KEYS
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            else -> EMPTY_KEYS
        }
    }

    companion object {
        val VERB = createTextAttributesKey("PLAY_ROUTES_VERB", DefaultLanguageHighlighterColors.KEYWORD)
        val ARROW = createTextAttributesKey("PLAY_ROUTES_ARROW", DefaultLanguageHighlighterColors.KEYWORD)
        val STATIC_PATH_SEGMENT = createTextAttributesKey("PLAY_ROUTES_STATIC_PATH_SEGMENT", DefaultLanguageHighlighterColors.STRING)
        val PATH_PARAMETER = createTextAttributesKey("PLAY_ROUTES_PATH_PARAMETER", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val PATH_REGEX_PARAM = createTextAttributesKey("PLAY_ROUTES_PATH_REGEX_PARAM", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val WILDCARD_PARAMETER = createTextAttributesKey("PLAY_ROUTES_WILDCARD_PARAMETER", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val ARGUMENT_NAME = createTextAttributesKey("PLAY_ROUTES_ARGUMENT_NAME", DefaultLanguageHighlighterColors.STATIC_FIELD)
        val ARGUMENT_VALUE = createTextAttributesKey("PLAY_ROUTES_ARGUMENT_VALUE", DefaultLanguageHighlighterColors.STRING)
        val COMMENT = createTextAttributesKey("PLAY_ROUTES_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = createTextAttributesKey("PLAY_ROUTES_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val VERB_KEYS = arrayOf(VERB)
        private val ARROW_KEYS = arrayOf(ARROW)
        private val STATIC_PATH_SEGMENT_KEYS = arrayOf(STATIC_PATH_SEGMENT)
        private val PATH_PARAMETER_KEYS = arrayOf(PATH_PARAMETER)
        private val PATH_REGEX_PARAM_KEYS = arrayOf(PATH_REGEX_PARAM)
        private val WILDCARD_PARAMETER_KEYS = arrayOf(WILDCARD_PARAMETER)
        private val ARGUMENT_NAME_KEYS = arrayOf(ARGUMENT_NAME)
        private val ARGUMENT_VALUE_KEYS = arrayOf(ARGUMENT_VALUE)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}