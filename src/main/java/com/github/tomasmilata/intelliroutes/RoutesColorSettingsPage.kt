package com.github.tomasmilata.intelliroutes

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.*
import org.jetbrains.annotations.*

import javax.swing.*

class RoutesColorSettingsPage : ColorSettingsPage {

    override fun getIcon(): Icon? {
        return RoutesIcons.FILE
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return RoutesSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return "# You are reading the \".properties\" entry.\n" +
                "! The exclamation mark can also mark text as comments.\n" +
                "website = http://en.wikipedia.org/\n" +
                "language = English\n" +
                "# The backslash below tells the application to continue reading\n" +
                "# the value onto the next line.\n" +
                "message = Welcome to \\\n" +
                "          Wikipedia!\n" +
                "# Add spaces to the key\n" +
                "key\\ with\\ spaces = This is the value that could be looked up with the key \"key with spaces\".\n" +
                "# Unicode\n" +
                "tab : \\u0009"
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "Routes"
    }

    companion object {
        private val DESCRIPTORS = arrayOf(AttributesDescriptor("HTTP Verb", RoutesSyntaxHighlighter.VERB), AttributesDescriptor("Path", RoutesSyntaxHighlighter.PATH), AttributesDescriptor("Call", RoutesSyntaxHighlighter.CALL))
    }
}