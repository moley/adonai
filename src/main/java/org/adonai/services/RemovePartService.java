package org.adonai.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongStructItem;

public class RemovePartService {

  /**
   * removes a complete part from a song
   * @param songCursor    current position
   * @return linepart to focus afterwards
   */
  public SongStructItem removePart (final SongCursor songCursor) {

    // if single line, then clear the part, else delete the part
    Song song = songCursor.getCurrentSong();
    SongStructItem removeStructItem = songCursor.getCurrentSongStructItem();
    SongPart removeSongPart = songCursor.getCurrentSongPart();
    SongStructItem focusStructItem;

    if (song.getStructItems().size() == 1) {
      removeSongPart.clear();
      focusStructItem = removeStructItem;
    }
    else {
      SongStructItem previousSongPart = song.getPreviousStructItem(removeStructItem);
      if (previousSongPart != null) {
        focusStructItem = previousSongPart;
      }
      else
        focusStructItem = song.getNextStructItem(removeStructItem);

      song.getStructItems().remove(removeStructItem);
    }

    //Remove not used parts
    HashSet<String> used = new HashSet<String>();
    for (SongStructItem next: song.getStructItems()) {
      used.add(next.getPartId());
    }

    List<SongPart> unusedParts = new ArrayList<SongPart>();
    for (SongPart nextPart: song.getSongParts()) {
      if (! used.contains(nextPart.getId()))
        unusedParts.add(nextPart);
    }
    song.getSongParts().removeAll(unusedParts);

    return focusStructItem;
  }
}
