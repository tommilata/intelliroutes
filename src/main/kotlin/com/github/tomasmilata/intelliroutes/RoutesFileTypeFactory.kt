package com.github.tomasmilata.intelliroutes

import com.intellij.openapi.fileTypes.*

class RoutesFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(fileTypeConsumer: FileTypeConsumer) {
        fileTypeConsumer.consume(RoutesFileType.INSTANCE, ExactFileNameMatcher("routes"))
    }
}