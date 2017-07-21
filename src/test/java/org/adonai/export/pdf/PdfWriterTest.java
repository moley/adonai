package org.adonai.export.pdf;

import org.junit.Test;
import org.adonai.export.AbstractExportTest;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportException;
import org.adonai.model.Song;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class PdfWriterTest extends AbstractExportTest {

  @Test
  public void export () throws IOException, ExportException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "exportWithSongs").toFile();
    List<Song> songs = getExportTestData();
    System.out.println (songs.get(0).toString());
    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithChords(true);
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);

  }
}
