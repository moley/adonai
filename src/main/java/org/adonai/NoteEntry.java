package org.adonai;

public class NoteEntry implements Comparable<NoteEntry> {

  private String label;
  private Note note;
  private NoteEntryType noteEntryType;

  public NoteEntry (final String label, final Note note, final NoteEntryType noteEntryType) {
    this.label = label;
    this.note = note;
    this.noteEntryType = noteEntryType;
  }

  public String getLabel() {
    return label;
  }

  public Note getNote() {
    return note;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NoteEntry noteEntry = (NoteEntry) o;

    if (label != null ? !label.equals(noteEntry.label) : noteEntry.label != null) return false;
    return note == noteEntry.note;
  }

  @Override
  public int hashCode() {
    int result = label != null ? label.hashCode() : 0;
    result = 31 * result + (note != null ? note.hashCode() : 0);
    return result;
  }

  @Override
  public int compareTo(NoteEntry o) {
    return - new Integer (getLabel().length()).compareTo(o.getLabel().length());
  }

  public NoteEntryType getNoteEntryType() {
    return noteEntryType;
  }

  public String toString () {
    return note.toString();

  }
}
