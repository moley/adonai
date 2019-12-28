package org.adonai.services;

import org.adonai.model.Song;
import org.adonai.model.SongBook;

public class RenumberService {

  public void renumber (final SongBook songbook) {
    for (int i = 0; i < songbook.getSongs().size(); i++) {
      Song nextSong = songbook.getSongs().get(i);
      nextSong.setId(i + 1);
    }
  }
}
