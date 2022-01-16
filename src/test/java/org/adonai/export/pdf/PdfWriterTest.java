package org.adonai.export.pdf;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.adonai.export.AbstractExportTest;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportToken;
import org.adonai.export.ExportTokenContainer;
import org.adonai.export.ReferenceStrategy;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartDescriptorStrategy;
import org.adonai.model.SongPartType;
import org.adonai.services.ModelService;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PdfWriterTest extends AbstractExportTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(PdfWriterTest.class);

  private boolean openPreview = false;


  @AfterClass
  public static void afterClass () {

    ModelService modelService = new ModelService(null);

  }

  @Test
  public void exportEmptyPartWithStructure () throws IOException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();

    SongBuilder songBuilder = new SongBuilder();
    songBuilder.withPart(SongPartType.INTRO).withLine().withLinePart("", "C").withLinePart("", "G");
    songBuilder.withPart(SongPartType.VERS).withLine().withLinePart("This is ", "C").withLinePart("a test", "G");
    List<Song> songs = Arrays.asList(songBuilder.get());
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setChordTextDistance(new Double(5));
    exportConfiguration.setInterLineDistance(new Double(5));
    exportConfiguration.setInterPartDistance(new Double(5));
    exportConfiguration.setStructureDistance(new Double(5));
    exportConfiguration.setLeftBorder(new Double(5));
    exportConfiguration.setUpperBorder(new Double(5));
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setOpenPreview(openPreview);


    PdfExporter pdfExporter = new PdfExporter();
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(tmpExportFile, Charset.defaultCharset());
    for (String next: lines) {
      LOGGER.info("> " + next);
    }

    ExportTokenContainer exportTokenContainer = pdfExporter.getPdfDocumentBuilder().getExportTokenContainer();
    LOGGER.info("ExportTokenContainer: " + exportTokenContainer);

    ExportToken firstExportToken = exportTokenContainer.getExportTokenList().get(0);
    Assert.assertEquals ("Part with no text is not exported when exporting without chords", "INTRO", firstExportToken.getText());

  }


  @Test
  public void exportSongPartReference () throws IOException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    Song song = getSongWithReference();
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
  public void exportSongPartDescriptorStrategyLong () throws IOException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    List<Song> songs = getExportTestData();
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
  public void exportSongPartDescriptorStrategyShort () throws IOException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    List<Song> songs = getExportTestData();
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
  public void export () throws IOException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "export").toFile();
    List<Song> songs = getExportTestData();
    PdfExporter pdfExporter = new PdfExporter();
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithChords(true);
    exportConfiguration.setOpenPreview(openPreview);
    pdfExporter.export(songs, tmpExportFile, exportConfiguration);
  }

  @Test
  public void exportLayout () throws IOException {

    SongBuilder builder = new SongBuilder();

    builder = builder.withPart(SongPartType.VERS);
    builder = builder.withLine().withLinePart("ich will dir", "A");
    builder = builder.withLine().withLinePart("Mein ganzes Herz", "D");

    Song layoutTestSong = builder.get();

    LOGGER.info("Song StructItems" + layoutTestSong.getStructItems());
    LOGGER.info("Song Parts" + layoutTestSong.getSongParts());

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
  public void firstChordEmpty () throws IOException {
    File tmpExportFile = Files.createTempFile(getClass().getSimpleName(), "emptychord").toFile();

    SongBuilder builder = SongBuilder.instance();
    builder = builder.withTitle("TITLE").withId("1");
    builder = builder.withPart(SongPartType.REFRAIN).withLine().withLinePart("First line", null).withLinePart("Second part", "D");
    builder = builder.withLine().withLinePart("Second line", null);
    builder = builder.withLine().withLinePart("Third line", null);
    builder = builder.withLine().withLinePart("Let every", "D");

    Song layoutTestSong = builder.get();
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

}
