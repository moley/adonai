package org.adonai.services;

import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class MovePartService {

  public LinePart movePartUp (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongPart songPart = songCursor.getCurrentSongPart();

    if (! songPart.equals(song.getFirstSongPart())) {
      SongPart previousSongPart = song.getPreviousSongPart(songPart);
      int indexPreviousSongPart = song.getIndex(previousSongPart);
      song.getSongParts().remove(songPart);
      song.getSongParts().add(indexPreviousSongPart, songPart);
    }
    return songCursor.getCurrentLinePart();
  }

  public LinePart movePartDown (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongPart songPart = songCursor.getCurrentSongPart();

    if (! songPart.equals(song.getLastSongPart())) {
      SongPart nextSongPart = song.getNextSongPart(songPart);
      int indexNextSongPart = song.getIndex(nextSongPart);
      song.getSongParts().remove(songPart);
      song.getSongParts().add(indexNextSongPart, songPart);

    }
    return songCursor.getCurrentLinePart();
  }
}
