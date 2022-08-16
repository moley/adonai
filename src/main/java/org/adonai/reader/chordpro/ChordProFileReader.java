package org.adonai.reader.chordpro;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.services.SongRepairer;

@Slf4j
public class ChordProFileReader {

  private String strip (final String identifier, final String line) {
    if (line.startsWith(identifier)) {
      String stripped = line.substring(identifier.length()).replace("}", "");
      return stripped;
    }

    return null;

  }
  public Song read (final List<String> lines) {

    Song newSong = new Song();
    SongPart newSongPart = null;

    for (String next: lines) {

      if (next.startsWith("CCLI-Liednummer"))
        break;

      String stripped = strip("{title: ", next);
      if (stripped != null) {
        newSong.setTitle(stripped.toUpperCase());
        continue;
      }

      stripped = strip("{key: ", next);
      if (stripped != null) {
        newSong.setOriginalKey(stripped);
        newSong.setCurrentKey(stripped);
        continue;
      }

      stripped = strip("{comment: ", next);
      if (stripped != null) {
        stripped = stripped.replaceAll("\\d","").trim().toUpperCase();
        newSongPart  = new SongPart();
        newSongPart.setId(UUID.randomUUID().toString());
        newSong.getSongParts().add(newSongPart);
        if (! newSong.getSongParts().contains(newSongPart)) //TODO extract to lib
          newSong.getSongParts().add(newSongPart);
        SongStructItem songStructItem = new SongStructItem();
        songStructItem.setPartId(newSongPart.getId());
        newSong.getStructItems().add(songStructItem);

        switch (stripped) {
          case "VERS": newSongPart.setSongPartType(SongPartType.VERS); break;
          case "CHORUS": newSongPart.setSongPartType(SongPartType.REFRAIN); break;
          case "BRIDGE": newSongPart.setSongPartType(SongPartType.BRIDGE); break;
          case "SCHLUSS": newSongPart.setSongPartType(SongPartType.EXTRO); break;
          default: newSongPart.setSongPartType(SongPartType.ZWISCHENSPIEL);
        }

        continue;

      }
      else if (next.startsWith("{")) {
        log.warn("Data " + next + " is not imported yet");
        continue;
      }



      if (! next.trim().isEmpty()) {
        log.debug("Line " + next + " is imported");
        String [] tokens = next.trim().split("\\[");
        Line newLine = new Line();
        newSongPart.getLines().add(newLine);
        for (String nextToken: tokens) {
          String [] innerTokens = nextToken.split("]");

          LinePart newLinePart = new LinePart();

          if (innerTokens.length > 1) {
            newLinePart.setChord(innerTokens[0]);
            newLinePart.setText(innerTokens[1]);
          }
          else {
            if (nextToken.contains("]"))
              newLinePart.setChord(nextToken.replace("]", ""));
            else
              newLinePart.setText(nextToken);

          }
          log.debug("Extracted linepart: " + newLinePart);
          newLine.getLineParts().add(newLinePart);

        }


      }


    }

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(newSong);


    return newSong;
  }


}
