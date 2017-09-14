package com.github.tomasmilata.intelliroutes;

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class RoutesSyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey VERB =
      createTextAttributesKey("PLAY_ROUTES_VERB", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey PATH =
      createTextAttributesKey("PLAY_ROUTES_PATH", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey CALL =
      createTextAttributesKey("PLAY_ROUTES_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL);
  public static final TextAttributesKey COMMENT =
      createTextAttributesKey("PLAY_ROUTES_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BAD_CHARACTER =
      createTextAttributesKey("PLAY_ROUTES_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
  private static final TextAttributesKey[] VERB_KEYS = new TextAttributesKey[]{VERB};
  private static final TextAttributesKey[] PATH_KEYS = new TextAttributesKey[]{PATH};
  private static final TextAttributesKey[] CALL_KEYS = new TextAttributesKey[]{CALL};
  private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new RoutesLexerAdapter();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(RoutesTypes.VERB)) {
      return VERB_KEYS;
    } else if (tokenType.equals(RoutesTypes.PATH)) {
      return PATH_KEYS;
    } else if (tokenType.equals(RoutesTypes.CALL)) {
      return CALL_KEYS;
    } else if (tokenType.equals(RoutesTypes.COMMENT)) {
      return COMMENT_KEYS;
    } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    } else {
      return EMPTY_KEYS;
    }
  }
}