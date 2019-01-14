package org.adonai.actions;

import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportException;
import org.adonai.export.pdf.PdfExporter;
import org.adonai.model.Configuration;
import org.adonai.model.Song;

import java.io.File;
import java.util.Collection;

public class ExportAction {

  public void export (Configuration configuration, Collection<Song> songs,
                      String name,
                      boolean withChords) {
    PdfExporter writer = new PdfExporter();

    ExportConfiguration exportConfiguration = writer.getPdfDocumentBuilder().getDefaultConfiguration();
    exportConfiguration.setWithChords(withChords);
    exportConfiguration.setOpenPreview(true);



    File exportFile = new File (configuration.getExportPathAsFile(), name + "_Chords.pdf");
    exportFile.getParentFile().mkdirs();
    try {
      writer.export(songs, exportFile, exportConfiguration);
    } catch (ExportException e) {
      throw new IllegalStateException(e);
    }
  }
}
