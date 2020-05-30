package org.adonai.fx.editor;

import java.util.ArrayList;
import java.util.List;
import org.adonai.StringUtils;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.SongPart;

public class TextRenderer {

  public String getRenderedText (final SongPart songPart) {
    List<String> lines = new ArrayList<>();
    for (Line line: songPart.getLines()) {
      String chordLine = "";
      String textLine = "";
      for (LinePart nextPart: line.getLineParts()) {

        //if not the first one and chord line is not shorter than text line then we have to add
        //a space because else the chords are merged together when creating model from it again
        /**if (! chordLine.isEmpty() && ! textLine.isEmpty()) {
          if (chordLine.length() == textLine.length()) {
            chordLine += " ";
            textLine += "";
          }
        }**/
        if (nextPart.getText() != null)
          textLine += nextPart.getText();

        if (nextPart.getChord() != null)
          chordLine += nextPart.getChord();

        String chord = nextPart.getChord() != null ? nextPart.getChord(): "";
        String text = nextPart.getText();

        int longest = Integer.max(chord.length(), text.length());
        if (longest == chord.length() && longest > 0)
          longest = longest +1;

        chordLine += StringUtils.spaces(longest - chord.length());
        textLine += StringUtils.spaces(longest - text.length());
      }
      lines.add(chordLine);
      lines.add(textLine);
    }

    return String.join("\n", lines);
  }
}
