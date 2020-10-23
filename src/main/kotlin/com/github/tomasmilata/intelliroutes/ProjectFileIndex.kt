package com.github.tomasmilata.intelliroutes

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.ProjectScope

object ProjectFileIndex {

    fun find(project: Project, fileType: LanguageFileType): Collection<VirtualFile> {
        val searchScope = ProjectScope.getContentScope(project)
        return FileTypeIndex.getFiles(fileType, searchScope)
    }

    fun routesFiles(project: Project): List<PsiFile> {
        val psiManager = PsiManager.getInstance(project)
        return find(project, RoutesFileType.INSTANCE)
                .mapNotNull {
                    psiManager.findFile(it)
                }.filter {
                    it.fileType is RoutesFileType
                }
    }

}