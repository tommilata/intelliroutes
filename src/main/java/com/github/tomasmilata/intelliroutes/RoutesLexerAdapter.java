package com.github.tomasmilata.intelliroutes;

import com.intellij.lexer.FlexAdapter;
import com.github.tomasmilata.intelliroutes.RoutesLexer;

import java.io.Reader;

public class RoutesLexerAdapter extends FlexAdapter {
  public RoutesLexerAdapter() {
    super(new RoutesLexer((Reader) null));
  }
}
