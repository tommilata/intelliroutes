package com.github.tomasmilata.intelliroutes

import com.intellij.lexer.FlexAdapter

import java.io.Reader

class RoutesLexerAdapter : FlexAdapter(RoutesLexer(null as Reader?))
