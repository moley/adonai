package org.adonai.export.pdf;

import org.adonai.export.*;
import org.adonai.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
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
    exportConfiguration.setOpenPreview(false);
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);
  }

  private Song getTestdata (String exportTestdata) {
    ConfigurationService configurationService = new ConfigurationService();
    configurationService.setConfigFile(new File ("src/test/resources/export/pdf/" + exportTestdata + "/config.xml"));
    Configuration configuration = configurationService.get();
    return configuration.getSongBooks().get(0).getSongs().get(0);
  }

  @Test
  public void exportLayout () throws IOException, ExportException {

    Song layoutTestSong = getTestdata("layouttest");


    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "exportWithSongs").toFile();

    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = pdfExporter.getPdfDocumentBuilder().getDefaultConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setOpenPreview(false);
    pdfExporter.export(Arrays.asList(layoutTestSong), tmpExportFile, exportConfiguration);
    ExportTokenContainer exportTokenContainer = pdfExporter.getPdfDocumentBuilder().getExportTokenContainer();
    ExportToken exportToken = exportTokenContainer.findTokenByText("ich will dir");
    ExportToken exportToken2 = exportTokenContainer.findTokenByText("Mein ganzes ");

    Double diffY = exportToken2.getAreaInfo().getY() - exportToken.getAreaInfo().getY();
    Assert.assertTrue ("Distance between two lines too small", diffY > 20);
  }
}
