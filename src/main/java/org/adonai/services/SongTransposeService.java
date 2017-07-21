package org.adonai.services;

import org.adonai.*;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class SongTransposeService {

  private KeyManager manager = new KeyManager();

  public void transpose (final Song song, final Key from, final Key to) {

    Note noteFrom = Note.from(from);
    Note noteTo = Note.from(to);

    int fromIndex = noteFrom.ordinal();
    int toIndex = noteTo.ordinal();

    int diff = toIndex - fromIndex;

    NoteEntryType noteEntryType = manager.getType(to);


    System.out.println ("From " + fromIndex + " to " + toIndex);
    for (SongPart nextPart: song.getSongParts()) {
      for (Line line: nextPart.getLines()) {
        for (LinePart nextLinePart: line.getLineParts()) {
          if (nextLinePart.getChord() != null && ! nextLinePart.getChord().trim().isEmpty()) {
            Chord nextChord = new Chord(nextLinePart.getChord());
            nextChord.transpose(diff, noteEntryType);
            nextLinePart.setChord(nextChord.toString());


          }

        }
      }
    }


  }
}
