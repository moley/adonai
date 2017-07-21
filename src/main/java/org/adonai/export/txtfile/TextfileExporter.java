package org.adonai.export.txtfile;

import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportEngine;
import org.adonai.export.ExportException;
import org.adonai.export.Exporter;
import org.adonai.model.Song;

import java.io.File;
import java.util.Collection;

public class TextfileExporter implements Exporter {
  @Override
  public void export(Collection<Song> songs, File exportFile, ExportConfiguration config) throws ExportException {

    TextfileDocumentBuilder text = new TextfileDocumentBuilder();
    ExportEngine exportEngine = new ExportEngine();
    exportEngine.exportSongs(songs, config, exportFile, text);

  }

  @Override
  public String getSuffix() {
    return ".txt";
  }


}
