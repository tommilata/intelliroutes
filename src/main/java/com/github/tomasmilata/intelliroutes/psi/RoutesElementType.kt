package com.github.tomasmilata.intelliroutes.psi

import com.intellij.psi.tree.IElementType
import com.github.tomasmilata.intelliroutes.RoutesLanguage
import org.jetbrains.annotations.*

class RoutesElementType(@NonNls debugName: String) : IElementType(debugName, RoutesLanguage.INSTANCE)