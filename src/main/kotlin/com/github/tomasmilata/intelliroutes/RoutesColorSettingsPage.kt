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
        return """
            # Gets a single user with the given ID.
            GET    /user/:userId    UserController.get(userId: String)
        """.trimIndent()
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
        private val DESCRIPTORS = arrayOf(
                AttributesDescriptor("HTTP Verb", RoutesSyntaxHighlighter.VERB),
                AttributesDescriptor("Path", RoutesSyntaxHighlighter.PATH),
                AttributesDescriptor("Call", RoutesSyntaxHighlighter.CALL)
        )
    }
}