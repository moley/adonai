package org.adonai.export;

import org.adonai.SizeInfo;
import org.adonai.export.pdf.PdfDocumentBuilder;
import org.adonai.export.txtfile.TextfileDocumentBuilder;
import org.adonai.model.SongPartDescriptorStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportConfigurationMergerTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExportConfigurationMergerTest.class);

  @Test(expected = IllegalStateException.class)
  public void mergeDifferentTypes () {
    ExportConfiguration defaultConfiguration = new ExportConfiguration();
    defaultConfiguration.setDocumentBuilderClass(PdfDocumentBuilder.class.getName());

    ExportConfiguration userConfiguration = new ExportConfiguration();
    userConfiguration.setDocumentBuilderClass(TextfileDocumentBuilder.class.getName());

    ExportConfigurationMerger exportConfigurationMerger = new ExportConfigurationMerger();
    exportConfigurationMerger.getMergedExportConfiguration(defaultConfiguration, userConfiguration);

  }

  @Test
  public void mergeFromUserTrue () {
    ExportConfiguration defaultConfiguration = new ExportConfiguration();
    defaultConfiguration.setName("defaultConfiguration");
    defaultConfiguration.setId("1");
    defaultConfiguration.setWithChords(Boolean.FALSE);
    defaultConfiguration.setWithContentPage(Boolean.FALSE);
    defaultConfiguration.setWithIndexPage(Boolean.FALSE);
    defaultConfiguration.setNewPageStrategy(NewPageStrategy.NEVER);
    defaultConfiguration.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
    defaultConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    defaultConfiguration.setPageSize(new SizeInfo(640, 400));
    defaultConfiguration.setWithTitle(Boolean.FALSE);
    defaultConfiguration.setInterPartDistance(5.0);
    defaultConfiguration.setInterSongDistance(5.0);
    defaultConfiguration.setInterLineDistance(5.0);
    defaultConfiguration.setStructureDistance(5.0);
    defaultConfiguration.setChordTextDistance(5.0);
    defaultConfiguration.setTitleSongDistance(5.0);
    defaultConfiguration.setUpperBorder(5.0);
    defaultConfiguration.setLeftBorder(5.0);
    defaultConfiguration.setLowerBorder(5.0);
    defaultConfiguration.setMinimalChordDistance(5.0);
    defaultConfiguration.setOpenPreview(Boolean.FALSE);

    ExportConfiguration userConfiguration = new ExportConfiguration();
    userConfiguration.setName("userConfiguration");
    userConfiguration.setId("2");
    userConfiguration.setWithChords(Boolean.TRUE);
    userConfiguration.setWithContentPage(Boolean.TRUE);
    userConfiguration.setWithIndexPage(Boolean.TRUE);
    userConfiguration.setNewPageStrategy(NewPageStrategy.PER_PART);
    userConfiguration.setReferenceStrategy(ReferenceStrategy.HIDE);
    userConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.SHORT);
    userConfiguration.setPageSize(new SizeInfo(800, 600));
    userConfiguration.setWithTitle(Boolean.TRUE);
    userConfiguration.setInterPartDistance(15.0);
    userConfiguration.setInterSongDistance(15.0);
    userConfiguration.setInterLineDistance(15.0);
    userConfiguration.setStructureDistance(15.0);
    userConfiguration.setChordTextDistance(15.0);
    userConfiguration.setTitleSongDistance(15.0);
    userConfiguration.setUpperBorder(15.0);
    userConfiguration.setLeftBorder(15.0);
    userConfiguration.setLowerBorder(15.0);
    userConfiguration.setMinimalChordDistance(15.0);
    userConfiguration.setOpenPreview(Boolean.TRUE);


    ExportConfigurationMerger exportConfigurationMerger = new ExportConfigurationMerger();
    ExportConfiguration mergedConfiguration = exportConfigurationMerger.getMergedExportConfiguration(defaultConfiguration, userConfiguration);

    LOGGER.info("Merged configuration: " + mergedConfiguration);
    Assert.assertEquals ("With chords invalid", Boolean.TRUE, mergedConfiguration.isWithChords());
    Assert.assertEquals("With content page invalid", Boolean.TRUE, mergedConfiguration.isWithContentPage());
    Assert.assertEquals("With index page invalid", Boolean.TRUE, mergedConfiguration.isWithIndexPage());
    Assert.assertEquals ("New page strategy invalid", NewPageStrategy.PER_PART, mergedConfiguration.getNewPageStrategy());
    Assert.assertEquals ("Reference strategy invalid", ReferenceStrategy.HIDE, mergedConfiguration.getReferenceStrategy());
    Assert.assertEquals("Songpart descriptor strategy invalid", SongPartDescriptorStrategy.SHORT, mergedConfiguration.getSongPartDescriptorType());
    Assert.assertEquals ("Pagesize invalid", new SizeInfo(800, 600), mergedConfiguration.getPageSize());
    Assert.assertEquals("With title invalid", Boolean.TRUE, mergedConfiguration.getWithTitle());

    Assert.assertEquals("Interpart distance invalid", Double.valueOf(15.0), mergedConfiguration.getInterPartDistance());
    Assert.assertEquals("Intersong distance invalid", Double.valueOf(15.0), mergedConfiguration.getInterSongDistance());
    Assert.assertEquals("InterLine distance invalid", Double.valueOf(15.0), mergedConfiguration.getInterLineDistance());
    Assert.assertEquals("Structure distance invalid", Double.valueOf(15.0), mergedConfiguration.getStructureDistance());
    Assert.assertEquals("ChordText distance invalid", Double.valueOf(15.0), mergedConfiguration.getChordTextDistance());
    Assert.assertEquals("TitleSong distance invalid", Double.valueOf(15.0), mergedConfiguration.getTitleSongDistance());
    Assert.assertEquals("Upper border invalid", Double.valueOf(15.0), mergedConfiguration.getUpperBorder());
    Assert.assertEquals("Left border invalid", Double.valueOf(15.0), mergedConfiguration.getLeftBorder());
    Assert.assertEquals("Lower border invalid", Double.valueOf(15.0), mergedConfiguration.getLowerBorder());
    Assert.assertEquals("Minimal chorddistance invalid", Double.valueOf(15.0), mergedConfiguration.getMinimalChordDistance());
    Assert.assertEquals ("OpenPreview invalid", Boolean.TRUE, mergedConfiguration.isOpenPreview());

  }

  @Test
  public void mergeFromUserFalse () {
    ExportConfiguration defaultConfiguration = new ExportConfiguration();
    defaultConfiguration.setName("defaultConfiguration");
    defaultConfiguration.setId("1");
    defaultConfiguration.setWithChords(Boolean.TRUE);
    defaultConfiguration.setWithContentPage(Boolean.TRUE);
    defaultConfiguration.setWithIndexPage(Boolean.TRUE);
    defaultConfiguration.setWithTitle(Boolean.TRUE);
    defaultConfiguration.setOpenPreview(Boolean.TRUE);

    ExportConfiguration userConfiguration = new ExportConfiguration();
    userConfiguration.setName("userConfiguration");
    userConfiguration.setId("2");

    ExportConfigurationMerger exportConfigurationMerger = new ExportConfigurationMerger();
    ExportConfiguration mergedConfiguration = exportConfigurationMerger.getMergedExportConfiguration(defaultConfiguration, userConfiguration);

    System.out.println (mergedConfiguration);
    Assert.assertEquals ("With chords invalid", Boolean.FALSE, userConfiguration.isWithChords());
    Assert.assertEquals("With content page invalid", Boolean.FALSE, userConfiguration.isWithContentPage());
    Assert.assertEquals("With index page invalid", Boolean.FALSE, userConfiguration.isWithIndexPage());
    Assert.assertEquals("With title invalid", Boolean.FALSE, userConfiguration.getWithTitle());
    Assert.assertEquals("OpenPreview invalid", Boolean.FALSE, userConfiguration.isOpenPreview());

  }

  @Test
  public void mergeFromDefault () {
    ExportConfiguration defaultConfiguration = new ExportConfiguration();
    defaultConfiguration.setName("defaultConfiguration");
    defaultConfiguration.setId("1");
    defaultConfiguration.setWithChords(Boolean.TRUE);
    defaultConfiguration.setWithContentPage(Boolean.TRUE);
    defaultConfiguration.setWithIndexPage(Boolean.TRUE);
    defaultConfiguration.setOpenPreview(Boolean.TRUE);
    defaultConfiguration.setNewPageStrategy(NewPageStrategy.NEVER);
    defaultConfiguration.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
    defaultConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    defaultConfiguration.setPageSize(new SizeInfo(640, 480));
    defaultConfiguration.setInterPartDistance(5.0);
    defaultConfiguration.setInterSongDistance(5.0);
    defaultConfiguration.setInterLineDistance(5.0);
    defaultConfiguration.setStructureDistance(5.0);
    defaultConfiguration.setChordTextDistance(5.0);
    defaultConfiguration.setTitleSongDistance(5.0);
    defaultConfiguration.setUpperBorder(5.0);
    defaultConfiguration.setLeftBorder(5.0);
    defaultConfiguration.setLowerBorder(5.0);
    defaultConfiguration.setMinimalChordDistance(5.0);



    ExportConfiguration userConfiguration = new ExportConfiguration();
    userConfiguration.setName("userConfiguration");
    userConfiguration.setId("2");
    userConfiguration.setNewPageStrategy(null);
    userConfiguration.setReferenceStrategy(null);
    userConfiguration.setSongPartDescriptorType(null);

    ExportConfigurationMerger exportConfigurationMerger = new ExportConfigurationMerger();
    ExportConfiguration mergedConfiguration = exportConfigurationMerger.getMergedExportConfiguration(defaultConfiguration, userConfiguration);

    System.out.println (mergedConfiguration);
    Assert.assertEquals ("With chords invalid", Boolean.TRUE, mergedConfiguration.isWithChords());
    Assert.assertEquals ("With content page invalid", Boolean.TRUE, mergedConfiguration.isWithChords());
    Assert.assertEquals ("With index page invalid", Boolean.TRUE, mergedConfiguration.isWithIndexPage());
    Assert.assertEquals ("New page strategy invalid", NewPageStrategy.NEVER, mergedConfiguration.getNewPageStrategy());
    Assert.assertEquals ("Reference strategy invalid", ReferenceStrategy.SHOW_STRUCTURE, mergedConfiguration.getReferenceStrategy());
    Assert.assertEquals ("SongpartDescriptorType invalid", SongPartDescriptorStrategy.LONG, mergedConfiguration.getSongPartDescriptorType());
    Assert.assertEquals ("Pagesize invalid", new SizeInfo(640, 480), mergedConfiguration.getPageSize());
    Assert.assertEquals("Interpart distance invalid", Double.valueOf(5.0), mergedConfiguration.getInterPartDistance());
    Assert.assertEquals("Intersong distance invalid", Double.valueOf(5.0), mergedConfiguration.getInterSongDistance());
    Assert.assertEquals("InterLine distance invalid", Double.valueOf(5.0), mergedConfiguration.getInterLineDistance());
    Assert.assertEquals("Structure distance invalid", Double.valueOf(5.0), mergedConfiguration.getStructureDistance());
    Assert.assertEquals("ChordText distance invalid", Double.valueOf(5.0), mergedConfiguration.getChordTextDistance());
    Assert.assertEquals("TitleSong distance invalid", Double.valueOf(5.0), mergedConfiguration.getTitleSongDistance());
    Assert.assertEquals("Upper border invalid", Double.valueOf(5.0), mergedConfiguration.getUpperBorder());
    Assert.assertEquals("Left border invalid", Double.valueOf(5.0), mergedConfiguration.getLeftBorder());
    Assert.assertEquals("Lower border invalid", Double.valueOf(5.0), mergedConfiguration.getLowerBorder());
    Assert.assertEquals("Minimal chorddistance invalid", Double.valueOf(5.0), mergedConfiguration.getMinimalChordDistance());
    Assert.assertEquals ("OpenPreview invalid", Boolean.TRUE, mergedConfiguration.isOpenPreview());


  }
}
