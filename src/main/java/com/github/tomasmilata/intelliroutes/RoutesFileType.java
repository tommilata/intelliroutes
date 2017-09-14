package com.github.tomasmilata.intelliroutes;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.*;

import javax.swing.*;

public class RoutesFileType extends LanguageFileType {
  public static final RoutesFileType INSTANCE = new RoutesFileType();

  private RoutesFileType() {
    super(RoutesLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "Routes file";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Play framework routes file";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "simple";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return RoutesIcons.FILE;
  }
}