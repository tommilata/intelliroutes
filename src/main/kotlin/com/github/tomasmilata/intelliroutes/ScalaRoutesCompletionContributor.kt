package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.ControllerMethodCompletionContributor.addCompletionsFromFiles
import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.openapi.project.Project
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiManager
import com.intellij.util.ProcessingContext
import org.jetbrains.plugins.scala.ScalaFileType
import org.jetbrains.plugins.scala.lang.psi.api.ScalaFile


class ScalaRoutesCompletionContributor : CompletionContributor() {
    init {
        val elementPattern = PlatformPatterns.psiElement(RoutesTypes.CONTROLLER_METHOD)
                .withLanguage(RoutesLanguage.INSTANCE)
        extend(CompletionType.BASIC, elementPattern, scalaControllerMethodCompletionProvider)
    }

    companion object {
        private val scalaControllerMethodCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        val scalaFiles = scalaFiles(project)
                        addCompletionsFromFiles(parameters, resultSet, scalaFiles)
                    }
                }

        private fun scalaFiles(project: Project): List<ScalaFile> {
            val psiManager = PsiManager.getInstance(project)
            return ProjectFileIndex.find(project, ScalaFileType.INSTANCE)
                    .mapNotNull {
                        psiManager.findFile(it) as ScalaFile?
                    }
        }

    }
}
