package org.adonai.export.pdf;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.adonai.AdonaiProperties;
import org.adonai.export.ExportConfiguration;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Song;

public class PdfWriterStarter {

  public final static void main (final String [] args) throws IOException {
    File tmpExportFile = Files.createTempFile(PdfWriterStarter.class.getSimpleName(), "exportWithSongs").toFile();
    AdonaiProperties adonaiProperties = new AdonaiProperties();
    ConfigurationService configurationService = new ConfigurationService();
    Configuration configuration = configurationService.get(adonaiProperties.getCurrentTenant());

    List<Song> songs = configuration.getSongBooks().get(0).getSongs();
    PdfExporter pdfExporter = new PdfExporter();
    PdfDocumentBuilder pdfDocumentBuilder = new PdfDocumentBuilder();
    ExportConfiguration exportConfiguration = pdfDocumentBuilder.getDefaultConfiguration();
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithChords(true);
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);

    if (Desktop.isDesktopSupported()) {
      try {
        Desktop.getDesktop().open(tmpExportFile);
      } catch (IOException ex) {
        // no application registered for PDFs
      }
    }

  }
}
