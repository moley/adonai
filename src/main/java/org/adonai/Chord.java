package org.adonai;

/**
 * Created by OleyMa on 11.08.16.
 */
public class Chord {

  ChordPart leftPart;
  ChordPart basePart;



  public Chord (String chordAsString) throws InvalidChordException {

    chordAsString = chordAsString.replace("(", "");
    chordAsString = chordAsString.replace(")", "");

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
  public Chord transpose (final int diff, final NoteEntryType noteEntryType) {
    leftPart.transpose(diff, noteEntryType);

    if (basePart != null)
    basePart.transpose(diff, noteEntryType);

    return this;
  }

  /**
   * transposes a chord
   * @param diff
   */
  public void transpose (final int diff) {
    NoteEntryType noteEntryType = diff >= 0 ? NoteEntryType.INCREMENT : NoteEntryType.DECREMENT;
    transpose(diff, noteEntryType);
  }



}
