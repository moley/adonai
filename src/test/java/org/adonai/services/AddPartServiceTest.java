package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class AddPartServiceTest {

  AddPartService songService = new AddPartService();


  @Test
  public void addPartAfterLastPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);

    System.out.println(songWithTwoParts);
    SongPart focusedLinePart = songService.addPartAfter(cursor);
    System.out.println(songWithTwoParts);

    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());
    SongPart songPart = songWithTwoParts.getSongParts().get(2);
    Assert.assertNull ("New songpart invalid", songPart.getSongPartType());
    Assert.assertEquals ("Focus linepart", focusedLinePart, songPart);
    Assert.assertEquals ("No lines added to new songpart", 1, focusedLinePart.getLines().size());

  }

  @Test
  public void addPartAfterFirstPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);

    System.out.println(songWithTwoParts);
    SongPart focusedLinePart = songService.addPartAfter(cursor);
    System.out.println(songWithTwoParts);

    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());
    SongPart songPart = songWithTwoParts.getSongParts().get(1);
    Assert.assertNull ("New songpart invalid", songPart.getSongPartType());
    Assert.assertEquals ("Focus linepart", focusedLinePart, songPart);
    Assert.assertEquals ("No lines added to new songpart", 1, focusedLinePart.getLines().size());

  }
}
