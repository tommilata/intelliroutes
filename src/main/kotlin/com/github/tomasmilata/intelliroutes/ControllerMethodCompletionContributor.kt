package com.github.tomasmilata.intelliroutes

import com.github.tomasmilata.intelliroutes.ControllerFilter.filterByClassPrefix
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope
import com.intellij.psi.util.InheritanceUtil.isInheritorOrSelf
import com.intellij.psi.util.PsiTypesUtil
import com.intellij.util.indexing.FileBasedIndex


object ControllerMethodCompletionContributor {

    fun addCompletionsFromFiles(parameters: CompletionParameters,
                                resultSet: CompletionResultSet,
                                files: List<PsiClassOwner>) {
        val enteredText = parameters.position.text.removeSuffix("IntellijIdeaRulezzz ") // WTF?

        val classes = files
                .flatMap { it.classes.toList() }
                .filter { filterByClassPrefix(it, enteredText) }
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

    fun virtualFiles(project: Project, fileType: LanguageFileType): Collection<VirtualFile> {
        val searchScope = ProjectScope.getContentScope(project)
        return FileTypeIndex.getFiles(fileType, searchScope)
    }

}
