package com.github.tomasmilata.intelliroutes

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.*

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
            GET    /user/${'$'}id<[0-9]+>     controllers.UserController.get(id: Int)
            GET    /user/:username       controllers.UserController.get(username: String)

            POST   /*path                controllers.UploadController.upload(path: String)
            
            +nocsrf
            GET    /version              controllers.AppController.version

            ->     /other                other.OtherRouter

            bad character
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
        return "Play 2 Routes"
    }

    companion object {
        private val DESCRIPTORS = arrayOf(
                AttributesDescriptor("HTTP Verb", RoutesSyntaxHighlighter.VERB),
                AttributesDescriptor("Redirect Arrow", RoutesSyntaxHighlighter.ARROW),
                AttributesDescriptor("Modifiers Plus Symbol", RoutesSyntaxHighlighter.PLUS),
                AttributesDescriptor("Modifier", RoutesSyntaxHighlighter.MODIFIER),
                AttributesDescriptor("Path Parameter", RoutesSyntaxHighlighter.PATH_PARAMETER),
                AttributesDescriptor("Path Regex Parameter", RoutesSyntaxHighlighter.PATH_REGEX_PARAM),
                AttributesDescriptor("Wildcard Path Parameter", RoutesSyntaxHighlighter.WILDCARD_PARAMETER),
                AttributesDescriptor("Static Path Segment", RoutesSyntaxHighlighter.STATIC_PATH_SEGMENT),
                AttributesDescriptor("Controller Method", RoutesSyntaxHighlighter.CONTROLLER_METHOD),
                AttributesDescriptor("Controller Call Argument Name", RoutesSyntaxHighlighter.ARGUMENT_NAME),
                AttributesDescriptor("Controller Call Argument Value", RoutesSyntaxHighlighter.ARGUMENT_VALUE),
                AttributesDescriptor("Router Reference", RoutesSyntaxHighlighter.ROUTER_REFERENCE),
                AttributesDescriptor("Comment", RoutesSyntaxHighlighter.COMMENT),
                AttributesDescriptor("Bad Character", RoutesSyntaxHighlighter.BAD_CHARACTER)
        )
    }
}