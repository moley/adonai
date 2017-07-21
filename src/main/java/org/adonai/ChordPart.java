package org.adonai;

/**
 * Created by OleyMa on 11.08.16.
 */
public class ChordPart {

  Note note;
  boolean moll;
  boolean major;
  Integer intervall;
  boolean sus;

  NoteEntryType noteEntryType;

  public String toString () {
    String asString = "";
    asString += note.getLabel(noteEntryType);

    if (moll)
      asString += "m";
    if (major)
      asString += "maj";

    if (sus)
      asString +="sus";

    if (intervall != null)
      asString += intervall;

    return asString;



  }

  public ChordPart (final String chordpartAsString) throws InvalidChordException {
    String trimmed = chordpartAsString.replace(String.valueOf((char) 160), " ").trim();
    if (trimmed.isEmpty())
      throw new InvalidChordException("Empty string");

    for (NoteEntry noteEntry: Note.getLengthOrderedNotes()) {

        if (trimmed.startsWith(noteEntry.getLabel())) {
          note = noteEntry.getNote();
          moll = false;
          trimmed = trimmed.substring(noteEntry.getLabel().length());
          noteEntryType = noteEntry.getNoteEntryType();
          break;
        }
        else if (trimmed.startsWith(noteEntry.getLabel().toLowerCase())) { //moll
          note = noteEntry.getNote();
          moll = true;
          trimmed = trimmed.substring(noteEntry.getLabel().length());
          noteEntryType = noteEntry.getNoteEntryType();
          break;
        }
    }

    if (note == null)
      throw new InvalidChordException(chordpartAsString + " contains no valid note (" + Note.getLengthOrderedNotes() + ")");


    if (! trimmed.isEmpty() && trimmed.startsWith("maj")) {
      trimmed = trimmed.substring(3);
      major = true;
    }

    if (! trimmed.isEmpty() && trimmed.startsWith("sus")) {
      trimmed = trimmed.substring(3);
      sus = true;
    }

    if (! trimmed.isEmpty() && trimmed.startsWith("m")) {
      trimmed = trimmed.substring(1);
      moll = true;
    }

    if (! trimmed.isEmpty()) {
      try {
        intervall = Integer.valueOf(trimmed);
      } catch (NumberFormatException e) {
        throw new InvalidChordException("Number format exception in " + trimmed + "(from chord " + chordpartAsString + ")");
      }
    }


  }




  /**
   * transposes a chord
   * @param diff
   */
  public void transpose (final int diff, final NoteEntryType noteEntryType) {
    int currentOrdinal = note.ordinal();
    int nextOrdinal = currentOrdinal + diff;
    if (nextOrdinal < 0)
      nextOrdinal += Note.values().length;
    else if (nextOrdinal >=  Note.values().length)
      nextOrdinal -= Note.values().length;

    this.note = Note.values()[nextOrdinal];
    this.noteEntryType = noteEntryType;
  }
}
