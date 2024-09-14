//package com.github.tomasmilata.intelliroutes
//
//import com.intellij.testFramework.ParsingTestCase
//
//
//class Simple: BaseTestCase() {
//    fun testSimple() {
//        doTest(true)
//    }
//}
//
//class PathParam: BaseTestCase() {
//    fun testPathParam() {
//        doTest(true)
//    }
//}
//
//class SubRouter: BaseTestCase() {
//    fun testSubRouter() {
//        doTest(true)
//    }
//}
//
//class Modifier: BaseTestCase() {
//    fun testModifier() {
//        doTest(true)
//    }
//}
//
//class MultipleModifiers: BaseTestCase() {
//    fun testMultipleModifiers() {
//        doTest(true)
//    }
//}
//
//abstract class BaseTestCase : ParsingTestCase("", "routes", RoutesParserDefinition()) {
//
//    override fun getTestDataPath(): String {
//        return "src/test/resources/parser-tests"
//    }
//
//    override fun includeRanges(): Boolean {
//        return true
//    }
//}