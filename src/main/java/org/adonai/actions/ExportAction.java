package org.adonai.actions;

import java.io.IOException;
import org.adonai.CreatePlaylistService;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportConfigurationMerger;
import org.adonai.export.ExportException;
import org.adonai.export.ReferenceStrategy;
import org.adonai.export.pdf.PdfExporter;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongPartDescriptorStrategy;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

public class ExportAction {

  private ExportConfigurationMerger exportConfigurationMerger = new ExportConfigurationMerger();


  public void export (Configuration configuration, Collection<Song> songs,
                      String name) {
    PdfExporter writer = new PdfExporter();

    File exportPath = new File (configuration.getExportPathAsFile(), name);
    exportPath.mkdirs();
    if (exportPath.exists()) {
      try {
        FileUtils.deleteDirectory(exportPath);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
    }

    //With chords
    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(writer.getPdfDocumentBuilder().getClass());
    exportConfiguration.setWithContentPage(true);
    exportConfiguration.setWithChords(true);
    exportConfiguration.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
    exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfiguration.setStructureDistance(new Double(5));

    File exportFile = new File(exportPath, name + "_Chords.pdf");
    exportFile.getParentFile().mkdirs();
    try {
      writer.export(songs, exportFile, exportConfiguration);
    } catch (ExportException e) {
      throw new IllegalStateException(e);
    }

    //Without chords
    writer = new PdfExporter();

    ExportConfiguration exportConfigurationNoChords = configuration.findDefaultExportConfiguration(writer.getPdfDocumentBuilder().getClass());
    exportConfigurationNoChords.setWithContentPage(true);
    exportConfigurationNoChords.setWithChords(false);
    exportConfigurationNoChords.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
    exportConfigurationNoChords.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
    exportConfigurationNoChords.setStructureDistance(new Double(5));

    File exportFileNoChords = new File(exportPath, name + ".pdf");
    exportFileNoChords.getParentFile().mkdirs();
    try {
      writer.export(songs, exportFileNoChords, exportConfigurationNoChords);
    } catch (ExportException e) {
      throw new IllegalStateException(e);
    }

    File songsPath = new File (exportPath, "mp3s");
    for (Song next: songs) {
      for (Additional nextAdditional: next.getAdditionals()) {
        if (nextAdditional.getAdditionalType().equals(AdditionalType.AUDIO)) {

          String filename = String.format("%03d - %s.mp3", next.getId(), next.getTitle());
          File destFile = new File (songsPath, filename);
          try {
            FileUtils.copyFile(new File (nextAdditional.getLink()), destFile);
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }
      }
    }

    File iTunesPlayList = new File (exportPath, name + ".m3u");
    CreatePlaylistService createPlaylistService = new CreatePlaylistService();
    try {
      createPlaylistService.create(iTunesPlayList, songs);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(new StringSelection(configuration.getExportPathAsFile().getAbsolutePath()), null);
  }
}
