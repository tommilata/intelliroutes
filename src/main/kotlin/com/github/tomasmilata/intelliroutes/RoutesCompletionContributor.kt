package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.util.InheritanceUtil.isInheritor
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.util.ProcessingContext
import com.intellij.util.indexing.FileBasedIndex


class RoutesCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.VERB).withLanguage(RoutesLanguage.INSTANCE),
                httpVerbCompletionProvider
        )
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.CONTROLLER_METHOD).withLanguage(RoutesLanguage.INSTANCE),
                controllerMethodCompletionProvider
        )
    }

    companion object {
        private val httpVerbs =
                listOf("GET", "POST", "PUT", "PATCH", "HEAD", "DELETE", "OPTIONS")
                        .map { verb ->
                            LookupElementBuilder.create(verb)
                        }
        private val httpVerbCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        resultSet.addAllElements(httpVerbs)
                    }
                }

        private val controllerMethodCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project

                        val javaClasses = javaFiles(project).flatMap { javaFile ->
                            javaFile.classes.toList()
                        }

                        javaClasses.forEach { cls ->
                            cls.methods.forEach { method ->
                                val returnType = PsiTypesUtil.getPsiClass(method.returnType)
                                if (isInheritor(returnType, "play.api.mvc.Action")) {
                                    val lookupElement = LookupElementBuilder.create("${cls.qualifiedName}.${method.name}")
                                    resultSet.addElement(lookupElement)
                                }
                            }
                        }
                    }
                }

        private fun javaFiles(project: Project): List<PsiJavaFile> {

            val index = FileBasedIndex.getInstance()
            val searchScope = ProjectScope.getContentScope(project)

            val virtualFiles = index.getContainingFiles(
                    FileTypeIndex.NAME, JavaFileType.INSTANCE, searchScope)


            return virtualFiles.mapNotNull { virtualFile ->
                PsiManager.getInstance(project).findFile(virtualFile) as PsiJavaFile?
            }
        }

    }
}
