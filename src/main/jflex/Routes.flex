package com.github.tomasmilata.intelliroutes;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.tomasmilata.intelliroutes.psi.RoutesTypes.*;

%%

%class RoutesLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

EOL=[\r\n]
WHITE_SPACE=\s+
COMMENT="#"[^\r\n]*
VERB=[A-Z]+
PATH=[^\ \r\n]+
CALL=[^\s\r\n][^\r\n]*

%state WAITING_PATH
%state WAITING_CALL

%%

<YYINITIAL> {
  {EOL}              { return EOL; }
  {WHITE_SPACE}      { return WHITE_SPACE; }
  {COMMENT}          { return COMMENT; }
  {VERB}             { yybegin(WAITING_PATH); return VERB; }
}

<WAITING_PATH> {
  {WHITE_SPACE}      { return WHITE_SPACE; }
  {PATH}             { yybegin(WAITING_CALL); return PATH; }
}

<WAITING_CALL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }
  {CALL}             { yybegin(YYINITIAL); return CALL; }
}

.                    { return BAD_CHARACTER; }
