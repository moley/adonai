package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class MergeLineService {

  /**
   * moves the current line to last line if
   * positioned at beginning of first line part and
   * @return line part to focus, not <code>null<</code>
   */
  public LinePart mergeLine (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongPart songPart = songCursor.getCurrentSongPart();
    Line currentLine = songCursor.getCurrentLine();
    LinePart currentLinePart = songCursor.getCurrentLinePart();
    int lineIndex = songPart.getIndex(currentLine);

    if (songCursor.getPositionInLinePart() != 0)
      return songCursor.getCurrentLinePart();

    if (lineIndex > 0) {
      //merge parts of current line to previous line
      Line previousLine = songPart.getPreviousLine(currentLine);
      previousLine.getLineParts().addAll(currentLine.getLineParts());
      songPart.getLines().remove(currentLine);
    } else {

      if (! songPart.equals(song.getFirstSongPart())) {
        //connect all lines of current song part to previous songpart
        SongPart previousSongPart = song.getPreviousSongPart(songPart);
        previousSongPart.getLines().addAll(songPart.getLines());
        song.getSongParts().remove(songPart);
      }

    }

    return currentLinePart;

  }
}
