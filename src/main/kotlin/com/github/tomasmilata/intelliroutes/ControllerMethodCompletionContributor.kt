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

        val playAction = playActionType(parameters)

        fun suggest(lookupElement: LookupElementBuilder, priority: Double) {
            val prioritizedLookupElement = PrioritizedLookupElement.withPriority(lookupElement, priority)
            resultSet.addElement(prioritizedLookupElement)
        }

        val filesToSearch = files.filter { it.isValid && it.isPhysical }
        filesToSearch.forEach { file ->
            suggest(LookupElementBuilder.create(file.packageName), priority = 0.0)
        }

        val classes = filesToSearch.flatMap { it.classes.toList() }
        classes.forEach {
            it.qualifiedName?.let { className ->
                val lookupElement = LookupElementBuilder
                        .create(className).bold()
                        .withIcon(it.getIcon(0))
                suggest(lookupElement, priority = 0.0)
            }
        }
        classes.forEach { cls ->
            val className = cls.qualifiedName
            if (className != null && enteredText.startsWith(className)) {
                cls.allMethods.forEach { method ->
                    val fullMethod = "${cls.qualifiedName}.${method.name}"
                    val returnType = PsiTypesUtil.getPsiClass(method.returnType)
                    val priority = if (isInheritorOrSelf(returnType, playAction, true)) 1.0 else 0.0
                    val lookupElement = LookupElementBuilder
                            .create(fullMethod).bold()
                            .withIcon(method.getIcon(0))
                    suggest(lookupElement, priority)
                }
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
