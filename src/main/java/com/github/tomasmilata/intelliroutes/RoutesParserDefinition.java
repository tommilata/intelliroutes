package com.github.tomasmilata.intelliroutes;

import com.github.tomasmilata.intelliroutes.psi.RoutesFile;
import com.github.tomasmilata.intelliroutes.psi.RoutesTypes;
import com.intellij.lang.*;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.*;
import com.github.tomasmilata.intelliroutes.parser.RoutesParser;
import org.jetbrains.annotations.NotNull;

public class RoutesParserDefinition implements ParserDefinition {
  public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
  public static final TokenSet COMMENTS = TokenSet.create(RoutesTypes.COMMENT);

  public static final IFileElementType FILE = new IFileElementType(RoutesLanguage.INSTANCE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new RoutesLexerAdapter();
  }

  @NotNull
  public TokenSet getWhitespaceTokens() {
    return WHITE_SPACES;
  }

  @NotNull
  public TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  public TokenSet getStringLiteralElements() {
    return TokenSet.EMPTY;
  }

  @NotNull
  public PsiParser createParser(final Project project) {
    return new RoutesParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return FILE;
  }

  public PsiFile createFile(FileViewProvider viewProvider) {
    return new RoutesFile(viewProvider);
  }

  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    return SpaceRequirements.MAY;
  }

  @NotNull
  public PsiElement createElement(ASTNode node) {
    return RoutesTypes.Factory.createElement(node);
  }
}