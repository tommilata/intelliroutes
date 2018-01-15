package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.ControllerFilter.filterByClassPrefix
import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.util.InheritanceUtil.isInheritorOrSelf
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.util.ProcessingContext
import com.intellij.util.indexing.FileBasedIndex
import org.jetbrains.plugins.scala.ScalaFileType
import org.jetbrains.plugins.scala.lang.psi.api.ScalaFile


class RoutesCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.VERB).withLanguage(RoutesLanguage.INSTANCE),
                httpVerbCompletionProvider
        )
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.CONTROLLER_METHOD).withLanguage(RoutesLanguage.INSTANCE),
                javaControllerMethodCompletionProvider
        )
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(RoutesTypes.CONTROLLER_METHOD).withLanguage(RoutesLanguage.INSTANCE),
                scalaControllerMethodCompletionProvider
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

        private val javaControllerMethodCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        addCompletions(parameters, resultSet, javaFiles(project))
                    }
                }


        private val scalaControllerMethodCompletionProvider =
                object : CompletionProvider<CompletionParameters>() {
                    override fun addCompletions(parameters: CompletionParameters,
                                                context: ProcessingContext,
                                                resultSet: CompletionResultSet) {
                        val project = parameters.originalFile.project
                        addCompletions(parameters, resultSet, scalaFiles(project))
                    }
                }

        private fun addCompletions(parameters: CompletionParameters,
                                   resultSet: CompletionResultSet,
                                   files: List<PsiClassOwner>) {
            val enteredText = parameters.position.text.removeSuffix("IntellijIdeaRulezzz ") // WTF?

            val classes = files
                    .flatMap { it.classes.toList() }
                    .filter { it.qualifiedName?.let { filterByClassPrefix(it, enteredText) } ?: false }
                    .filter { it.isPhysical }
                    .filterNot { it.isInterface }

            val project = parameters.originalFile.project
            val projectWithLibrariesScope = ProjectScope.getAllScope(project)
            val psiFacade = JavaPsiFacade.getInstance(project)
            val playAction = psiFacade.findClass("play.api.mvc.Action", projectWithLibrariesScope)

            classes.forEach { cls ->
                cls.allMethods.forEach { method ->
                    val returnType = PsiTypesUtil.getPsiClass(method.returnType)
                    if (isInheritorOrSelf(returnType, playAction, true)) {
                        val name = "${cls.qualifiedName}.${method.name}"
                        val lookupElement = LookupElementBuilder.create(name)
                        resultSet.addElement(lookupElement)
                    }
                }
            }
        }

        private fun javaFiles(project: Project): List<PsiJavaFile> {
            val psiManager = PsiManager.getInstance(project)
            val virtualFiles = virtualFiles(project, JavaFileType.INSTANCE)
            return virtualFiles.mapNotNull {
                psiManager.findFile(it) as PsiJavaFile?
            }
        }

        private fun scalaFiles(project: Project): List<ScalaFile> {
            val psiManager = PsiManager.getInstance(project)
            val virtualFiles = virtualFiles(project, ScalaFileType.INSTANCE)
            return virtualFiles.mapNotNull {
                psiManager.findFile(it) as ScalaFile?
            }
        }

        private fun virtualFiles(project: Project, fileType: LanguageFileType): Collection<VirtualFile> {
            val index = FileBasedIndex.getInstance()
            val searchScope = ProjectScope.getContentScope(project)

            return index.getContainingFiles(
                    FileTypeIndex.NAME, fileType, searchScope)
        }

    }
}
