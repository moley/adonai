package org.adonai.reader.chordpro;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.adonai.Chord;
import org.adonai.ChordPart;
import org.adonai.InvalidChordException;
import org.adonai.NoteEntryType;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.reader.ReaderUtil;
import org.adonai.reader.text.TextfileReader;
import org.adonai.reader.text.TextfileReaderParam;
import org.adonai.services.SongRepairer;

@Slf4j public class ChordProFileReader {

  private ReaderUtil readerUtil = new ReaderUtil();

  private String strip(final String identifier, final String line) {
    if (line.startsWith(identifier)) {
      String stripped = line.substring(identifier.length()).replace("}", "");
      return stripped;
    }

    return null;

  }

  public Song read(final List<String> lines) {

    Song newSong = new Song();

    //Textfile extracted
    if (! String.join(",", lines).contains("{")) {
      String text = String.join("#", lines);
      String subtext = text.substring(0, text.indexOf("CCLI-Liednummer"));
      List<String> asText = Arrays.asList(subtext.split("#"));

      TextfileReaderParam textfileReaderParam = new TextfileReaderParam();
      textfileReaderParam.setEmptyLineIsNewPart(true);
      textfileReaderParam.setWithTitle(true);
      TextfileReader textfileReader = new TextfileReader();
      newSong = textfileReader.read(asText,textfileReaderParam);
    }
    else { //Ordinary chordpro

      SongPart newSongPart = null;

      for (String next : lines) {

        if (next.startsWith("CCLI-Liednummer"))
          break;

        String stripped = strip("{title: ", next);
        if (stripped != null) {
          newSong.setTitle(stripped.toUpperCase());
          continue;
        }

        stripped = strip("{key: ", next);
        if (stripped != null) {
          ChordPart chordPart = new ChordPart(stripped);
          if (chordPart.isMoll()) {
            chordPart.transpose(3, NoteEntryType.INCREMENT);
            stripped = chordPart.getNote().toString();
          }
          newSong.setOriginalKey(stripped);
          newSong.setCurrentKey(stripped);
          continue;
        }

        stripped = strip("{comment: ", next);
        if (stripped != null) {
          stripped = stripped.replaceAll("\\d", "").trim().toUpperCase();
          newSongPart = new SongPart();
          newSongPart.setId(UUID.randomUUID().toString());
          newSong.getSongParts().add(newSongPart);
          if (!newSong.getSongParts().contains(newSongPart)) //TODO extract to lib
            newSong.getSongParts().add(newSongPart);
          SongStructItem songStructItem = new SongStructItem();
          songStructItem.setPartId(newSongPart.getId());
          newSong.getStructItems().add(songStructItem);

          newSongPart.setSongPartType(readerUtil.extractTypeOrDefault(stripped));

          continue;

        } else if (next.startsWith("{")) {
          log.warn("Data " + next + " is not imported yet");
          continue;
        }

        if (!next.trim().isEmpty()) {
          log.debug("Line " + next + " is imported");
          String[] tokens = next.trim().split("\\[");
          Line newLine = new Line();
          newSongPart.getLines().add(newLine);
          for (String nextToken : tokens) {
            String[] innerTokens = nextToken.split("]");

            LinePart newLinePart = new LinePart();

            if (innerTokens.length > 1) {
              String nextChord = innerTokens[0];
              try {
                Chord chord = new Chord(nextChord);
                newLinePart.setChord(chord.toString());
              } catch (InvalidChordException e) {
                log.warn("Chord " + nextChord + " could not be extracted");
              }
              newLinePart.setText(innerTokens[1]);
            } else {
              if (nextToken.contains("]")) {
                String nextChord = nextToken.replace("]", "");
                try {
                  Chord chord = new Chord(nextChord);
                  newLinePart.setChord(chord.toString());
                } catch (InvalidChordException e) {
                  log.warn("Chord " + nextChord + " could not be extracted");
                }
              } else
                newLinePart.setText(nextToken);

            }
            log.debug("Extracted linepart: " + newLinePart);
            newLine.getLineParts().add(newLinePart);

          }

        }

      }
    }

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(newSong);

    return newSong;

  }



}
