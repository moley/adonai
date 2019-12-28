package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;

public class MoveChordService {

  public void toLeft (final SongCursor songCursor) {
    Line line = songCursor.getCurrentLine();
    LinePart currentLinePart = songCursor.getCurrentLinePart();
    LinePart previousLinePart = line.getPreviousLinePart(currentLinePart);
    String lastingKey = previousLinePart.getText().substring(0, previousLinePart.getText().length() - 1);
    String lastKey = previousLinePart.getText().substring(previousLinePart.getText().length() - 1, previousLinePart.getText().length());
    previousLinePart.setText(lastingKey);
    currentLinePart.setText(lastKey + currentLinePart.getText());



  }

  public void toRight (final SongCursor songCursor) {
    Line line = songCursor.getCurrentLine();
    LinePart currentLinePart = songCursor.getCurrentLinePart();
    LinePart previousLinePart = line.getPreviousLinePart(currentLinePart);

    String movedText = currentLinePart.getText().substring(0, 1);
    String restTest = currentLinePart.getText().substring(1, currentLinePart.getText().length());
    previousLinePart.setText(previousLinePart.getText() + movedText);
    currentLinePart.setText(restTest);

  }
}
