package org.adonai.export;

import java.io.File;
import org.adonai.api.ExportBuilder;

public abstract class AbstractDocumentBuilder implements DocumentBuilder, ExportBuilder {

  ExportTokenContainer exportTokenContainer = new ExportTokenContainer();


  public void newToken (final ExportToken exportToken) {
    exportTokenContainer.addToken(exportToken);
  }

  public ExportTokenContainer getExportTokenContainer() {
    return exportTokenContainer;
  }

  @Override
  public void openPreview(File file) {

  }





}
