package org.adonai.export.presentation;

import org.adonai.SizeInfo;
import org.adonai.export.AbstractDocumentBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportTokenType;
import org.adonai.export.NewPageStrategy;

import java.io.File;

public class PresentationDocumentBuilder extends AbstractDocumentBuilder {




  @Override
  public SizeInfo getSize(String text, final ExportTokenType exportTokenType) {
    return null;
  }

  @Override
  public void write(File outputfile) {

  }

  @Override
  public ExportConfiguration getDefaultConfiguration() {
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setWithPartType(false);
    exportConfiguration.setNewPageStrategy(NewPageStrategy.PER_PART);
    return exportConfiguration;
  }


}
