package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.model.SongBook;
import org.adonai.model.Song;
import org.adonai.services.AddSongService;

public class AddSongServiceTest {

  @Test
  public void addSong () {
    SongBook songBook = new SongBook();
    AddSongService addSongService = new AddSongService();
    Song firstSong = new Song();
    Song secondSong = new Song();
    addSongService.addSong(firstSong, songBook);
    addSongService.addSong(secondSong, songBook);

    Assert.assertEquals (new Integer(1), firstSong.getId());
    Assert.assertEquals (new Integer(2), secondSong.getId());

  }
}
