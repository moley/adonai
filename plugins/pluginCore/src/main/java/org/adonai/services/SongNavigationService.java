package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongStructItem;

public class SongNavigationService {

  public LinePart stepToNextPart (final SongCursor songCursor) {

    SongStructItem currentStructItem = songCursor.getCurrentSongStructItem();
    Song currentSong = songCursor.getCurrentSong();
    if (currentStructItem.equals(currentSong.getLastStructItem()))
      return songCursor.getCurrentLinePart();
    else {
      SongStructItem nextStructItem = currentSong.getNextStructItem(currentStructItem);
      SongPart nextSongPart = currentSong.findSongPart(nextStructItem);
      return nextSongPart.getFirstLine().getFirstLinePart();
    }
  }

  public LinePart stepToPreviousPart (final SongCursor songCursor) {
    SongStructItem currentStructItem = songCursor.getCurrentSongStructItem();
    Song currentSong = songCursor.getCurrentSong();
    if (currentStructItem.equals(currentSong.getFirstStructItem()))
      return songCursor.getCurrentLinePart();
    else {
      SongStructItem nextStructItem = currentSong.getPreviousStructItem(currentStructItem);
      SongPart nextSongPart = currentSong.findSongPart(nextStructItem);
      return nextSongPart.getFirstLine().getFirstLinePart();
    }

  }

  /**
   * steps to previous line part
   * - previous line if we are in first part of line
   * - previous part if we are in first part of first line
   * @param songCursor  current position
   * @return line part to focus
   */
  public LinePart stepToPreviousLinePart (final SongCursor songCursor) {
    LinePart linePart = songCursor.getCurrentLinePart();
    Line line = songCursor.getCurrentLine();
    Song song = songCursor.getCurrentSong();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    SongPart songPart = songCursor.getCurrentSongPart();

    if (! linePart.equals(line.getFirstLinePart())) { // if not last linepart in line
      return line.getPreviousLinePart(linePart);
    }
    else {
      if (!line.equals(songPart.getFirstLine())) {
        return songPart.getPreviousLine(line).getLastLinePart();
      }
      else if (!songStructItem.equals(song.getFirstStructItem())){
        SongStructItem previousStructItem = song.getPreviousStructItem(songStructItem);
        SongPart previousSongPart = song.findSongPart(previousStructItem);
        return previousSongPart.getLastLine().getLastLinePart();
      }
    }
    return linePart;

  }

  /**
   * steps to the next line part
   * - next line if we are in last part of line
   * - next part if we are in last part of last line
   * @param songCursor  current position
   * @return focusable linepart
   */
  public LinePart stepToNextLinePart (final SongCursor songCursor) {
    LinePart linePart = songCursor.getCurrentLinePart();
    Line line = songCursor.getCurrentLine();
    Song song = songCursor.getCurrentSong();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    SongPart songPart = songCursor.getCurrentSongPart();

    if (! linePart.equals(line.getLastLinePart())) { // if not last linepart in line
      return line.getNextLinePart(linePart);
    }
    else {
      if (!line.equals(songPart.getLastLine())) {
        return songPart.getNextLine(line).getFirstLinePart();
      }
      else if (!songStructItem.equals(song.getLastStructItem())){
        SongStructItem nextStructItem = song.getNextStructItem(songStructItem);
        SongPart nextSongPart = song.findSongPart(nextStructItem);
        return nextSongPart.getFirstLine().getFirstLinePart();
      }
    }
    return linePart;
  }

  public LinePart stepToPreviousLine(SongCursor songCursor) {
    Line line = songCursor.getCurrentLine();
    SongPart songPart = songCursor.getCurrentSongPart();

    if (line.equals(songPart.getFirstLine()))
      return line.getFirstLinePart();
    else
    return songPart.getPreviousLine(line).getFirstLinePart();
  }

  public LinePart stepToNextLine(SongCursor songCursor) {
    Line line = songCursor.getCurrentLine();
    SongPart songPart = songCursor.getCurrentSongPart();

    if (line.equals(songPart.getLastLine()))
      return line.getFirstLinePart();
    else
      return songPart.getNextLine(line).getFirstLinePart();
  }
}
