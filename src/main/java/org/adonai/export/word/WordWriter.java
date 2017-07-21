package org.adonai.export.word;

import org.adonai.export.ExportException;
import org.adonai.export.Exporter;
import org.adonai.export.ExportConfiguration;
import org.adonai.model.Song;

import java.io.File;
import java.util.Collection;

public class WordWriter implements Exporter {
  @Override
  public void export(Collection<Song> songs, File exportFile, ExportConfiguration config) throws ExportException {

  }

  @Override
  public String getSuffix() {
    return ".doc";
  }
}
