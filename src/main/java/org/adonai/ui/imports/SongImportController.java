package org.adonai.ui.imports;

import org.adonai.model.SongBook;
import org.adonai.model.Song;

public class SongImportController {



  public SongBook getSongBook() {
    return songBook;
  }

  public void setSongBook(SongBook songBook) {
    this.songBook = songBook;
  }

  private SongBook songBook;

  public Song getSongToImport() {
    return songToImport;
  }

  public void setSongToImport(Song songToImport) {
    this.songToImport = songToImport;
  }

  Song songToImport;


}
