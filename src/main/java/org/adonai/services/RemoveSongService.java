package org.adonai.services;

import java.util.logging.Logger;
import org.adonai.model.Song;
import org.adonai.model.SongBook;

public class RemoveSongService {

  private static final Logger LOGGER = Logger.getLogger(RemoveSongService.class.getName());

  public Song removeSong (final Song removeSong, final SongBook songBook) {
    LOGGER.info("Remove song " + removeSong.getName());
    LOGGER.info("from songbook with " + songBook.getSongs().size() + " songs");

    int indexSong = songBook.getSongs().indexOf(removeSong);
    songBook.getSongs().remove(removeSong);
    LOGGER.info("Removed song from songbook at index " + indexSong + "(size afterwards: " + songBook.getSongs().size());

    int nextSong = songBook.getSongs().size() > indexSong ? indexSong : songBook.getSongs().size() - 1;
    LOGGER.info("Select " + nextSong);

    return songBook.getSongs().get(nextSong);
  }


}
