package com.github.tomasmilata.intelliroutes.psi;

import com.intellij.psi.tree.IElementType;
import com.github.tomasmilata.intelliroutes.RoutesLanguage;
import org.jetbrains.annotations.*;

public class RoutesElementType extends IElementType {
  public RoutesElementType(@NotNull @NonNls String debugName) {
    super(debugName, RoutesLanguage.INSTANCE);
  }
}