package com.github.tomasmilata.intelliroutes

import com.intellij.openapi.fileTypes.ExactFileNameMatcher
import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher
import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory

class RoutesFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(fileTypeConsumer: FileTypeConsumer) {
        fileTypeConsumer.consume(RoutesFileType.INSTANCE, ExactFileNameMatcher("routes"))
        fileTypeConsumer.consume(RoutesFileType.INSTANCE, ExtensionFileNameMatcher("routes"))
    }
}