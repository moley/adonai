package org.adonai.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.CreatePlaylistService;
import org.adonai.api.MainAction;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ReferenceStrategy;
import org.adonai.export.pdf.PdfExporter;
import org.adonai.fx.editcontent.KeyType;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongPartDescriptorStrategy;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportAction implements MainAction {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExportAction.class);

  @Override public String getIconname() {
    return "fas-file-pdf";
  }

  @Override public String getDisplayName() {
    return "Export";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return event -> {

      String name = applicationEnvironment.getCurrentScopeItem().getName().trim();
      Configuration configuration = applicationEnvironment.getCurrentConfiguration();
      List<Song> songs = applicationEnvironment.getSongsOfCurrentScope();

      LOGGER.info("Export " + name + " with " + songs.size() + " songs");
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

      LOGGER.info("Added " + exportPath.getAbsolutePath());

      //With chords
      ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(writer.getPdfDocumentBuilder().getClass());
      exportConfiguration.setWithContentPage(true);
      exportConfiguration.setWithIndexPage(true);
      exportConfiguration.setWithChords(true);
      exportConfiguration.setWithId(true);
      exportConfiguration.setWithTitle(true);
      exportConfiguration.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
      exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
      exportConfiguration.setKeyType(KeyType.CURRENT);
      exportConfiguration.setWithIndexPage(songs.size() > 1);
      exportConfiguration.setWithContentPage(songs.size() > 1);
      exportConfiguration.setStructureDistance(Double.valueOf(5));
      exportConfiguration.setWithRemarks(true);
      exportConfiguration.setRemarksStructureDistance(Double.valueOf(5));
      exportConfiguration.setRemarksRight(true);
      File exportFile = new File(exportPath, name + "_Chords.pdf");
      exportFile.getParentFile().mkdirs();
      writer.export(songs, exportFile, exportConfiguration);
      LOGGER.info("Exported with chords " + exportPath.getAbsolutePath());

      //Without chords
      writer = new PdfExporter();

      ExportConfiguration exportConfigurationNoChords = configuration.findDefaultExportConfiguration(writer.getPdfDocumentBuilder().getClass());
      exportConfigurationNoChords.setWithContentPage(true);
      exportConfigurationNoChords.setWithChords(false);
      exportConfigurationNoChords.setWithIndexPage(songs.size() > 1);
      exportConfigurationNoChords.setWithContentPage(songs.size() > 1);
      exportConfigurationNoChords.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
      exportConfigurationNoChords.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
      exportConfigurationNoChords.setStructureDistance(Double.valueOf(5));
      exportConfigurationNoChords.setWithRemarks(true);
      exportConfigurationNoChords.setRemarksStructureDistance(Double.valueOf(5));
      exportConfigurationNoChords.setRemarksRight(true);
      File exportFileNoChords = new File(exportPath, name + ".pdf");
      exportFileNoChords.getParentFile().mkdirs();
      writer.export(songs, exportFileNoChords, exportConfigurationNoChords);
      LOGGER.info("Exported without chords " + exportPath.getAbsolutePath());

      //With capo and chords
      writer = new PdfExporter();
      ExportConfiguration exportConfigurationCapoAndChords = configuration.findDefaultExportConfiguration(writer.getPdfDocumentBuilder().getClass());
      exportConfigurationCapoAndChords.setWithContentPage(true);
      exportConfigurationCapoAndChords.setWithIndexPage(true);
      exportConfigurationCapoAndChords.setWithChords(true);
      exportConfigurationCapoAndChords.setWithId(true);
      exportConfigurationCapoAndChords.setWithTitle(true);
      exportConfigurationCapoAndChords.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
      exportConfigurationCapoAndChords.setKeyType(KeyType.CURRENT_CAPO);
      exportConfigurationCapoAndChords.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
      exportConfigurationCapoAndChords.setWithIndexPage(songs.size() > 1);
      exportConfigurationCapoAndChords.setWithContentPage(songs.size() > 1);
      exportConfigurationCapoAndChords.setStructureDistance(Double.valueOf(5));
      exportConfigurationCapoAndChords.setWithRemarks(true);
      exportConfigurationCapoAndChords.setRemarksStructureDistance(Double.valueOf(5));
      exportConfigurationCapoAndChords.setRemarksRight(true);
      File exportConfigurationCapoAndChordsFile = new File(exportPath, name + "_Chords_Capo.pdf");
      exportFile.getParentFile().mkdirs();
      writer.export(songs, exportConfigurationCapoAndChordsFile, exportConfigurationCapoAndChords);
      LOGGER.info("Exported with chords and capo " + exportPath.getAbsolutePath());

      File songsPath = new File (exportPath, "mp3s");
      for (Song next: songs) {
        for (Additional nextAdditional: next.getAdditionals()) {
          if (nextAdditional.getAdditionalType().equals(AdditionalType.AUDIO)) {

            String filename = String.format("%03d - %s.mp3", next.getId(), next.getTitle());
            File destFile = new File (songsPath, filename);
            try {
              FileUtils.copyFile(new File (nextAdditional.getLink()), destFile);
            } catch (IOException e) {
              LOGGER.error("Error copying file " +nextAdditional.getLink() + ":" + e.getLocalizedMessage());
            }
          }
        }
      }
      LOGGER.info("Copied mp3s");

      File iTunesPlayList = new File (exportPath, name + ".m3u");
      CreatePlaylistService createPlaylistService = new CreatePlaylistService();
      try {
        createPlaylistService.create(iTunesPlayList, songs);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
      LOGGER.info("Created playlist");
    };
  }

  @Override public boolean isVisible() {
    return true;
  }
}
