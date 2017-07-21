package org.adonai.ui.editor;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

import java.util.ArrayList;
import java.util.Collection;

public class SongRepairer {

  public void repairSong(final Song song) {
    Collection<SongPart> emptySongParts = new ArrayList<SongPart>();
    for (SongPart nextPart : song.getSongParts()) {


      Collection<Line> emptyLines = new ArrayList<Line>();

      for (Line line : nextPart.getLines()) {


        Collection<LinePart> emptyLineParts = new ArrayList<LinePart>();

        for (LinePart nextLinePart : line.getLineParts()) {
          if (nextLinePart.getChord() == null && nextLinePart.getText() == null)
            emptyLineParts.add(nextLinePart);
          //TODO further checks

        }
        line.getLineParts().removeAll(emptyLineParts);

        if (line.getLineParts().isEmpty())
          emptyLines.add(line);

      }

      nextPart.getLines().removeAll(emptyLines);

      if (nextPart.getLines().isEmpty())
        emptySongParts.add(nextPart);


    }

    song.getSongParts().removeAll(emptySongParts);

  }
}
