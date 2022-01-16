package org.adonai.services;

import lombok.extern.slf4j.Slf4j;
import org.adonai.Chord;
import org.adonai.Key;
import org.adonai.KeyManager;
import org.adonai.Note;
import org.adonai.NoteEntryType;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

@Slf4j
public class SongTransposeService {

  private KeyManager manager = new KeyManager();

  public void transpose (final LinePart linePart, final Key from, final Key to) {
    if (from == null || to == null)
      return;

    Note noteFrom = Note.from(from);
    Note noteTo = Note.from(to);

    int fromIndex = noteFrom.ordinal();
    int toIndex = noteTo.ordinal();

    int diff = toIndex - fromIndex;

    NoteEntryType noteEntryTypeTo = manager.getType(to);
    NoteEntryType noteEntryTypeFrom = manager.getType(from);

    if (linePart.getChord() != null && linePart.getOriginalChord() == null) {
      Chord nextChord = new Chord(linePart.getChord());
      nextChord.transpose(- diff, noteEntryTypeFrom);
      linePart.setOriginalChord(nextChord.toString());
    }
    if (linePart.getChord() == null && linePart.getOriginalChord() != null) {
      Chord nextChord = new Chord(linePart.getOriginalChord());
      nextChord.transpose(diff, noteEntryTypeTo);
      linePart.setChord(nextChord.toString());
    }

  }

  public void recalculateOrigin (final Song song) {
    log.info("recalculate origin chords (from " + song.getOriginalKey() + " to " + song.getCurrentKey() + ")");
    if (song.getCurrentKey() == null || song.getOriginalKey() == null)
      throw new IllegalStateException("Both original and current key have to be set to recalculate the origin key");

    for (SongPart nextPart: song.getSongParts()) {
      for (Line line: nextPart.getLines()) {
        for (LinePart nextLinePart: line.getLineParts()) {
          nextLinePart.setOriginalChord(null);
          transpose(nextLinePart, Key.fromString(song.getOriginalKey()), Key.fromString(song.getCurrentKey()));
        }
      }
    }

    log.info("Recalculating origin chords finished");
  }

  public void recalculateCurrent (final Song song) {
    log.info("recalculate current chords (from " + song.getOriginalKey() + " to " + song.getCurrentKey() + ")");
    if (song.getCurrentKey() == null || song.getOriginalKey() == null)
      throw new IllegalStateException("Both original and current key have to be set to recalculate the current key");
    for (SongPart nextPart: song.getSongParts()) {
      for (Line line: nextPart.getLines()) {
        for (LinePart nextLinePart: line.getLineParts()) {
          nextLinePart.setChord(null);
          transpose(nextLinePart, Key.fromString(song.getOriginalKey()), Key.fromString(song.getCurrentKey()));
        }
      }
    }

    log.info("Recalculated current chords finished");


  }

  public void transpose (final Song song, final Key from, final Key to) {

    Note noteFrom = Note.from(from);
    Note noteTo = Note.from(to);

    int fromIndex = noteFrom.ordinal();
    int toIndex = noteTo.ordinal();

    int diff = toIndex - fromIndex;

    NoteEntryType noteEntryType = manager.getType(to);

    log.info("Transposing song " + song.getId() + " from " + fromIndex + "(" + from + ") to " + toIndex + "(" + to + ")");

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
