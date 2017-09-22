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
        return if (tokenType == RoutesTypes.VERB) {
            VERB_KEYS
        } else if (tokenType == RoutesTypes.PATH) {
            PATH_KEYS
        } else if (tokenType == RoutesTypes.CALL) {
            CALL_KEYS
        } else if (tokenType == RoutesTypes.COMMENT) {
            COMMENT_KEYS
        } else if (tokenType == TokenType.BAD_CHARACTER) {
            BAD_CHAR_KEYS
        } else {
            EMPTY_KEYS
        }
    }

    companion object {
        val VERB = createTextAttributesKey("PLAY_ROUTES_VERB", DefaultLanguageHighlighterColors.KEYWORD)
        val PATH = createTextAttributesKey("PLAY_ROUTES_PATH", DefaultLanguageHighlighterColors.STRING)
        val CALL = createTextAttributesKey("PLAY_ROUTES_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL)
        val COMMENT = createTextAttributesKey("PLAY_ROUTES_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BAD_CHARACTER = createTextAttributesKey("PLAY_ROUTES_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val VERB_KEYS = arrayOf(VERB)
        private val PATH_KEYS = arrayOf(PATH)
        private val CALL_KEYS = arrayOf(CALL)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}