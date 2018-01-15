package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.ControllerFilter.filterByClassPrefix
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.properties.*
import io.kotlintest.specs.StringSpec


class ControllerFilterTest : StringSpec() {

    init {
        "filter return true for matching prefixes" {
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
            forAll(prefixes) { prefix ->
                filterByClassPrefix("foo.bar.Controller", prefix) shouldEqual true
            }
        }

        "filter return false for non-matching prefixes" {
            val prefixes = table(
                    headers("prefix"),
                    row("xf"),
                    row("foo.Xb"),
                    row("foo.barX"),
                    row("foo.bar.X"),
                    row("foo.bar.ControllerX")
            )
            forAll(prefixes) { prefix ->
                filterByClassPrefix("foo.bar.Controller", prefix) shouldEqual false
            }
        }
    }
}