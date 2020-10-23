package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTokenType
import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.search.ProjectScope

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
        val project = editor?.project!!

        val targetRoutesFiles: Array<PsiElement> = ProjectFileIndex.routesFiles(project)
                .filter {
                    it.name.replace("\\.routes$".toRegex(), ".Routes") == sourceElement.text
                }.toTypedArray()

        val projectWithLibrariesScope = ProjectScope.getAllScope(project)
        val psiFacade = JavaPsiFacade.getInstance(project)

        val className = sourceElement.text
        val targetRouterClasses = psiFacade.findClasses(className, projectWithLibrariesScope)

        return targetRoutesFiles.plus(targetRouterClasses)
    }

}
