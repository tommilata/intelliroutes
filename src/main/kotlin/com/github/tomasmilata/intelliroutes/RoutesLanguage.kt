package com.github.tomasmilata.intelliroutes

import com.intellij.lang.Language

class RoutesLanguage private constructor() : Language("Routes") {
    companion object {
        val INSTANCE = RoutesLanguage()
    }
}