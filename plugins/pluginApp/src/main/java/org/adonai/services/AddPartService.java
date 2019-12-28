package org.adonai.services;

import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class AddPartService {


  /**
   * add a complete song part to a song
   * @param songCursor  current position
   * @return linepart to focus afterwards
   */
  public SongPart addPartBefore (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongPart currentSongPart = songCursor.getCurrentSongPart();
    int index = song.getSongParts().indexOf(currentSongPart);
    SongPart newSongpart = new SongPart();
    newSongpart.newLine();
    song.getSongParts().add(index , newSongpart);

    return newSongpart;
  }

  /**
   * add a complete song part to a song
   * @param songCursor  current position
   * @return linepart to focus afterwards
   */
  public SongPart addPartAfter (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongPart currentSongPart = songCursor.getCurrentSongPart();
    int index = song.getSongParts().indexOf(currentSongPart);
    SongPart newSongpart = new SongPart();
    newSongpart.newLine();
    song.getSongParts().add(index + 1, newSongpart);

    return newSongpart;
  }
}
