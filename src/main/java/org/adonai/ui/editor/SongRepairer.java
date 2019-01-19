package org.adonai.ui.editor;

import org.adonai.additionals.AdditionalsImporter;
import org.adonai.model.*;
import org.adonai.ui.mainpage.MainPageController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class SongRepairer {

  private static final Logger LOGGER = Logger.getLogger(SongRepairer.class.getName());

  private AdditionalsImporter additionalsImporter = new AdditionalsImporter();

  public void repairSong(final Song song) {
    Collection<SongPart> emptySongParts = new ArrayList<SongPart>();
    for (SongPart nextPart : song.getSongParts()) {

      //in referenced parts we don't have content
      if (nextPart.getReferencedSongPart() != null)
        nextPart.getLines().clear();

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

      if (nextPart.getLines().isEmpty() && nextPart.getReferencedSongPart() == null)
        emptySongParts.add(nextPart);


    }

    if (song.findAdditional(AdditionalType.AUDIO) != null) {
      Additional additional = song.findAdditional(AdditionalType.AUDIO);

      try {
        File additionalFile = additionalsImporter.getAdditional(additional);
        LOGGER.info("Mp3 " + additional.getLink() + "-" + additionalFile.getAbsolutePath());
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

    }

    song.getSongParts().removeAll(emptySongParts);

  }
}
