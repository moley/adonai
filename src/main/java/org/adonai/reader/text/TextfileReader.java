package org.adonai.reader.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.adonai.Chord;
import org.adonai.InvalidChordException;
import org.adonai.StringUtils;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.reader.ReaderUtil;
import org.adonai.services.SongRepairer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextfileReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(TextfileReader.class.getName());

  private ReaderUtil readerUtil = new ReaderUtil();

  private String addPendingChordLine (final String currentChordLine, final SongPart songPart) {
    if (currentChordLine != null) { //chordline as last line in part
      List<Chord> chords = getChords(currentChordLine);
      Line chordline = new Line();
      for (Chord nextChord: chords) {
        chordline.getLineParts().add(new LinePart(nextChord));
      }
      if (songPart != null) {
        LOGGER.info("- add pending chordline " + chordline + " to songpart " + songPart.getId());
        songPart.getLines().add(chordline);
      }

    }

    return null;

  }

  public Song read(List<String> lines, TextfileReaderParam textfileReaderParam) {

    Song song = new Song();
    HashMap<String, SongPart> createdParts = new HashMap<>();

    SongPart currentSongPart = null;
    SongStructItem currentStructItem = null;

    String currentChordLine = null;
    for (int linenumber = 0; linenumber < lines.size(); linenumber++) {

      String next = lines.get(linenumber);

      if (next.trim().isEmpty()) {
        if (textfileReaderParam.isEmptyLineIsNewPart()) {
          currentSongPart = null;
          currentStructItem = null;
        }
        continue;
      }

      LOGGER.info("Read line " + linenumber + ":" + next);


      SongPart newSongPart = createSongPartOnDemand(createdParts, next); //Rest
      if (newSongPart != null) {
        currentChordLine = addPendingChordLine(currentChordLine, currentSongPart);
        currentSongPart = newSongPart;
        if (! song.getSongParts().contains(currentSongPart)) //TODO extract to lib
          song.getSongParts().add(currentSongPart);
        SongStructItem songStructItem = new SongStructItem();
        songStructItem.setPartId(currentSongPart.getId());
        currentStructItem = songStructItem;
        song.getStructItems().add(songStructItem);
        LOGGER.info("- added songstructitem for part " + songStructItem.getPartId());
        continue;
      } else if (currentSongPart == null) {
        if (textfileReaderParam.isWithTitle() && song.getTitle() == null) { //No title set, seems to be title
          song.setTitle(next.toUpperCase());
          LOGGER.info ("- set title to " + song.getTitle());
          continue;
        }
        else { //part beginning without type
          if (textfileReaderParam.isEmptyLineIsNewPart()) {
            newSongPart = new SongPart();
            currentSongPart = newSongPart;
            currentSongPart.setId(UUID.randomUUID().toString());
            song.getSongParts().add(currentSongPart);
            SongStructItem songStructItem = new SongStructItem();
            songStructItem.setPartId(currentSongPart.getId());
            currentStructItem = songStructItem;
            song.getStructItems().add(songStructItem);
            LOGGER.info("- added songstructitem for part " + songStructItem.getPartId());

          }
        }
      }

      //quantities removed and set at part
      String bracketContent = StringUtils.getBracketContent(next);
      LOGGER.info("- bracket content = " + bracketContent);
      if (! bracketContent.trim().isEmpty()) {
        currentStructItem.setQuantity(bracketContent.trim());
        next = next.replace("(" + bracketContent + ")", "");
        LOGGER.info ("- rest: " + next);
      }

      if (next.trim().isEmpty()) {
        LOGGER.info("- break");
        continue;
      }

      SongPartType foundType = readerUtil.extractType(next.trim());
      if (foundType != null && newSongPart != null)
        newSongPart.setSongPartType(foundType);
      else {

        if (isChordLine(next)) { //read chord line
          if (currentChordLine != null)
            addPendingChordLine(currentChordLine, currentSongPart);

          currentChordLine = next;
        } else { //read text line

          Line newLine = new Line();
          if (currentChordLine == null) {
            LOGGER.info("- create linepart without chords (" + next + ")");
            newLine.getLineParts().add(new LinePart(next));
          } else {

            List<Integer> tokens = getTokens(currentChordLine);
            List<Chord> chords = getChords(currentChordLine);
            if (tokens.get(0) > 0) { //Line in front ot first chord
              newLine.getLineParts().add(new LinePart(next.substring(0, tokens.get(0))));            }

            for (int i = 0; i < tokens.size(); i++) {
              Integer from = tokens.get(i);
              Integer to = next.length();
              if (i < tokens.size() - 1)
                to = tokens.get(i + 1);
              try {
                Chord chord = chords.get(i);
                Integer toOrEnmd = Integer.min(to.intValue(), next.length());
                String text = from.intValue() < toOrEnmd ? next.substring(from.intValue(), toOrEnmd) : " ";

                String evenutallyTrimmedText = newLine.getLineParts().isEmpty() ? StringUtils.trimLeft(text) : text;

                newLine.getLineParts().add(new LinePart(evenutallyTrimmedText, chord));
              } catch (Exception e) {
                throw new IllegalStateException("From " + from + " to " + to + "(" + i + " of " + (tokens.size() - 1) + ") in line " + linenumber + "(" + next + ")",
                    e);

              }

            }

            currentChordLine = null;
          }

          LOGGER.info("- create lineparts with chords (" + newLine + ")");
          currentSongPart.getLines().add(newLine);

        }
      }

    }

    currentChordLine = addPendingChordLine(currentChordLine, currentSongPart);

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);

    return song;
  }

  SongPart createSongPartOnDemand(final HashMap<String, SongPart> createdParts, final String line) {
    if (line.contains("[") && line.contains("]")) {
      String completeType = line.replace("[", "").replace("]", "").trim().toUpperCase();

      SongPart existingPart = createdParts.get(completeType);
      if (existingPart != null) {
        return existingPart;
      }
      else {

        String[] types = completeType.split(" ");
        String type = types[0];
        if (type.equals("STROPHE"))
          type = SongPartType.VERS.name();
        else if (type.startsWith("VERSE"))
          type = SongPartType.VERS.name();
        else if (type.startsWith("PRE-CHORUS"))
          type = SongPartType.BRIDGE.name();
        else if (type.startsWith("CHORUS"))
          type = SongPartType.REFRAIN.name();
        else if (type.startsWith("INTERLUDE"))
          type = SongPartType.ZWISCHENSPIEL.name();
        else if (type.equals("OUTRO"))
          type = SongPartType.EXTRO.name();

        SongPartType determinedSongPart = SongPartType.valueOf(type);
        if (determinedSongPart == null)
          throw new IllegalStateException("Part " + type + " not found, please add mapping");

        LOGGER.info("- created songpart with songparttype " + determinedSongPart + "(" + completeType + ")");

        SongPart songPart = new SongPart();
        songPart.setSongPartType(determinedSongPart);
        songPart.getId(); //to initialize the id
        createdParts.put(completeType, songPart);
        return songPart;
      }

    }

    return null;

  }

  boolean isChordLine(final String line) {
    String[] tokens = line.split(" ");
    for (String nextToken : tokens) {
      String trimmedToken = nextToken.trim();
      if (trimmedToken.trim().isEmpty())
        continue;

      try {
        new Chord(trimmedToken);
      } catch (InvalidChordException e) {
        return false;
      }
    }

    return true;

  }

  List<Chord> getChords(final String line) {
    List<Chord> chords = new ArrayList<Chord>();
    String[] tokens = line.split(" ");

    for (String nextToken : tokens) {
      String trimmedToken = nextToken.trim();
      if (trimmedToken.isEmpty())
        continue;

      Chord chord = new Chord(trimmedToken);
      chords.add(chord);
    }

    return chords;

  }

  List<Integer> getTokens(final String line) {
    List<Integer> integer = new ArrayList<Integer>();

    boolean inChord = false;
    for (int i = 0; i < line.length(); i++) {
      boolean whitespace = Character.isWhitespace(line.charAt(i));
      if (whitespace)
        inChord = false;

      if (inChord == false && !whitespace) {
        integer.add(i);
        inChord = true;
      }

    }

    return integer;

  }


}
