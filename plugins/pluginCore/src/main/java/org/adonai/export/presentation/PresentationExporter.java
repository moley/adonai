package org.adonai.export.presentation;

import java.io.File;
import java.util.Collection;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.SizeInfo;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportEngine;
import org.adonai.export.ExportTokenContainer;
import org.adonai.export.Exporter;
import org.adonai.model.Song;

public class PresentationExporter implements Exporter {

  private PresentationDocumentBuilder presentationDocumentBuilder;

  public PresentationExporter (final SizeInfo sizeInfo, EventHandler<ActionEvent> onSongContentChange) {
    presentationDocumentBuilder = new PresentationDocumentBuilder();
    presentationDocumentBuilder.setOnSongContentChange(onSongContentChange);
    presentationDocumentBuilder.setSizeInfo(sizeInfo);
  }

  @Override public void export(Collection<Song> songs, File exportFile, ExportConfiguration config) {
    ExportEngine exportEngine = new ExportEngine();
    exportEngine.exportSongs(songs, config, exportFile, presentationDocumentBuilder);
  }

  @Override public String getSuffix() {
    return null;
  }

  public ExportTokenContainer getExportTokenContainer () {
    return presentationDocumentBuilder.getExportTokenContainer();
  }

  public List<Page> getPanes () {
    return presentationDocumentBuilder.getPages();
  }

}