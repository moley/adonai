package org.adonai.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import org.adonai.Key;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class SongRepairer {

  private static final Logger LOGGER = Logger.getLogger(SongRepairer.class.getName());

  private AdditionalsImporter additionalsImporter = new AdditionalsImporter();
  private SongTransposeService songTransposeService = new SongTransposeService();

  public void repairSong(final Song song) {

    Key currentKey = song.getCurrentKey() != null ? Key.fromString(song.getCurrentKey()): null;
    Key originKey = song.getOriginalKey() != null ? Key.fromString(song.getOriginalKey()): null;


    Collection<SongPart> emptySongParts = new ArrayList<SongPart>();
    for (SongPart nextPart : song.getSongParts()) {

      //in referenced parts we don't have content
      if (nextPart.getReferencedSongPart() != null)
        nextPart.getLines().clear();

      //remove all non digits from quantity
      if (nextPart.getQuantity() != null) {
        String quantityOnlyDigits = nextPart.getQuantity().replaceAll("\\D+", "");
        nextPart.setQuantity(quantityOnlyDigits);
      }

      Collection<Line> emptyLines = new ArrayList<Line>();

      for (Line line : nextPart.getLines()) {


        Collection<LinePart> emptyLineParts = new ArrayList<LinePart>();

        for (LinePart nextLinePart : line.getLineParts()) {

          if (nextLinePart.getChord() != null && nextLinePart.getChord().trim().isEmpty())
            nextLinePart.setChord(null);

          if (nextLinePart.getOriginalChord() != null && nextLinePart.getOriginalChord().trim().isEmpty())
            nextLinePart.setOriginalChord(null);

          if (nextLinePart.getChord() == null && nextLinePart.getText() == null)
            emptyLineParts.add(nextLinePart);




          try {
            songTransposeService.transpose(nextLinePart, originKey, currentKey);
          } catch (Exception e) {
            throw new IllegalStateException("Error repairing chord in linepart " + nextLinePart + " in song " + song.getId());

          }
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

      additionalsImporter.refreshCache(song, additional);

    }

    song.getSongParts().removeAll(emptySongParts);

  }
}
