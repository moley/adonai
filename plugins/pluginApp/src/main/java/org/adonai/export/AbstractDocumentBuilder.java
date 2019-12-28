package org.adonai.export;

import java.io.File;

public abstract class AbstractDocumentBuilder implements DocumentBuilder{

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
