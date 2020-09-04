package com.github.tomasmilata.intelliroutes

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiMethod
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.util.InheritanceUtil.isInheritorOrSelf
import com.intellij.psi.util.PsiTypesUtil

object ControllerMethodCompletionContributor {

    fun addCompletionsFromFiles(parameters: CompletionParameters,
                                resultSet: CompletionResultSet,
                                files: List<PsiClassOwner>) {
        val enteredText = parameters.position.text.removeSuffix("IntellijIdeaRulezzz ") // WTF?

        val filesToSearch = files.filter { it.isValid && it.isPhysical }
        val packageNames = filesToSearch.map { it.packageName }
        val classes = filesToSearch.flatMap { it.classes.toList() }

        val playAction = playActionType(parameters)

        fun addPrioritizedSuggestion(suggestion: String, priority: Double) {
            val lookupElement = LookupElementBuilder.create(suggestion)
            val prioritizedLookupElement = PrioritizedLookupElement.withPriority(lookupElement, priority)
            resultSet.addElement(prioritizedLookupElement)
        }

        fun addSuggestion(fullSuggestion: String) {
            val suggestedSuffix = fullSuggestion.removePrefix(enteredText)
            if (!suggestedSuffix.contains('.')) { // only add suffixes until the next '.'
                addPrioritizedSuggestion(fullSuggestion, 0.0)
            }
        }

        fun addMethodSuggestion(cls: PsiClass, method: PsiMethod) {
            val fullSuggestion = "${cls.qualifiedName}.${method.name}"

            val suggestedSuffix = fullSuggestion.removePrefix(enteredText)
            if (!suggestedSuffix.contains('.')) { // only add suffixes until the next '.'
                val returnType = PsiTypesUtil.getPsiClass(method.returnType)

                val priority =  if (isInheritorOrSelf(returnType, playAction, true))  1.0 else 0.0
                addPrioritizedSuggestion(fullSuggestion, priority)
            }
        }

        packageNames.forEach { addSuggestion(it) }
        classes.forEach { cls ->
            addSuggestion(cls.qualifiedName.toString())
        }
        classes.forEach { cls ->
            cls.allMethods.forEach { method -> addMethodSuggestion(cls, method) }
        }
    }

    private fun playActionType(parameters: CompletionParameters): PsiClass? {
        val project = parameters.originalFile.project
        val projectWithLibrariesScope = ProjectScope.getAllScope(project)
        val psiFacade = JavaPsiFacade.getInstance(project)
        return psiFacade.findClass("play.api.mvc.Action", projectWithLibrariesScope)
    }

    fun virtualFiles(project: Project, fileType: LanguageFileType): Collection<VirtualFile> {
        val searchScope = ProjectScope.getContentScope(project)
        return FileTypeIndex.getFiles(fileType, searchScope)
    }

}
