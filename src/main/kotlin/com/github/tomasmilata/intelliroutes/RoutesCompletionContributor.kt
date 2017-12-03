package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.psi.RoutesTypes
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiMethod
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.util.InheritanceUtil.isInheritor
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
                        val text = parameters.position.text.removeSuffix("IntellijIdeaRulezzz ")

                        val javaClasses = javaFiles(project).flatMap { javaFile ->
                            javaFile.classes.toList()
                                    .filter { it.qualifiedName?.startsWith(text) ?: false }
                        }

                        val scalaFiles = scalaFiles(project)
                        val scalaClasses = scalaFiles.flatMap { scalaFile ->
                            val r = scalaFile.classes.toList()
                            r.filter { it.qualifiedName?.startsWith(text) ?: false }
                        }
                                .filter { it.isPhysical }
                                .filterNot { it.isInterface }

                        val allClasses = javaClasses.plus(scalaClasses)

                        allClasses.forEach { cls ->
                            cls.allMethods.forEach { method ->
                                val returnType = PsiTypesUtil.getPsiClass(method.returnType)
                                if (isInheritor(returnType, "play.api.mvc.Action")) {
                                    val name = fullyQualifiedName(cls, method)
                                    val lookupElement = LookupElementBuilder.create(name)
                                    resultSet.addElement(lookupElement)
                                }
                            }
                        }
                    }
                }

        private fun fullyQualifiedName(cls: PsiClass, method: PsiMethod) = "${cls.qualifiedName}.${method.name}"

        private fun javaFiles(project: Project): List<PsiJavaFile> {

            val index = FileBasedIndex.getInstance()
            val searchScope = ProjectScope.getContentScope(project)

            val virtualFiles = index.getContainingFiles(
                    FileTypeIndex.NAME, JavaFileType.INSTANCE, searchScope)

            val psiManager = PsiManager.getInstance(project)
            return virtualFiles.mapNotNull { virtualFile ->
                psiManager.findFile(virtualFile) as PsiJavaFile?
            }
        }

        private fun scalaFiles(project: Project): List<ScalaFile> {

            val index = FileBasedIndex.getInstance()
            val searchScope = ProjectScope.getContentScope(project)

            val virtualFiles = index.getContainingFiles(
                    FileTypeIndex.NAME, ScalaFileType.INSTANCE, searchScope)

            val psiManager = PsiManager.getInstance(project)
            return virtualFiles.mapNotNull { virtualFile ->
                psiManager.findFile(virtualFile) as ScalaFile?
            }
        }

    }
}
