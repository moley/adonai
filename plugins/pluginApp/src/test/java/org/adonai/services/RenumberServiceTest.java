package org.adonai.services;

import java.util.Arrays;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.junit.Assert;
import org.junit.Test;

public class RenumberServiceTest {

  @Test
  public void renumber () {
    Song firstSong = SongTestData.getSongWithTwoParts();
    firstSong.setId(1);

    Song secondSong = SongTestData.getSongWithTwoParts();
    firstSong.setId(3);

    Song thirdSong = SongTestData.getSongWithTwoParts();
    firstSong.setId(3);

    SongBook songBook = new SongBook();
    songBook.getSongs().addAll(Arrays.asList(firstSong, secondSong, thirdSong));

    RenumberService renumberService = new RenumberService();
    renumberService.renumber(songBook);

    Assert.assertEquals ("ID of first song invalid", Integer.valueOf(1), firstSong.getId());
    Assert.assertEquals ("ID of second song invalid", Integer.valueOf(2), secondSong.getId());
    Assert.assertEquals ("ID of third song invalid", Integer.valueOf(3), thirdSong.getId());

  }
}
