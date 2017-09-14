package com.github.tomasmilata.intelliroutes.psi;

import com.intellij.psi.tree.IElementType;
import com.github.tomasmilata.intelliroutes.RoutesLanguage;
import org.jetbrains.annotations.*;

public class RoutesTokenType extends IElementType {
  public RoutesTokenType(@NotNull @NonNls String debugName) {
    super(debugName, RoutesLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return "RoutesTokenType." + super.toString();
  }
}