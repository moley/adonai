package org.adonai.reader.word;

import org.adonai.InvalidChordException;
import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.Chord;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 15.09.16.
 */
public class WordReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordReader.class);



  private ITokenizer wordTokenizer = new WordTokenizer();


  public Collection<Song> read (final InputStream inputStream) throws IOException {

    Collection<Song> songs = new ArrayList<Song>();

    Song currentSong = null;
    SongPart currentSongPart = null;

    for (String paragraph: wordTokenizer.getTokens(inputStream)) {


      if (paragraph.trim().isEmpty()) {  //new paragraph
        if (currentSongPart == null || !currentSongPart.isEmpty()) {
          currentSongPart = new SongPart();
          currentSong.getSongParts().add(currentSongPart);
        }
        continue;
      }



      try {

        if (Character.isDigit(paragraph.trim().charAt(0))) {
          String[] tokens = paragraph.trim().split("\\s+");
          String id = tokens[0];
          String title = paragraph.trim().substring(id.length()).trim();
          currentSong = new Song();
          currentSong.setTitle(title);
          currentSong.setId(Integer.valueOf(id));
          currentSongPart = null;
          songs.add(currentSong);
          LOGGER.info("Determined title <" + paragraph + ">");
        } else {
          String[] lines = paragraph.split("\n");
          for (String nextLine : lines) {

            nextLine = nextLine.replaceAll("\\s+", " ");
            nextLine = nextLine.trim();

            SongPartType songPartType = extractPartType(nextLine);
            if (songPartType != null) {
              nextLine = nextLine.replace(songPartType.name().toUpperCase(), "").trim();
              currentSongPart.setSongPartType(songPartType);
            }

            String quantity = extractQuantity(nextLine);
            if (quantity != null) {
              currentSongPart.setQuantity(quantity.substring(0, quantity.length() - 1));
              nextLine = nextLine.replace(quantity + " ", "");
            }

            if (nextLine.isEmpty()) {
              if (currentSongPart == null || !currentSongPart.isEmpty()) {
                currentSongPart = new SongPart();
                currentSong.getSongParts().add(currentSongPart);
              }
            }

            boolean noChordAvailable = false;

            if (nextLine.isEmpty())
              noChordAvailable = true;

            try {
              Collection<Chord> chords = getChords(nextLine);
            } catch (InvalidChordException e) {
              noChordAvailable = true;
            }

            if (noChordAvailable && !nextLine.isEmpty()) {
              Line newLine = new Line(nextLine);
              currentSongPart.getLines().add(newLine);

            }
          }

        }

      } catch (Exception e) {
        throw new IllegalStateException("Error in reading " + (currentSong != null ? currentSong.getTitle() : "") + ":", e);

      }


    }

    //Remove empty song parts
    for (Song nextSong : songs) {
      Collection<SongPart> emptyParts = new ArrayList<SongPart>();
      for (SongPart nextPart : nextSong.getSongParts()) {
        if (nextPart.isEmpty())
          emptyParts.add(nextPart);
      }
      nextSong.getSongParts().removeAll(emptyParts);
    }

    return songs;
  }

  private String extractQuantity(String line) {
    for (String next: line.split(" ")) {
      if (next.endsWith("x"))
        return next;
    }
    return null;
  }

  private SongPartType extractPartType (String line) {
    for (SongPartType next: SongPartType.values()) {
      if (line.toUpperCase().contains(next.name().toUpperCase())) {

        return next;
      }
    }

    return null;

  }

  public Collection<Chord> getChords(final String line) throws InvalidChordException {

    Collection<Chord> chords = new ArrayList<Chord>();

    for (String nextChord : line.split(" ")) {
      nextChord = nextChord.trim();
      if (nextChord.trim().isEmpty()) {
        continue;
      }


      Chord chordAsObject = new Chord(nextChord);
      chords.add(chordAsObject);
      if (LOGGER.isDebugEnabled())
        LOGGER.debug(">" + nextChord + "< is a chord");
    }

    return chords;

  }

  public ITokenizer getWordTokenizer() {
    return wordTokenizer;
  }

  public void setWordTokenizer(ITokenizer wordTokenizer) {
    this.wordTokenizer = wordTokenizer;
  }
}
