package org.adonai.export.presentation;

import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportEngine;
import org.adonai.export.ExportException;
import org.adonai.export.Exporter;
import org.adonai.model.Song;

import java.io.File;
import java.util.Collection;

public class PresentationExporter implements Exporter {


  public void export(final Collection<Song> songs, final File exportFile, final ExportConfiguration config) throws ExportException {

    PresentationDocumentBuilder pdfDocumentBuilder = new PresentationDocumentBuilder();
    ExportEngine exportEngine = new ExportEngine();
    exportEngine.exportSongs(songs, config, exportFile, pdfDocumentBuilder);


  }

  @Override
  public String getSuffix() {
    return null;
  }


}