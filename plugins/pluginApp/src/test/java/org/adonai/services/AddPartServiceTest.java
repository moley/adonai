package org.adonai.services;

import org.adonai.model.SongStructItem;
import org.junit.Assert;
import org.junit.Test;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class AddPartServiceTest {

  AddPartService songService = new AddPartService();

  @Test
  public void addPartBeforeFirstPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);
    SongStructItem focusedLinePart = songService.addPartBefore(cursor);
    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());
    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(0);
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
  }

  @Test
  public void addPartBeforeSecondPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);
    SongStructItem focusedLinePart = songService.addPartBefore(cursor);
    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());
    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(1);
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
  }

  @Test
  public void addPartAfterLastPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);

    SongStructItem focusedLinePart = songService.addPartAfter(cursor);

    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());

    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(2);
    SongPart songPart = songWithTwoParts.findSongPart(songStructItem);
    Assert.assertNull ("New songpart invalid", songPart.getSongPartType());
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
    Assert.assertEquals ("No lines added to new songpart", 1, songPart.getLines().size());

  }

  @Test
  public void addPartAfterFirstPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);

    System.out.println ("Parts: " + songWithTwoParts.getSongParts());
    System.out.println ("StructItems: " + songWithTwoParts.getStructItems());

    SongStructItem focusedLinePart = songService.addPartAfter(cursor);

    System.out.println ("Parts: " + songWithTwoParts.getSongParts());
    System.out.println ("StructItems: " + songWithTwoParts.getStructItems());

    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());

    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(1);
    SongPart songPart = songWithTwoParts.findSongPart(songStructItem);
    Assert.assertNull ("New songpart invalid", songPart.getSongPartType());
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
    Assert.assertEquals ("No lines added to new songpart", 1, songPart.getLines().size());

  }
}
