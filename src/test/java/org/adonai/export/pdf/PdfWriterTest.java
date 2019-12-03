package org.adonai.export.pdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.adonai.export.AbstractExportTest;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportException;
import org.adonai.export.ExportToken;
import org.adonai.export.ExportTokenContainer;
import org.adonai.export.ExportTokenNewPage;
import org.adonai.export.ReferenceStrategy;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Song;
import org.adonai.model.SongPartDescriptorStrategy;
import org.junit.Assert;
import org.junit.Test;

public class PdfWriterTest extends AbstractExportTest {

  private boolean openPreview = false;



  @Test
  public void exportSongPartReference () throws IOException, ExportException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    Song song = getSongWithReference();
    System.out.println (song.toString());
    PdfExporter pdfExporter = new PdfExporter();

    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setChordTextDistance(new Double(5));
    exportConfiguration.setInterLineDistance(new Double(5));
    exportConfiguration.setInterPartDistance(new Double(5));
    exportConfiguration.setStructureDistance(new Double(5));
    exportConfiguration.setLeftBorder(new Double(5));
    exportConfiguration.setUpperBorder(new Double(5));
    exportConfiguration.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);

    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithChords(true);
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(Arrays.asList(song), tmpExportFile, exportConfiguration);

  }

  @Test
  public void exportSongPartDescriptorStrategyLong () throws IOException, ExportException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    List<Song> songs = getExportTestData();
    System.out.println (songs.get(0).toString());
    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setChordTextDistance(new Double(5));
    exportConfiguration.setInterLineDistance(new Double(5));
    exportConfiguration.setInterPartDistance(new Double(5));
    exportConfiguration.setStructureDistance(new Double(5));
    exportConfiguration.setLeftBorder(new Double(5));
    exportConfiguration.setUpperBorder(new Double(5));

    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithChords(true);
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);
  }

  @Test
  public void exportSongPartDescriptorStrategyShort () throws IOException, ExportException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    List<Song> songs = getExportTestData();
    System.out.println (songs.get(0).toString());
    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setStructureDistance(new Double(5));
    exportConfiguration.setChordTextDistance(new Double(5));
    exportConfiguration.setInterLineDistance(new Double(5));
    exportConfiguration.setInterPartDistance(new Double(5));
    exportConfiguration.setLeftBorder(new Double(5));
    exportConfiguration.setUpperBorder(new Double(5));
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithChords(true);
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.SHORT);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);
  }

  @Test
  public void export () throws IOException, ExportException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    List<Song> songs = getExportTestData();
    System.out.println (songs.get(0).toString());
    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithChords(true);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);
  }

  private Song getTestdata (String exportTestdata) {
    ConfigurationService configurationService = new ConfigurationService();
    configurationService.close();
    configurationService.setConfigFile(new File ("src/test/resources/export/pdf/" + exportTestdata + "/config.xml"));
    if (! configurationService.getConfigFile().exists())
      throw new IllegalStateException("Modelfile " + configurationService.getConfigFile().getAbsolutePath() + " does not exist");
    Configuration configuration = configurationService.get();
    return configuration.getSongBooks().get(0).getSongs().get(0);
  }



  @Test
  public void emptySecondSide () throws IOException, ExportException {
    Song layoutTestSong = getTestdata("emptySecondSide");

    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "emptySecondSide").toFile();

    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = pdfExporter.getPdfDocumentBuilder().getDefaultConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(Arrays.asList(layoutTestSong), tmpExportFile, exportConfiguration);
    ExportTokenContainer exportTokenContainer = pdfExporter.getPdfDocumentBuilder().getExportTokenContainer();
    ExportToken prelastexportToken = exportTokenContainer.getExportTokenList().get(exportTokenContainer.getExportTokenList().size() - 2);
    Assert.assertNotEquals("No newPage export token expected", ExportTokenNewPage.class, prelastexportToken.getClass());
    ExportToken lastexportToken = exportTokenContainer.getExportTokenList().get(exportTokenContainer.getExportTokenList().size() - 1);
    Assert.assertEquals ("Zwischenzeit", lastexportToken.getText());
  }

  @Test
  public void exportLayout () throws IOException, ExportException {

    Song layoutTestSong = getTestdata("layouttest");

    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "exportLayout").toFile();

    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = pdfExporter.getPdfDocumentBuilder().getDefaultConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(Arrays.asList(layoutTestSong), tmpExportFile, exportConfiguration);
    ExportTokenContainer exportTokenContainer = pdfExporter.getPdfDocumentBuilder().getExportTokenContainer();
    ExportToken exportToken = exportTokenContainer.findTokenByText("ich will dir");
    ExportToken exportToken2 = exportTokenContainer.findTokenByText("Mein ganzes ");

    Double diffY = exportToken2.getAreaInfo().getY() - exportToken.getAreaInfo().getY();
    Assert.assertTrue ("Distance between two lines too small", diffY > 20);
  }

  @Test
  public void firstChordEmpty () throws IOException, ExportException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "emptychord").toFile();

    Song layoutTestSong = getTestdata("emptychord");
    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = pdfExporter.getPdfDocumentBuilder().getDefaultConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setWithContentPage(false);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(Arrays.asList(layoutTestSong), tmpExportFile, exportConfiguration);
    ExportTokenContainer exportTokenContainer = pdfExporter.getPdfDocumentBuilder().getExportTokenContainer();
    ExportToken exportTokenThirdLine = exportTokenContainer.findTokenByText("Let every");
    Assert.assertTrue ("Y value " + exportTokenThirdLine.getAreaInfo().getY() + " is not low enough",
      exportTokenThirdLine.getAreaInfo().getY() > 119);
  }

  @Test
  public void songMultiSides () throws IOException, ExportException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "multisides").toFile();

    Song layoutTestSong = getTestdata("multisides");
    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = pdfExporter.getPdfDocumentBuilder().getDefaultConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setWithContentPage(false);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(Arrays.asList(layoutTestSong), tmpExportFile, exportConfiguration);
    ExportTokenContainer exportTokenContainer = pdfExporter.getPdfDocumentBuilder().getExportTokenContainer();
    ExportToken exportTokenSecondSide = exportTokenContainer.findTokenByText("Du machst Himmel und Erde einmal neu");
    Assert.assertTrue ("Y-Position of second side token invalid (" + exportTokenSecondSide + ")",
      exportTokenSecondSide.getAreaInfo().getY() < 50);

  }
}
