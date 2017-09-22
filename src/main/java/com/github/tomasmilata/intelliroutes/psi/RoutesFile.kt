package com.github.tomasmilata.intelliroutes.psi

import com.github.tomasmilata.intelliroutes.RoutesFileType
import com.github.tomasmilata.intelliroutes.RoutesLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

import javax.swing.*

class RoutesFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, RoutesLanguage.INSTANCE) {

    override fun getFileType(): FileType {
        return RoutesFileType.INSTANCE
    }

    override fun toString(): String {
        return "Routes File"
    }

    override fun getIcon(flags: Int): Icon? {
        return super.getIcon(flags)
    }
}