package org.adonai.services;

import org.adonai.model.LinePart;

public class RemoveChordService {

  public LinePart removeChord (final SongCursor songCursor) {
    LinePart linePart = songCursor.getCurrentLinePart();
    Integer partIndex = songCursor.getPositionInLinePart();
    if (partIndex == 0) {
      if (linePart.equals(songCursor.getCurrentLine().getFirstLinePart())) {
        linePart.setChord(null);
      }
      else {
        LinePart previousLinePart = songCursor.getCurrentLine().getPreviousLinePart(linePart);
        int lengthBeforeMerge = previousLinePart.getText().length();
        previousLinePart.setText(previousLinePart.getText() + linePart.getText());
        songCursor.getCurrentLine().getLineParts().remove(linePart);
        songCursor.setPositionInLinePart(lengthBeforeMerge);

        return previousLinePart;

      }
    }

    songCursor.setPositionInLinePart(null);
    return linePart;
  }
}
