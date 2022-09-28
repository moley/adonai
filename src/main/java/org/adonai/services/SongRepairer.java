package org.adonai.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.adonai.Key;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;

public class SongRepairer {

  private SongTransposeService songTransposeService = new SongTransposeService();

  public HashMap<SongPartType, Integer> getAggregatedNumberOfTypes (final Song song) {
    HashMap<SongPartType, Integer>  aggregatedNumberOfTypes = new HashMap<SongPartType, Integer>();

    //migrate on the fly to new structure elements and collect data for the structure repair loop afterwards
    for (SongPart nextPart : song.getSongParts()) {

      SongPartType songPartType = nextPart.getSongPartType();
      Integer count = aggregatedNumberOfTypes.get(songPartType);
      if (count == null)
        count = 0;
      count = count + 1;
      aggregatedNumberOfTypes.put(songPartType, count);
    }
    return aggregatedNumberOfTypes;
  }

  public void repairSong(final Song song) {

    Key currentKey = (song.getCurrentKey() != null && ! song.getCurrentKey().trim().isEmpty()) ? Key.fromString(song.getCurrentKey()) : null;
    Key originKey = (song.getOriginalKey() != null && ! song.getOriginalKey().trim().isEmpty()) ? Key.fromString(song.getOriginalKey()) : null;

    Collection<SongPart> emptySongParts = new ArrayList<SongPart>();

    //remove duplicate parts
    HashMap<String, SongPart> equalSongPartHashMap = new HashMap<String, SongPart>();
    for (SongPart nextPart: song.getSongParts()) {
      String equalKey = nextPart.getEqualKey();
      SongPart originSongPart = equalSongPartHashMap.get(equalKey);
      if (originSongPart != null) {
        for (SongStructItem structItem: song.getStructItems()) {
          if (structItem.getPartId().equals(nextPart.getId()))
            structItem.setPartId(originSongPart.getId());
        }
      }
      else
        equalSongPartHashMap.put(equalKey, nextPart);
    }

    //remove parts, which are not referenced in struct items any more
    Collection<SongPart> nonReferencedSongParts = new ArrayList<SongPart>();
    for (SongPart nextPart: song.getSongParts()) {
      if (song.getStructItems(nextPart).isEmpty())
        nonReferencedSongParts.add(nextPart);
    }
    song.getSongParts().removeAll(nonReferencedSongParts);

    //numbering text / shorttext
    HashMap<SongPartType, Integer>  currentNumberOfTypes = new HashMap<SongPartType, Integer>();
    HashMap<String, Integer> numberPerId = new HashMap<>();
    HashSet<String> workedIds = new HashSet<String>();

    HashMap<SongPartType, Integer>  aggregatedNumberOfTypes = getAggregatedNumberOfTypes(song);


    for (SongStructItem next : song.getStructItems()) {

      try {
        SongPart songPart = song.findSongPart(next);
        SongPartType songPartType = songPart.getSongPartType();

        Integer count = null;
        if (numberPerId.get(next.getPartId()) != null)
          count = numberPerId.get(next.getPartId());
        else {
          count = currentNumberOfTypes.get(songPartType);
          if (count == null)
            count = 0;
          count = count + 1;
          currentNumberOfTypes.put(songPartType, count);
          numberPerId.put(next.getPartId(), count);
        }

        String numbered = aggregatedNumberOfTypes.get(songPart.getSongPartType()) > 1 ? (" " + count.toString()) : "";

        if (songPart.getSongPartType() != null) {
          next.setText(songPart.getSongPartType().name() + numbered);
          next.setShorttext(songPart.getSongPartType().name().substring(0, 1) + numbered.trim());
        }
        else {
          next.setText("");
          next.setShorttext("");
        }
        boolean isFirstOccurence = !workedIds.contains(next.getPartId());
        workedIds.add(next.getPartId());
        next.setFirstOccurence(isFirstOccurence);
      } catch (Exception e) {
        throw new RuntimeException("Error repairing structitem " + next.getText() + " with part id " + next.getPartId(), e);
      }
    }

    //repair struct items
    for (SongStructItem nextStructItem: song.getStructItems()) {
      //remove all non digits from quantity
      if (nextStructItem.getQuantity() != null) {
        String quantityOnlyDigits = nextStructItem.getQuantity().replaceAll("\\D+", "");
        nextStructItem.setQuantity(quantityOnlyDigits);
      }
    }

    //repair parts
    for (int i = 0; i < song.getSongParts().size(); i++) {
      SongPart nextPart = song.getSongParts().get(i);

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

          //complete key / origin key
          try {
            songTransposeService.transpose(nextLinePart, originKey, currentKey);
          } catch (Exception e) {
            throw new IllegalStateException(
                "Error repairing chord in linepart " + nextLinePart + " in song " + song.getId() + ":" + e.getLocalizedMessage(), e);

          }
        }

        //remove empty line parts
        line.getLineParts().removeAll(emptyLineParts);

        if (line.getLineParts().isEmpty())
          emptyLines.add(line);

      }

      //remove empty lines
      nextPart.getLines().removeAll(emptyLines);

    }

    //remove empty parts
    song.getSongParts().removeAll(emptySongParts);

  }
}
