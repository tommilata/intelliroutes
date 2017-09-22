package com.github.tomasmilata.intelliroutes

import com.intellij.lexer.FlexAdapter
import com.github.tomasmilata.intelliroutes.RoutesLexer

import java.io.Reader

class RoutesLexerAdapter : FlexAdapter(RoutesLexer(null as Reader?))
