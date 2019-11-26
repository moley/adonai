package org.adonai.services;

import org.adonai.Chord;
import org.adonai.model.Line;
import org.adonai.model.LinePart;

public class AddChordService {

  public LinePart addChord (final SongCursor songCursor, Chord chord) {
    return addChord(songCursor, chord, chord);
  }

  public LinePart addChord (final SongCursor songCursor, Chord chord, Chord originalChord) {
    LinePart linePart = songCursor.getCurrentLinePart();
    Line line = songCursor.getCurrentLine();

    int currentCaretPosition = songCursor.getPositionInLinePart();

    String leftText = linePart.getText().substring(0, currentCaretPosition);
    String rightText = linePart.getText().substring(currentCaretPosition, linePart.getText().length());

    String chordString = chord != null ? chord.toString(): null;
    String originChordString = originalChord != null ? originalChord.toString(): null;

    linePart.setText(leftText);
    LinePart newLinePart = new LinePart(rightText, chordString, originChordString);

    int indexLeftPart = line.getLineParts().indexOf(linePart);
    line.getLineParts().add(indexLeftPart + 1, newLinePart);

    return newLinePart;
  }
}
