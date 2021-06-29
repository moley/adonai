package org.adonai.fx.viewer;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.adonai.StringUtils;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.SongPart;

@Slf4j
public class TextRenderer {

  public String getRenderedText (final SongPart songPart, final boolean original) {
    try {
      List<String> lines = new ArrayList<>();
      for (Line line : songPart.getLines()) {
        String chordLine = "";
        String textLine = "";
        for (LinePart nextPart : line.getLineParts()) {

          //if not the first one and chord line is not shorter than text line then we have to add
          //a space because else the chords are merged together when creating model from it again
          /**if (! chordLine.isEmpty() && ! textLine.isEmpty()) {
           if (chordLine.length() == textLine.length()) {
           chordLine += " ";
           textLine += "";
           }
           }**/

          String selectedChord = original ? nextPart.getOriginalChord() : nextPart.getChord();
          if (nextPart.getText() != null)
            textLine += nextPart.getText();

          if (selectedChord != null)
            chordLine += selectedChord;

          String chord = selectedChord != null ? selectedChord : "";
          String text = nextPart.getText();

          int longest = Integer.max(chord.length(), text.length());
          if (longest == chord.length() && longest > 0)
            longest = longest + 1;

          chordLine += StringUtils.spaces(longest - chord.length());
          textLine += StringUtils.spaces(longest - text.length());
        }
        lines.add(chordLine);
        lines.add(textLine);
      }

      return String.join("\n", lines);
    } catch (Exception e) {
      log.error("Error rendering song part " + (songPart != null ? songPart.getId() : "null") + ":" + e.getLocalizedMessage(), e);
      throw new RuntimeException(e);
    }
  }
}
