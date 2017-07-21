package org.adonai.reader;

import org.junit.Assert;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class SongConsistencyChecker {

  public void check (final Song song) {

    for (SongPart nextPart: song.getSongParts()) {

      Assert.assertTrue (nextPart.getLines().size() > 0);

      for (Line line: nextPart.getLines()) {
        Assert.assertTrue ("Empty line in part " + nextPart.toString(), line.getLineParts().size() > 0);

        for (LinePart nextLinePart: line.getLineParts()) {

          Assert.assertFalse ("LinePart found with no chord AND no text in " + nextPart, nextLinePart.getChord() == null && nextLinePart.getText() == null);

        }
      }
    }


  }
}
