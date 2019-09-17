package com.github.tomasmilata.intelliroutes

import com.intellij.testFramework.ParsingTestCase


class Simple: BaseTestCase() {
    fun testSimple() {
        doTest(true)
    }
}

class PathParam: BaseTestCase() {
    fun testPathParam() {
        doTest(true)
    }
}

abstract class BaseTestCase : ParsingTestCase("", "routes", RoutesParserDefinition()) {

    override fun getTestDataPath(): String {
        return "src/test/resources/parser-tests"
    }

    override fun includeRanges(): Boolean {
        return true
    }
}