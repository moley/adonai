package org.adonai.services;

import java.util.Arrays;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.junit.Assert;
import org.junit.Test;

public class RemoveSongServiceTest {

  private RemoveSongService removeSongService = new RemoveSongService();

  @Test
  public void removeSong () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Song anotherSongWithTwoParts = SongTestData.getSongWithTwoParts();
    SongBook songBook = new SongBook();
    songBook.getSongs().addAll(Arrays.asList(songWithTwoParts, anotherSongWithTwoParts));
    Song selectedSong = removeSongService.removeSong(songWithTwoParts, songBook);
    Assert.assertEquals ("Invalid number of songs after removal", 1, songBook.getSongs().size());
    Assert.assertEquals ("Wrong song selected", anotherSongWithTwoParts, selectedSong);
  }

  @Test
  public void removeLastSong () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongBook songBook = new SongBook();
    songBook.getSongs().add(songWithTwoParts);
    Song selectedSong = removeSongService.removeSong(songWithTwoParts, songBook);
    Assert.assertEquals ("Invalid number of songs after removal", 0, songBook.getSongs().size());
    Assert.assertNull ("Wrong selected song", selectedSong);

  }
}
