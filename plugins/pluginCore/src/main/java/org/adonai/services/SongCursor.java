package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongStructItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongCursor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongCursor.class.getName());


  private Song currentSong;


  private SongStructItem currentSongStructItem;

  private SongPart currentSongPart;


  private Line currentLine;

  private LinePart currentLinePart;

  private Integer positionInLinePart;

  public SongCursor () {

  }

  public SongCursor (final Song song, final int part, final int line, final int linepart, int positionInLinePart) {
    LOGGER.info("Create song cursor for song " + song.getTitle () + "(" + part + "-" + line + "-" + linepart + "-" + positionInLinePart + ")");
    currentSong = song;
    currentSongStructItem = ! song.getStructItems().isEmpty() ? song.getStructItems().get(part):null;
    currentSongPart = currentSong.findSongPart(currentSongStructItem);
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
           "structitem " + currentSongStructItem + "\n" +
           "songpart " + currentSongPart + "\n" +
           "line " + currentLine + "\n" +
           "linepart" + currentLinePart + "\n"+
           "pos in linepart " + positionInLinePart;

  }


  public void setCurrentSongStructItem(SongStructItem currentSongStructItem) {
    this.currentSongStructItem = currentSongStructItem;
  }

  public SongStructItem getCurrentSongStructItem() {
    return currentSongStructItem;
  }

  public SongPart getCurrentSongPart() {
    return currentSongPart;
  }
}
