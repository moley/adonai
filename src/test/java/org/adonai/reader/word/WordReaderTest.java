package org.adonai.reader.word;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.model.SongPartType;
import org.adonai.InvalidChordException;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by OleyMa on 15.09.16.
 */
public class WordReaderTest {

  @Test
  public void tabInChordLine () throws InvalidChordException {
    String chordLine = "G                                C               G             D            G";
    WordReader reader = new WordReader();
    reader.getChords(chordLine);
  }

  @Test
  public void title () throws IOException {
    DefaultTokenizer tokenizer = new DefaultTokenizer();
    tokenizer = tokenizer.add("1			EVERLASTING GOD");

    WordReader reader = new WordReader();
    reader.setWordTokenizer(tokenizer);

    Collection<Song> songs = reader.read(null);
    Song firstSong = songs.iterator().next();
    Assert.assertEquals ("EVERLASTING GOD", firstSong.getTitle());

  }

  @Test
  public void partType () throws IOException {
    DefaultTokenizer tokenizer = new DefaultTokenizer();
    tokenizer = tokenizer.add("1    EVERLASTING GOD");
    tokenizer = tokenizer.add("");
    tokenizer = tokenizer.add("       G       H       C");
    tokenizer = tokenizer.add("VERS 2x Hallo, dies ist ein Test");

    WordReader reader = new WordReader();
    reader.setWordTokenizer(tokenizer);

    Collection<Song> songs = reader.read(null);
    Song firstSong = songs.iterator().next();
    SongPart songPart = firstSong.getSongParts().get(0);
    Assert.assertEquals (SongPartType.VERS, songPart.getSongPartType());

  }

  @Test
  public void id () throws IOException {
    DefaultTokenizer tokenizer = new DefaultTokenizer();
    tokenizer = tokenizer.add("1			EVERLASTING GOD");

    WordReader reader = new WordReader();
    reader.setWordTokenizer(tokenizer);

    Collection<Song> songs = reader.read(null);
    Song firstSong = songs.iterator().next();
    Assert.assertEquals (new Integer("1"), firstSong.getId());

  }

  @Test
  public void numbers () throws IOException {
    DefaultTokenizer tokenizer = new DefaultTokenizer();
    tokenizer = tokenizer.add("1			EVERLASTING GOD").add("").add("");
    tokenizer = tokenizer.add("ZWISCHEN  6x	I will worship,             I will worship, You");

    WordReader reader = new WordReader();
    reader.setWordTokenizer(tokenizer);

    Collection<Song> songs = reader.read(null);
    Song firstSong = songs.iterator().next();
    System.out.println (firstSong.getSongParts().get(0).getLines().get(0).getText());


  }

  @Test
  public void songpart () throws IOException {
    DefaultTokenizer tokenizer = new DefaultTokenizer();
    tokenizer = tokenizer.add("1			EVERLASTING GOD").add("").add("");
    tokenizer = tokenizer.add("ZWISCHENSPIEL I will worship,             I will worship, You");

    WordReader reader = new WordReader();
    reader.setWordTokenizer(tokenizer);

    Collection<Song> songs = reader.read(null);
    Song firstSong = songs.iterator().next();
    Assert.assertEquals ("I will worship, I will worship, You", firstSong.getSongParts().get(0).getLines().get(0).getText());



  }

}
