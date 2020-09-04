package com.github.tomasmilata.intelliroutes

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

object ControllerMethodCompletionContributor {

    fun addCompletionsFromFiles(parameters: CompletionParameters,
                                resultSet: CompletionResultSet,
                                files: List<PsiClassOwner>) {
        val enteredText = parameters.position.text.removeSuffix("IntellijIdeaRulezzz ") // WTF?

        val filesToSearch = files.filter { it.isValid && it.isPhysical }
        val packageNames = filesToSearch.map { it.packageName }
        val classes = filesToSearch.flatMap { it.classes.toList() }

        val project = parameters.originalFile.project
        val projectWithLibrariesScope = ProjectScope.getAllScope(project)
        val psiFacade = JavaPsiFacade.getInstance(project)


//        psiFacade.findPackage()

//        val playAction = psiFacade.findClass("play.api.mvc.Action", projectWithLibrariesScope)

        fun add(fullSuggestion: String) {
            val suggestedSuffix  = fullSuggestion.removePrefix(enteredText)
            if (!suggestedSuffix.contains('.')) {
                // only add suffixes until the next '.'
                resultSet.addElement(LookupElementBuilder.create(fullSuggestion))
            }
        }

        packageNames.forEach { add(it) }
        classes.forEach { cls ->
            add(cls.qualifiedName.toString())
        }
        classes.forEach { cls ->
            cls.allMethods.forEach { method ->
//                val returnType = PsiTypesUtil.getPsiClass(method.returnType)
//                if (isInheritorOrSelf(returnType, playAction, true)) {
//                    val name = "${cls.qualifiedName}.${method.name}"
//                    val lookupElement = LookupElementBuilder.create(name)
//                    resultSet.addElement(lookupElement)
//                }
                val fqMethodName = "${cls.qualifiedName}.${method.name}"
//                println("method: $fqMethodName")
                add(fqMethodName)
            }
        }
    }

    fun virtualFiles(project: Project, fileType: LanguageFileType): Collection<VirtualFile> {
        val searchScope = ProjectScope.getContentScope(project)
        return FileTypeIndex.getFiles(fileType, searchScope)
    }

}
