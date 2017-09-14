package com.github.tomasmilata.intelliroutes;

import com.intellij.lang.Language;

public class RoutesLanguage extends Language {
  public static final RoutesLanguage INSTANCE = new RoutesLanguage();

  private RoutesLanguage() {
    super("Routes");
  }
}