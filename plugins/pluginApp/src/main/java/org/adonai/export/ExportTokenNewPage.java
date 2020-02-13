package org.adonai.export;

import org.adonai.model.Song;

public class ExportTokenNewPage extends ExportToken {

  public ExportTokenNewPage (final Song song) {
    super (song, null, null, ExportTokenType.NEW_PAGE);
  }
}
