package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class SongCloneService {

  public LinePart cloneLinePart (final LinePart linePart) {
    LinePart clonedLinePart = new LinePart();
    clonedLinePart.setChord(linePart.getChord());
    clonedLinePart.setOriginalChord(linePart.getOriginalChord());
    clonedLinePart.setText(linePart.getText());
    return clonedLinePart;
  }

  public Line cloneLine (final Line line) {
    Line clonedLine = new Line();
    for (LinePart nextLinePart: line.getLineParts()) {
      clonedLine.getLineParts().add(cloneLinePart(nextLinePart));
    }
    return clonedLine;
  }

  public SongPart clonePart (final SongPart origin) {
    SongPart clonedSongPart = new SongPart();
    clonedSongPart.setSongPartType(origin.getSongPartType());
    clonedSongPart.setId(origin.getId());
    for (Line nextLine: origin.getLines()) {
      clonedSongPart.getLines().add(cloneLine(nextLine));
    }
    return clonedSongPart;
  }

  public Song cloneSong (final Song origin) {
    Song clonedSong = new Song();
    clonedSong.setCurrentKey(origin.getCurrentKey());
    clonedSong.setOriginalKey(origin.getOriginalKey());
    clonedSong.setId(origin.getId());

    for (SongPart nextSongpart:origin.getSongParts()) {
      clonedSong.getSongParts().add(clonePart(nextSongpart));
    }

    return clonedSong;
  }
}
