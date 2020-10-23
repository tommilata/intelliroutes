package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.Project
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.util.ProcessingContext


class OtherRoutersCompletionContributor : CompletionContributor() {
    init {
        val elementPattern = PlatformPatterns.psiElement(RoutesTypes.ROUTER_REFERENCE)
                .withLanguage(RoutesLanguage.INSTANCE)
        extend(CompletionType.BASIC, elementPattern, otherRoutersCompletionProvider)
    }

    companion object {
        private val otherRoutersCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        val routesFiles = routesFiles(project)
                        routesFiles.forEach {
                            val routerName = it.name.replace("\\.routes$".toRegex(), ".Routes")
                            val suggestion = LookupElementBuilder.create(routerName)
                            resultSet.addElement(suggestion)
                        }

                    }
                }

        private fun routesFiles(project: Project): List<PsiFile> {
            val psiManager = PsiManager.getInstance(project)
            return ProjectFileIndex.find(project, RoutesFileType.INSTANCE)
                    .mapNotNull {
                        psiManager.findFile(it)
                    }.filter {
                        it.fileType is RoutesFileType
                    }
        }

    }
}
