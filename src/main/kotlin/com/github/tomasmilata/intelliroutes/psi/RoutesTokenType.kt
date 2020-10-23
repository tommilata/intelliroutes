package com.github.tomasmilata.intelliroutes.psi

import com.github.tomasmilata.intelliroutes.RoutesLanguage
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class RoutesTokenType(@NonNls debugName: String) : IElementType(debugName, RoutesLanguage.INSTANCE)