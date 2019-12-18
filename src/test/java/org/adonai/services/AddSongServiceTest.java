package org.adonai.services;

import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.junit.Assert;
import org.junit.Test;

public class AddSongServiceTest {

  private AddSongService addSongService = new AddSongService();

  @Test public void addSong() {
    SongBook songBook = new SongBook();
    Song firstSong = new Song();
    Song secondSong = new Song();
    addSongService.addSong(firstSong, songBook);
    addSongService.addSong(secondSong, songBook);

    Assert.assertEquals(Integer.valueOf(1), firstSong.getId());
    Assert.assertEquals(Integer.valueOf(2), secondSong.getId());
  }

  @Test public void createSong() {
    Song song = addSongService.createSong("My way");
    Assert.assertEquals("MY WAY", song.getTitle());
    Assert.assertEquals(1, song.getSongParts().size());

  }
}
