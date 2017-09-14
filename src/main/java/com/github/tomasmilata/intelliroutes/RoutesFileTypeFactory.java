package com.github.tomasmilata.intelliroutes;

import com.intellij.openapi.fileTypes.*;
import org.jetbrains.annotations.NotNull;

public class RoutesFileTypeFactory extends FileTypeFactory {
  @Override
  public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
    fileTypeConsumer.consume(RoutesFileType.INSTANCE, "simple");
  }
}