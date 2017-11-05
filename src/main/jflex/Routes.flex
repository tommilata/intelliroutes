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

EOL=[\r\n]+
WHITE_SPACE=[ \t\x0B\f]+
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
%state WAITING_EOL

%%

<YYINITIAL> {
    {COMMENT}       { return COMMENT; }
    {VERB}          { yybegin(WAITING_PATH); return VERB; }
    {EOL}           { return EOL; }
    {WHITE_SPACE}   { return WHITE_SPACE; }
}

<WAITING_PATH> {
    {EOL}           { yybegin(YYINITIAL); return BAD_CHARACTER; }
    {WHITE_SPACE}   { yybegin(PATH); return WHITE_SPACE; }
}

<PATH> {
    \/                    { return SLASH; }
    {STATIC_PATH_SEGMENT} { return STATIC_PATH_SEGMENT;}
    {PATH_PARAMETER}      { return PATH_PARAMETER;}
    {WILDCARD_PARAMETER}  { return WILDCARD_PARAMETER;}
    {EOL}                 { yybegin(YYINITIAL); return BAD_CHARACTER; }
    {WHITE_SPACE}         { yybegin(WAITING_CONTROLLER_METHOD); return WHITE_SPACE; }
}

<WAITING_CONTROLLER_METHOD> {
    {CONTROLLER_METHOD}   { yybegin(WAITING_ARGUMENTS); return CONTROLLER_METHOD; }
    {EOL}                 { yybegin(YYINITIAL); return BAD_CHARACTER; }
    {WHITE_SPACE}         { return WHITE_SPACE; }
}

<WAITING_ARGUMENTS> {
    \(              { yybegin(WAITING_ARGUMENT_NAME); return OPENING_PARENTHESIS; }
    {EOL}           { yybegin(YYINITIAL); return EOL; }
    {WHITE_SPACE}   { return WHITE_SPACE; }
}

<WAITING_ARGUMENT_NAME> {
    {ARGUMENT_NAME} { return ARGUMENT_NAME; }
    ,               { return COMMA; }
    :               { yybegin(WAITING_ARGUMENT_TYPE); return COLON; }
    =               { yybegin(WAITING_ARGUMENT_VALUE); return EQ; }
    \)              { yybegin(WAITING_EOL); return CLOSING_PARENTHESIS; }
    {EOL}           { yybegin(YYINITIAL); return BAD_CHARACTER; }
    {WHITE_SPACE}   { return WHITE_SPACE; }
}

<WAITING_ARGUMENT_TYPE> {
    {ARGUMENT_TYPE} { yybegin(WAITING_ARGUMENT_NAME); return ARGUMENT_TYPE; }
    {EOL}           { yybegin(YYINITIAL); return BAD_CHARACTER; }
    {WHITE_SPACE}   { return WHITE_SPACE; }
}

<WAITING_ARGUMENT_VALUE> {
    {ARGUMENT_VALUE}    { yybegin(WAITING_ARGUMENT_NAME); return ARGUMENT_VALUE; }
    {EOL}               { yybegin(YYINITIAL); return BAD_CHARACTER; }
    {WHITE_SPACE}       { return WHITE_SPACE; }
}

<WAITING_EOL> {
    {EOL}           { yybegin(YYINITIAL); return EOL; }
    {WHITE_SPACE}   { return WHITE_SPACE; }
}

.   { return BAD_CHARACTER; }
