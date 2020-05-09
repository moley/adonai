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
        if (nextPart.getText() != null)
          textLine += nextPart.getText();

        if (nextPart.getChord() != null)
          chordLine += nextPart.getChord();

        int additionalSpaceChor = (nextPart.getText() != null && nextPart.getChord() != null ? nextPart.getText().length() - nextPart.getChord().length(): nextPart.getText().length());
        if (additionalSpaceChor > 0) {
          chordLine += StringUtils.spaces(additionalSpaceChor);
        }
        else if (additionalSpaceChor < 0) {
          chordLine += StringUtils.spaces( - additionalSpaceChor);
        }

        if (nextPart.getText().trim().isEmpty() && nextPart.getChord() != null) {
          chordLine += " ";
          textLine += " ";
        }


      }
      lines.add(chordLine);
      lines.add(textLine);
    }

    return String.join("\n", lines);
  }
}
