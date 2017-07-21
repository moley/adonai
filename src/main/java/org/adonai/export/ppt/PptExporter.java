package org.adonai.export.ppt;

import org.adonai.export.ExportEngine;
import org.adonai.export.ExportException;
import org.adonai.export.Exporter;
import org.adonai.export.ExportConfiguration;
import org.adonai.model.Song;

import java.io.File;
import java.util.Collection;

/**
 * Created by OleyMa on 15.09.16.
 */
public class PptExporter implements Exporter {


  public void export(final Collection<Song> songs, final File exportFile, final ExportConfiguration config) throws ExportException {

    PptDocumentBuilder pdfDocumentBuilder = new PptDocumentBuilder();
    ExportEngine exportEngine = new ExportEngine();
    exportEngine.exportSongs(songs, config, exportFile, pdfDocumentBuilder);


  }

  @Override
  public String getSuffix() {
    return ".ppt";
  }



}
