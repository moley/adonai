package org.adonai.export;

public abstract class AbstractDocumentBuilder implements DocumentBuilder{

  ExportTokenContainer exportTokenContainer = new ExportTokenContainer();


  public void newToken (final ExportToken exportToken) {
    exportTokenContainer.addToken(exportToken);
  }

  public ExportTokenContainer getExportTokenContainer() {
    return exportTokenContainer;
  }



}
