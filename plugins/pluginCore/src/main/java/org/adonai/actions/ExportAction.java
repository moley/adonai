package org.adonai.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.CreatePlaylistService;
import org.adonai.api.MainAction;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportConfigurationMerger;
import org.adonai.export.ReferenceStrategy;
import org.adonai.export.pdf.PdfExporter;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongPartDescriptorStrategy;
import org.apache.commons.io.FileUtils;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
public class ExportAction implements MainAction {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExportAction.class);

  @Override public String getIconname() {
    return "fas-file-pdf";
  }

  @Override public String getDisplayName() {
    return "Export";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        String name = applicationEnvironment.getCurrentScopeItem().getName();
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
        exportConfiguration.setReferenceStrategy(ReferenceStrategy.SHOW_STRUCTURE);
        exportConfiguration.setSongPartDescriptorType(SongPartDescriptorStrategy.LONG);
        exportConfiguration.setWithIndexPage(songs.size() > 1);
        exportConfiguration.setWithContentPage(songs.size() > 1);
        exportConfiguration.setStructureDistance(new Double(5));
        //exportConfiguration.setInterPartDistance(new Double(5));

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
        exportConfigurationNoChords.setStructureDistance(new Double(5));


        File exportFileNoChords = new File(exportPath, name + ".pdf");
        exportFileNoChords.getParentFile().mkdirs();
        writer.export(songs, exportFileNoChords, exportConfigurationNoChords);
        LOGGER.info("Exported without chords " + exportPath.getAbsolutePath());

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
        LOGGER.info("Copied mp3s");

        File iTunesPlayList = new File (exportPath, name + ".m3u");
        CreatePlaylistService createPlaylistService = new CreatePlaylistService();
        try {
          createPlaylistService.create(iTunesPlayList, songs);
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
        LOGGER.info("Created playlist");
      }
    };
  }
}
