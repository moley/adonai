package org.adonai.services;

import org.adonai.model.Song;
import org.adonai.model.SongBook;

public class RemoveSongService {

  public Song removeSong (final Song removeSong, final SongBook songBook) {
    int indexSong = songBook.getSongs().indexOf(removeSong);
    songBook.getSongs().remove(removeSong);

    int nextSong = songBook.getSongs().size() > indexSong ? indexSong : songBook.getSongs().size() - 1;

    return songBook.getSongs().get(nextSong);
  }


}
