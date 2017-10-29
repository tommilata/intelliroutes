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
STATIC_PATH_SEGMENT=[^\s\r\n\/:*]+
PATH_PARAMETER=:[^\s\r\n\/]+
WILDCARD_PARAMETER=\*[^\s\r\n\/]+
CONTROLLER_METHOD=[^\s\r\n()][^\r\n()]*
ARGUMENT_NAME=[^\s\r\n,():=][^\r\n,():=]*
ARGUMENT_TYPE=[^\s\r\n,():=][^\r\n,():=]*
ARGUMENT_VALUE=[^\s\r\n,():=][^\r\n,():=]*


%state WAITING_PATH
%state PATH
%state WAITING_CONTROLLER_METHOD
%state WAITING_ARGUMENTS
%state WAITING_ARGUMENT_NAME
%state WAITING_ARGUMENT_TYPE
%state WAITING_ARGUMENT_VALUE

%%

<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }
  {COMMENT}           { return COMMENT; }
  {VERB}              { yybegin(WAITING_PATH); return VERB; }
}

<WAITING_PATH> {
  {WHITE_SPACE}      { yybegin(PATH); return WHITE_SPACE; }
}

<PATH> {
  \/    { return SLASH; }
  {STATIC_PATH_SEGMENT} { return STATIC_PATH_SEGMENT;}
  {PATH_PARAMETER} { return PATH_PARAMETER;}
  {WILDCARD_PARAMETER} { return WILDCARD_PARAMETER;}
  {WHITE_SPACE}       { yybegin(WAITING_CONTROLLER_METHOD); return WHITE_SPACE; }
}

<WAITING_CONTROLLER_METHOD> {
  {WHITE_SPACE}      { return WHITE_SPACE; }
  {CONTROLLER_METHOD} { yybegin(WAITING_ARGUMENTS); return CONTROLLER_METHOD; }
}

<WAITING_ARGUMENTS> {
  {WHITE_SPACE}      { return WHITE_SPACE; }
  \(                 { yybegin(WAITING_ARGUMENT_NAME); return OPENING_PARENTHESIS; }
  .                  { yybegin(YYINITIAL); }
}

<WAITING_ARGUMENT_NAME> {
  {WHITE_SPACE}      { return WHITE_SPACE; }
  {ARGUMENT_NAME}         { return ARGUMENT_NAME; }
  ,                  { return COMMA; }
  :                 { yybegin(WAITING_ARGUMENT_TYPE); return COLON; }
  =                 { yybegin(WAITING_ARGUMENT_VALUE); return EQ; }
  \)                 { yybegin(YYINITIAL); return CLOSING_PARENTHESIS; }
}

<WAITING_ARGUMENT_TYPE> {
    {WHITE_SPACE}      { return WHITE_SPACE; }
    {ARGUMENT_TYPE}         { yybegin(WAITING_ARGUMENT_NAME); return ARGUMENT_TYPE; }
}

<WAITING_ARGUMENT_VALUE> {
    {WHITE_SPACE}      { return WHITE_SPACE; }
    {ARGUMENT_VALUE}         { yybegin(WAITING_ARGUMENT_NAME); return ARGUMENT_VALUE; }
}

{EOL}               { return EOL; }
.                    { return BAD_CHARACTER; }
