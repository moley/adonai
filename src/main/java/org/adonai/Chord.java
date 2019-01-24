package org.adonai;

/**
 * Created by OleyMa on 11.08.16.
 */
public class Chord {

  ChordPart leftPart;
  ChordPart basePart;



  public Chord (final String chordAsString) throws InvalidChordException {
    String [] chordParts = chordAsString.split("/");
    if (chordParts.length == 0 || chordParts.length > 2)
      throw new InvalidChordException("Not one or two chord parts found, but " + chordParts.length + " in " + chordAsString);
    leftPart = new ChordPart(chordParts [0]);
    if (chordParts.length == 2)
      basePart = new ChordPart(chordParts[1]);
  }

  public String toString () {
    String baseNotNull = basePart != null ? ("/" + basePart.toString()) : "";
    return leftPart.toString() + baseNotNull;
  }

  /**
   * transposes a chord
   * @param diff
   */
  public void transpose (final int diff, final NoteEntryType noteEntryType) {
    if (leftPart != null)
    leftPart.transpose(diff, noteEntryType);

    if (basePart != null)
    basePart.transpose(diff, noteEntryType);
  }

  /**
   * transposes a chord
   * @param diff
   */
  public void transpose (final int diff) {
    NoteEntryType noteEntryType = diff >= 0 ? NoteEntryType.INCREMENT : NoteEntryType.DECREMENT;
    if (leftPart != null)
      leftPart.transpose(diff, noteEntryType);

    if (basePart != null)
      basePart.transpose(diff, noteEntryType);
  }



}
