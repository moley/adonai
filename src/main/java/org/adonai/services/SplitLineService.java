package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.SongPart;

import java.util.ArrayList;
import java.util.Collection;

public class SplitLineService {

  /**
   * split a line by pressing cr
   * - split current linepart editor in previous and after (next line)
   * - all following line part editors into next line
   * - focus first token of next line
   * @param songCursor
   * @return
   */
  public LinePart splitLine (final SongCursor songCursor) {
    SongPart currentSongPart = songCursor.getCurrentSongPart();
    if (currentSongPart == null)
      throw new IllegalStateException("Current song part must not be null");

    Line currentLine = songCursor.getCurrentLine();
    LinePart currentLinePart = songCursor.getCurrentLinePart();
    Integer posInCurrentLinePart = songCursor.getPositionInLinePart();
    //end of last line part
    if (posInCurrentLinePart == currentLinePart.getText().length() - 1 && currentLinePart.equals(currentLine.getLastLinePart())) {
      Line newLine = currentSongPart.newLine(currentSongPart.getIndex(currentLine) + 1);
      return newLine.getFirstLinePart();
    }
    else if (posInCurrentLinePart == 0 && currentLinePart.equals(currentLine.getFirstLinePart())) { //beginning of first line part
      currentSongPart.newLine(currentSongPart.getIndex(currentLine));
      return currentLinePart;
    }
    else if (posInCurrentLinePart == 0) { //beginning of any other line part
      Line newLine = currentSongPart.newLine(currentSongPart.getIndex(currentLine) + 1, false);

      Collection<LinePart> movedLineParts = new ArrayList<LinePart>();

      for (int i = currentLine.getIndex(currentLinePart); i < currentLine.getLineParts().size(); i++) {
        movedLineParts.add(currentLine.getLineParts().get(i));
      }

      currentLine.getLineParts().removeAll(movedLineParts);
      newLine.getLineParts().addAll(movedLineParts);
      return newLine.getFirstLinePart();

    }  else { //middle of line part, splitting line parts in two parts

      String previousText = songCursor.getCurrentLinePart().getText().substring(0, posInCurrentLinePart);
      String nextText = songCursor.getCurrentLinePart().getText().substring(posInCurrentLinePart);
      currentLinePart.setText(previousText);

      Line newLine = currentSongPart.newLine(currentSongPart.getIndex(currentLine) + 1);
      if (nextText.isEmpty())
        nextText = " ";
      newLine.getFirstLinePart().setText(nextText);


      Collection<LinePart> movedLineParts = new ArrayList<LinePart>();

      for (int i = currentLine.getIndex(currentLinePart) + 1; i < currentLine.getLineParts().size(); i++) {
        movedLineParts.add(currentLine.getLineParts().get(i));
      }

      currentLine.getLineParts().removeAll(movedLineParts);
      newLine.getLineParts().addAll(movedLineParts);
      return newLine.getFirstLinePart();
    }
  }
}
