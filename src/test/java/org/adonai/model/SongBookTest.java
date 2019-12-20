package org.adonai.model;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class SongBookTest {

  @Test
  public void findInvalidSong () {

    Song song1 = SongBuilder.instance().withId("1").withTitle("Hello world").get();

    SongBook songBook = new SongBook();
    songBook.setSongs(Arrays.asList(song1));
    Assert.assertNull (songBook.findSong(Integer.valueOf(2)));

  }
}
