package org.adonai.reader.text;

import org.adonai.Chord;
import org.adonai.InvalidChordException;
import org.adonai.model.*;
import org.adonai.ui.editor.SongRepairer;

import java.util.ArrayList;
import java.util.List;

public class TextfileReader {

  private String addPendingChordLine (final String currentChordLine, final SongPart songPart) {
    if (currentChordLine != null) { //chordline as last line in part
      List<Chord> chords = getChords(currentChordLine);
      Line chordline = new Line();
      for (Chord nextChord: chords) {
        chordline.getLineParts().add(new LinePart(nextChord));
      }
      songPart.getLines().add(chordline);

    }

    return null;

  }

  public Song read(List<String> lines) {

    Song song = new Song();

    SongPart currentSongPart = null;

    String currentChordLine = null;
    for (int linenumber = 0; linenumber < lines.size(); linenumber++) {
      String next = lines.get(linenumber);
      if (next.trim().isEmpty())
        continue;


      SongPart newSongPart = findSongPart(next); //Rest
      if (newSongPart != null) {
        currentChordLine = addPendingChordLine(currentChordLine, currentSongPart);
        currentSongPart = newSongPart;
        song.getSongParts().add(currentSongPart);
        continue;
      } else if (currentSongPart == null) { //Beginning: Title
        song.setTitle(next.toUpperCase());
        continue;
      }

      if (isChordLine(next)) { //read chord line
        if (currentChordLine != null)
          addPendingChordLine(currentChordLine, currentSongPart);

        currentChordLine = next;
      }
      else { //read text line

        Line newLine = new Line();
        if (currentChordLine == null) {
          newLine.getLineParts().add(new LinePart(next));
        } else {

            List<Integer> tokens = getTokens(currentChordLine);
            List<Chord> chords = getChords(currentChordLine);
            if (tokens.get(0) > 0) { //Line in front ot first chord
              newLine.getLineParts().add(new LinePart(next.substring(0, tokens.get(0)), (String) null));
            }

            for (int i = 0; i < tokens.size(); i++) {
              Integer from = tokens.get(i);
              Integer to = next.length();
              if (i < tokens.size() - 1)
                to = tokens.get(i + 1);
              try {
                Chord chord = chords.get(i);
                Integer toOrEnmd = Integer.min(to.intValue(), next.length());
                String text = from.intValue() < toOrEnmd ? next.substring(from.intValue(), toOrEnmd) : " ";

                newLine.getLineParts().add(new LinePart(text, chord));
              } catch (Exception e) {
                throw new IllegalStateException("From " + from + " to " + to + "(" + i + " of " + (tokens.size() - 1) + ") in line " + linenumber + "(" + next + ")", e);

              }


          }

          currentChordLine = null;
        }


        currentSongPart.getLines().add(newLine);

      }

    }

    currentChordLine = addPendingChordLine(currentChordLine, currentSongPart);

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);

    return song;
  }

  SongPart findSongPart(final String line) {
    if (line.contains("[") && line.contains("]")) {
      String type = line.replace("[", "").replace("]", "").trim().toUpperCase();
      if (type.equals("STROPHE"))
        type = SongPartType.VERS.name();

      SongPartType determinedSongPart = SongPartType.valueOf(type);
      if (determinedSongPart == null)
        throw new IllegalStateException("Part " + type + " not found, please add mapping");

      SongPart songPart = new SongPart();
      songPart.setSongPartType(determinedSongPart);
      return songPart;
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
