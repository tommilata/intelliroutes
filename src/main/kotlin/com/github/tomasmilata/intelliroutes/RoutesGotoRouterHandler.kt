package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTokenType
import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement

class RoutesGotoRouterHandler : GotoDeclarationHandler {

    override fun getActionText(context: DataContext): String {
        return "Go to router"
    }

    override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, editor: Editor?): Array<PsiElement>? {
        if (sourceElement !is LeafPsiElement || sourceElement.elementType !is RoutesTokenType) {
            return PsiElement.EMPTY_ARRAY
        }

        if (!(sourceElement.elementType as RoutesTokenType).isRouterReference()) {
            return PsiElement.EMPTY_ARRAY
        }

        return ProjectFileIndex.routesFiles(editor?.project!!)
                .filter {
                    it.name.replace("\\.routes$".toRegex(), ".Routes") == sourceElement.text
                }.toTypedArray()
    }

}
