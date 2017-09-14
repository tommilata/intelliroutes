package com.github.tomasmilata.intelliroutes.psi;

import com.github.tomasmilata.intelliroutes.RoutesFileType;
import com.github.tomasmilata.intelliroutes.RoutesLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class RoutesFile extends PsiFileBase {
  public RoutesFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, RoutesLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return RoutesFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return "Routes File";
  }

  @Override
  public Icon getIcon(int flags) {
    return super.getIcon(flags);
  }
}