package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.ControllerMethodCompletionContributor.addCompletionsFromFiles
import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.util.ProcessingContext


class JavaRoutesCompletionContributor : CompletionContributor() {
    init {
        val elementPattern = PlatformPatterns.psiElement(RoutesTypes.CONTROLLER_METHOD)
                .withLanguage(RoutesLanguage.INSTANCE)
        extend(CompletionType.BASIC, elementPattern, javaControllerMethodCompletionProvider)
    }

    companion object {
        private val javaControllerMethodCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        val javaFiles = javaFiles(project)
                        addCompletionsFromFiles(parameters, resultSet, javaFiles)
                    }
                }

        private fun javaFiles(project: Project): List<PsiJavaFile> {
            val psiManager = PsiManager.getInstance(project)
            return ProjectFileIndex.find(project, JavaFileType.INSTANCE)
                    .mapNotNull {
                        psiManager.findFile(it) as PsiJavaFile?
                    }
        }

    }
}
