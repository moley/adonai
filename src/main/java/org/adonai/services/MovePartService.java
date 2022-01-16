package org.adonai.services;

import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongStructItem;

public class MovePartService {

  public LinePart movePartUp (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();

    if (! songStructItem.equals(song.getFirstStructItem())) {
      SongStructItem previousStructItem = song.getPreviousStructItem(songStructItem);
      int indexPreviousSongPart = song.getIndex(previousStructItem);
      song.getStructItems().remove(songStructItem);
      song.getStructItems().add(indexPreviousSongPart, songStructItem);
    }
    return songCursor.getCurrentLinePart();
  }

  public LinePart movePartDown (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();

    if (! songStructItem.equals(song.getLastStructItem())) {
      SongStructItem nextSongPart = song.getNextStructItem(songStructItem);
      int indexNextSongPart = song.getIndex(nextSongPart);
      song.getStructItems().remove(songStructItem);
      song.getStructItems().add(indexNextSongPart, songStructItem);

    }
    return songCursor.getCurrentLinePart();
  }
}
