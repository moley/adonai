package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.Chord;

public class AddChordService {

  public LinePart addChord (final SongCursor songCursor, Chord chord) {
    //TODO move to service
    LinePart linePart = songCursor.getCurrentLinePart();
    Line line = songCursor.getCurrentLine();

    int currentCaretPosition = songCursor.getPositionInLinePart();

    String leftText = linePart.getText().substring(0, currentCaretPosition);
    String rightText = linePart.getText().substring(currentCaretPosition, linePart.getText().length());

    linePart.setText(leftText);
    LinePart newLinePart = new LinePart(rightText, chord.toString());

    int indexLeftPart = line.getLineParts().indexOf(linePart);
    line.getLineParts().add(indexLeftPart + 1, newLinePart);

    return newLinePart;
  }
}
