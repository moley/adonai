package org.adonai.services;

import java.util.logging.Logger;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class SongCursor {

  private static final Logger LOGGER = Logger.getLogger(SongCursor.class.getName());


  private Song currentSong;

  private SongPart currentSongPart;

  private Line currentLine;

  private LinePart currentLinePart;

  private Integer positionInLinePart;

  public SongCursor () {

  }

  public SongCursor (final Song song, final int part, final int line, final int linepart, int positionInLinePart) {
    LOGGER.info("Create song cursor for song " + song.getTitle () + "(" + part + "-" + line + "-" + linepart + "-" + positionInLinePart + ")");
    currentSong = song;
    currentSongPart = ! song.getSongParts().isEmpty() ? song.getSongParts().get(part):null;
    currentLine = (currentSongPart != null && !currentSongPart.getLines().isEmpty()) ? currentSongPart.getLines().get(line): null;
    currentLinePart = (currentLine != null && !currentLine.getLineParts().isEmpty()) ? currentLine.getLineParts().get(linepart): null;
    this.positionInLinePart = positionInLinePart;
  }


  public Song getCurrentSong() {
    return currentSong;
  }

  public void setCurrentSong(Song currentSong) {
    this.currentSong = currentSong;
  }

  public SongPart getCurrentSongPart() {
    return currentSongPart;
  }

  public void setCurrentSongPart(SongPart currentSongPart) {
    this.currentSongPart = currentSongPart;
  }

  public Line getCurrentLine() {
    return currentLine;
  }

  public void setCurrentLine(Line currentLine) {
    this.currentLine = currentLine;
  }

  public LinePart getCurrentLinePart() {
    return currentLinePart;
  }

  public void setCurrentLinePart(LinePart currentLinePart) {
    this.currentLinePart = currentLinePart;
  }

  public Integer getPositionInLinePart() {
    return positionInLinePart;
  }

  public void setPositionInLinePart(Integer positionInLinePart) {
    this.positionInLinePart = positionInLinePart;
  }

  public String toString () {
    return "song " + currentSong + "\n" +
           "part " + currentSongPart + "\n" +
           "line " + currentLine + "\n" +
           "linepart" + currentLinePart + "\n"+
           "pos in linepart " + positionInLinePart;

  }
}
