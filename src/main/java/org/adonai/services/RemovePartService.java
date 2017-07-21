package org.adonai.services;

import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class RemovePartService {

  /**
   * removes a complete part from a song
   * @param songCursor    current position
   * @return linepart to focus afterwards
   */
  public SongPart removePart (final SongCursor songCursor) {

    // wenn einzige Zeile, dann Part clearen, ansonsten löschen
    Song song = songCursor.getCurrentSong();
    SongPart removeSongPart = songCursor.getCurrentSongPart();
    SongPart focusLinePart;

    if (song.getSongParts().size() == 1) {
      removeSongPart.clear();
      focusLinePart = removeSongPart;
    }
    else {
      SongPart previousSongPart = song.getPreviousSongPart(removeSongPart);
      if (previousSongPart != null) {
        focusLinePart = previousSongPart;
      }
      else
        focusLinePart = song.getNextSongPart(removeSongPart);

      song.getSongParts().remove(removeSongPart);
    }

    //wenn leer, dann leeren SongPart einfüllen

    return focusLinePart;
  }
}
