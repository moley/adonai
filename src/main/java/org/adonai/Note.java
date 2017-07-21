package org.adonai;

import java.util.*;

/**
 * Created by OleyMa on 11.08.16.
 */
public enum Note {


  C("C", "C"),
  CIS("C#", "Db"),
  D("D", "D"),
  DIS ("D#", "Eb"),
  E ("E", "E"),
  F ("F", "F"),
  FIS ("F#", "Gb"),
  G ("G", "G"),
  GIS ("G#", "Ab"),
  A ("A", "A"),
  B ("A#", "Bb"),
  H ("H", "H");

  private String labelDecremented = "";
  private String labelIncremented = "";

  Note (final String labelIncremented, final String labelDecremented) {
    this.labelDecremented = labelDecremented;
    this.labelIncremented = labelIncremented;
  }


  public String getLabelDecremented() {
    return labelDecremented;
  }

  public String getLabelIncremented() {
    return labelIncremented;
  }

  public String getLabel (final NoteEntryType noteEntryType) {
    return noteEntryType == NoteEntryType.INCREMENT ? labelIncremented: labelDecremented;
  }

  public Collection<String> getLabels () {
    return Arrays.asList(labelDecremented, labelIncremented);
  }

  public static Collection<NoteEntry> getLengthOrderedNotes () {
    List<NoteEntry> orderedNotes = new ArrayList<NoteEntry>();

    for (Note nextNote: Note.values()) {
      NoteEntry decrementedEntry = new NoteEntry(nextNote.getLabelDecremented(), nextNote, NoteEntryType.DECREMENT);
      NoteEntry incrementedEntry = new NoteEntry(nextNote.getLabelIncremented(), nextNote, NoteEntryType.INCREMENT);
      if (! orderedNotes.contains(decrementedEntry))
        orderedNotes.add(decrementedEntry);

      if (! orderedNotes.contains(incrementedEntry))
        orderedNotes.add(incrementedEntry);
    }

    Collections.sort(orderedNotes);

    return orderedNotes;

  }

  public static Note from (final Key key) {
    for (Note next: Note.values()) {
      if (next.getLabelDecremented().equals(key.name()) ||
      next.getLabelIncremented().equals(key.name()) ||
        next.name().equals(key.name())) {
        return next;
      }
    }
    throw new IllegalStateException("No note found for key " + key.name());
  }

  public String toString () {
    return name();
  }
}
