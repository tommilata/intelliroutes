package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.ControllerFilter.filterByClassPrefix
import com.intellij.psi.PsiClass
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.mock.mock
import io.kotlintest.mock.`when`
import io.kotlintest.properties.*
import io.kotlintest.specs.StringSpec


class ControllerFilterTest : StringSpec() {

    private fun controllerClassMock(): PsiClass {
        val controllerClassMock = mock<PsiClass>()
        `when`(controllerClassMock.qualifiedName).thenReturn("foo.bar.Controller")
        return controllerClassMock
    }

    init {
        "filter should return true for matching prefixes" {
            val prefixes = table(
                    headers("prefix"),
                    row("foo"),
                    row("foo."),
                    row("foo.b"),
                    row("foo.bar"),
                    row("foo.bar."),
                    row("foo.bar.C"),
                    row("foo.bar.Controller"),
                    row("foo.bar.Controller."),
                    row("foo.bar.Controller.method")
            )
            forAll(prefixes) {
                filterByClassPrefix(controllerClassMock(), it) shouldEqual true
            }
        }

        "filter should  return false for non-matching prefixes" {
            val prefixes = table(
                    headers("prefix"),
                    row("xf"),
                    row("foo.Xb"),
                    row("foo.barX"),
                    row("foo.bar.X"),
                    row("foo.bar.ControllerX")
            )
            forAll(prefixes) {
                filterByClassPrefix(controllerClassMock(), it) shouldEqual false
            }
        }

        "filter should return false if qualifiedName returns null" {
            val controllerClassMock = mock<PsiClass>()
            `when`(controllerClassMock.qualifiedName).thenReturn(null)
            filterByClassPrefix(controllerClassMock, "not used") shouldEqual false
        }
    }
}