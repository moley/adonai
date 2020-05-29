package org.adonai.export.txtfile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.adonai.SizeInfo;
import org.adonai.StringUtils;
import org.adonai.export.AbstractExportTest;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.Exporter;
import org.adonai.export.ReferenceStrategy;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartDescriptorStrategy;
import org.adonai.model.SongPartType;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class TextfileExporterTest extends AbstractExportTest {

  TextfileExporter textfileWriter = new TextfileExporter();


  private ExportConfiguration createExportConfiguration () {
    TextfileDocumentBuilder textfileDocumentBuilder = new TextfileDocumentBuilder();
    return textfileDocumentBuilder.getDefaultConfiguration();
  }

  @Test
  public void numbersForReferencesPart () throws IOException {
    SongBuilder songBuilder = new SongBuilder();
    songBuilder.withPart(SongPartType.VERS).withPartId("1").withLine().withLinePart("This is ", "C").withLinePart("a test", "G");
    songBuilder.withPartReference("1").withQuantity(2).withLine().withLinePart("Second", "F");
    Song song = songBuilder.get();
    List<Song> songs = Arrays.asList(song);
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    File exportFile = createExportFile(textfileWriter, "exportEmptyPartWithStructure");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertTrue ("VERS 2x not found (" + lines + ")", lines.toString().contains("VERS 2x"));

  }
  @Test
  public void exportEmptyPartWithStructure () throws IOException {
    SongBuilder songBuilder = new SongBuilder();
    songBuilder.withPart(SongPartType.INTRO).withLine().withLinePart("", "C").withLinePart("", "G");
    songBuilder.withPart(SongPartType.VERS).withLine().withLinePart("This is ", "C").withLinePart("a test", "G");
    List<Song> songs = Arrays.asList(songBuilder.get());
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
    File exportFile = createExportFile(textfileWriter, "exportEmptyPartWithStructure");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertTrue ("Empty part not exported without content (" + lines.get(0) + ")", lines.get(0).trim().startsWith("INTRO"));

  }

  @Test
  public void exportOnlyChordPart () throws IOException {
    SongBuilder songBuilder = new SongBuilder();
    songBuilder.withPart(SongPartType.VERS).withLine().withLinePart("", "A").withLinePart("", "D").withLinePart("", "A");
    List<Song> songs = Arrays.asList(songBuilder.get());
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setMinimalChordDistance (new Double(4));
    File exportFile = createExportFile(textfileWriter, "exportOnlyChordPart");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("A        D        A", StringUtils.trimRight(lines.get(0)));
  }

  @Test
  public void exportFilterEmptyPart () throws IOException {
    SongBuilder songBuilder = new SongBuilder();
    songBuilder.withPart(SongPartType.VERS).withLine().withLinePart("This is a vers", "A");
    songBuilder.withPart(SongPartType.INSTRUMENTAL).withLinePart("", "A").withLinePart(" ", "D");
    songBuilder.withPart(SongPartType.VERS).withLine().withLinePart("This is a second vers", "A");
    List<Song> songs = Arrays.asList(songBuilder.get());
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(false);
    File exportFile = createExportFile(textfileWriter, "exportFilterEmptyPart");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("This is a vers", StringUtils.trimRight(lines.get(0)));
    Assert.assertEquals("<" + StringUtils.trimRight(lines.get(2)) + "> is invalid (" + lines + ")", "This is a second vers", StringUtils.trimRight(lines.get(2)));


  }

  @Test
  public void exportLeftBorder () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setLeftBorder(new Double(5));
    File exportFile = createExportFile(textfileWriter, "exportLeftBorder");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("     Strength will rise as we wait upon the", StringUtils.trimRight(lines.get(0)));
    Assert.assertEquals("     Lord, we will wait upon the Lord", StringUtils.trimRight(lines.get(1)));


  }

  @Test
  public void exportUpperBorder () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setUpperBorder(new Double(1));
    File exportFile = createExportFile(textfileWriter, "exportUpperBorder");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("", lines.get(0).trim());
    Assert.assertEquals("Strength will rise as we wait upon the", lines.get(1).trim());


  }

  @Test
  public void exportChordTextDistance () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setChordTextDistance(new Double(2));
    exportConfiguration.setWithChords(true);
    File exportFile = createExportFile(textfileWriter, "exportChordTextDistance");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("G                        Gsus", lines.get(0).trim());
    Assert.assertEquals("", lines.get(1).trim());
    Assert.assertEquals("", lines.get(2).trim());
    Assert.assertEquals("Strength will rise as we wait upon the", lines.get(3).trim());


  }

  @Test
  public void exportInterLineDistance () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setInterLineDistance(new Double(2));
    File exportFile = createExportFile(textfileWriter, "exportInterLineDistance");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("Strength will rise as we wait upon the", lines.get(0).trim());
    Assert.assertEquals("", lines.get(1).trim());
    Assert.assertEquals("", lines.get(2).trim());
    Assert.assertEquals("Lord, we will wait upon the Lord", lines.get(3).trim());


  }
  @Test
  public void exportWithChords () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(true);
    File exportFile = createExportFile(textfileWriter, "exportWithChords");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("G                        Gsus", lines.get(0).trim());
    Assert.assertEquals("Strength will rise as we wait upon the", lines.get(1).trim());
    Assert.assertEquals("G             Gsus          G", lines.get(2).trim());
    Assert.assertEquals("Lord, we will wait upon the Lord", lines.get(3).trim());
    Assert.assertEquals("", lines.get(4).trim());
    Assert.assertEquals("G/H C", lines.get(5).trim());


  }

  private File createExportFile (final Exporter export, final String name) throws IOException {
    File tmpDir = Files.createTempDirectory(getClass().getSimpleName()).toFile();
    return new File (tmpDir, name + export.getSuffix());
  }

  @Test
  public void exportWithoutChords () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setPageSize(new SizeInfo(100, 100));

    File exportFile = createExportFile(textfileWriter, "exportWithoutChords");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> lines = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals("Strength will rise as we wait upon the", lines.get(0).trim());
    Assert.assertEquals("Lord, we will wait upon the Lord", lines.get(1).trim());
    Assert.assertEquals("", lines.get(2).trim());
    Assert.assertEquals("Our God,", lines.get(3).trim());
  }

  @Test
  public void exportWithChordsSmallSize () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setPageSize(new SizeInfo(40, 40));
    File exportFile = createExportFile(textfileWriter, "exportWithChords");
    textfileWriter.export(songs, exportFile, exportConfiguration);
  }

  @Test
  public void exportWithoutChordsSmallSize () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setPageSize(new SizeInfo(40, 40));
    File exportFile = createExportFile(textfileWriter, "exportWithoutChords");
    textfileWriter.export(songs, exportFile, exportConfiguration);
  }

  @Test
  public void exportWithChordsTooSmall () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithChords(true);
    exportConfiguration.setPageSize(new SizeInfo(10, 40));
    File exportFile = createExportFile(textfileWriter, "exportWithChords");
    textfileWriter.export(songs, exportFile, exportConfiguration);
  }

  @Test
  public void exportWithoutChordsTooSmall () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = new ExportConfiguration();
    exportConfiguration.initializeValues();
    exportConfiguration.setWithChords(false);
    exportConfiguration.setPageSize(new SizeInfo(10, 40));
    File exportFile = createExportFile(textfileWriter, "exportWithoutChords");
    textfileWriter.export(songs, exportFile, exportConfiguration);
  }

  @Test
  public void exportWithTitleAndId () throws IOException {
    List<Song> songs = getExportTestData();
    ExportConfiguration exportConfiguration = createExportConfiguration();
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithId(true);
    File exportFile = createExportFile(textfileWriter, "exportWithChords");
    textfileWriter.export(songs, exportFile, exportConfiguration);
    List<String> content = FileUtils.readLines(exportFile, Charset.defaultCharset());
    Assert.assertEquals ("1     Everlasting God", content.get(0).trim());
    Assert.assertTrue (content.get(1).trim().isEmpty());
    Assert.assertEquals ("Strength will rise as we wait upon the", content.get(2).trim());
  }


}
