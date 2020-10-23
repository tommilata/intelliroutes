package com.github.tomasmilata.intelliroutes

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.PrioritizedLookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.*
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

        fun suggest(suggestion: String, priority: Double) {
            val lookupElement = LookupElementBuilder.create(suggestion)
            val prioritizedLookupElement = PrioritizedLookupElement.withPriority(lookupElement, priority)
            resultSet.addElement(prioritizedLookupElement)
        }

        fun addMethodSuggestion(cls: PsiClass, method: PsiMethod) {
            val fullMethod = "${cls.qualifiedName}.${method.name}"
            val returnType = PsiTypesUtil.getPsiClass(method.returnType)
            val priority = if (isInheritorOrSelf(returnType, playAction, true)) 1.0 else 0.0
            suggest(fullMethod, priority)
        }

        packageNames.forEach { suggest(it, priority = 0.0) }
        classes.mapNotNull { it.qualifiedName }.forEach { suggest(it, priority = 0.0) }
        classes.forEach { cls ->
            val className = cls.qualifiedName
            if (className != null && enteredText.startsWith(className)) {
                cls.allMethods.forEach { method -> addMethodSuggestion(cls, method) }
            }
        }
    }

    private fun playActionType(parameters: CompletionParameters): PsiClass? {
        val project = parameters.originalFile.project
        val projectWithLibrariesScope = ProjectScope.getAllScope(project)
        val psiFacade = JavaPsiFacade.getInstance(project)
        return psiFacade.findClass("play.api.mvc.Action", projectWithLibrariesScope)
    }

}
