package org.adonai.export;

import org.adonai.model.Song;

import java.io.File;
import java.util.Collection;

public interface Exporter {

  /**
   * exports a collection of items in a specific format, saves the results in the parameterized exportfile
   *
   * @param songs
   * @param exportFile
   * @param config
   * @throws Exception
   */
  void export (final Collection<Song> songs, final File exportFile, final ExportConfiguration config);

  /**
   * gets the suffix for this export
   * @return
   */
  String getSuffix ();


}