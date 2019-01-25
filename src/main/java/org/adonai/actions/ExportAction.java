package org.adonai.actions;

import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportConfigurationMerger;
import org.adonai.export.ExportException;
import org.adonai.export.pdf.PdfExporter;
import org.adonai.model.Configuration;
import org.adonai.model.Song;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Collection;

public class ExportAction {

  private ExportConfigurationMerger exportConfigurationMerger = new ExportConfigurationMerger();


  public void export (Configuration configuration, Collection<Song> songs,
                      String name,
                      boolean withChords,
                      boolean withTransposeInfo) {
    PdfExporter writer = new PdfExporter();

    // TODO User specific configuration
    //  ExportConfiguration mergedConfiguration = exportConfigurationMerger.getMergedExportConfiguration(documentBuilder.getDefaultConfiguration(), exportConfiguration);



    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(writer.getPdfDocumentBuilder().getClass());
    exportConfiguration.setWithChords(withChords);
    exportConfiguration.setWithTransposeInfo(withTransposeInfo);
    File exportFile = new File(configuration.getExportPathAsFile(), name + "_Chords.pdf");
    exportFile.getParentFile().mkdirs();
    try {
      writer.export(songs, exportFile, exportConfiguration);
    } catch (ExportException e) {
      throw new IllegalStateException(e);
    }

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(new StringSelection(configuration.getExportPathAsFile().getAbsolutePath()), null);
  }
}
