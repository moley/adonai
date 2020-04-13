package org.adonai.services;

import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.junit.Assert;
import org.junit.Test;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class AddPartServiceTest {

  AddPartService songService = new AddPartService();

  @Test
  public void addNewPartBeforeFirstPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);
    SongStructItem focusedLinePart = songService.addPartBefore(cursor, null, SongPartType.REFRAIN);
    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());
    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(0);
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
  }

  @Test
  public void addNewPartBeforeSecondPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);
    SongStructItem focusedLinePart = songService.addPartBefore(cursor, null, SongPartType.REFRAIN);
    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());
    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(1);
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
  }

  @Test
  public void addNewPartAfterLastPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);

    SongStructItem focusedLinePart = songService.addPartAfter(cursor,  null, SongPartType.REFRAIN);

    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());

    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(2);
    SongPart songPart = songWithTwoParts.findSongPart(songStructItem);
    Assert.assertEquals ("New songpart invalid", SongPartType.REFRAIN, songPart.getSongPartType());
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
    Assert.assertEquals ("No lines added to new songpart", 1, songPart.getLines().size());

  }

  @Test
  public void addNewPartAfterFirstPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);

    SongStructItem focusedLinePart = songService.addPartAfter(cursor,  null, SongPartType.REFRAIN);
    Assert.assertEquals (3, songWithTwoParts.getStructItems().size());
    Assert.assertEquals (3, songWithTwoParts.getSongParts().size());

    SongStructItem songStructItem = songWithTwoParts.getStructItems().get(1);
    SongPart songPart = songWithTwoParts.findSongPart(songStructItem);
    Assert.assertEquals ("New songpart invalid", SongPartType.REFRAIN, songPart.getSongPartType());
    Assert.assertEquals ("Focus linepart", focusedLinePart, songStructItem);
    Assert.assertEquals ("No lines added to new songpart", 1, songPart.getLines().size());

  }

  @Test
  public void addExistingPartBefore () {

    // VERS, REFRAIN
    // REFRAIN, VERS, REFRAIN
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());

    SongPart copiedPart = songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(1));

    Assert.assertEquals ("First structitem invalid", SongPartType.VERS, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(0)).getSongPartType());
    Assert.assertEquals ("Second structitem invalid", SongPartType.REFRAIN, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(1)).getSongPartType());

    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);
    songService.addPartBefore(cursor,  copiedPart, null);

    Assert.assertEquals ("First structitem invalid", SongPartType.REFRAIN, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(0)).getSongPartType());
    Assert.assertEquals ("Second structitem invalid", SongPartType.VERS, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(1)).getSongPartType());
    Assert.assertEquals ("Third structitem invalid", SongPartType.REFRAIN, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(2)).getSongPartType());


  }

  @Test
  public void addExistingPartAfter () {

    // VERS, REFRAIN
    // VERS, REFRAIN, REFRAIN
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getStructItems().size());

    SongPart copiedPart = songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(1));


    Assert.assertEquals ("First structitem invalid", SongPartType.VERS, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(0)).getSongPartType());
    Assert.assertEquals ("First structitem invalid", SongPartType.REFRAIN, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(1)).getSongPartType());

    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);
    songService.addPartAfter(cursor,  copiedPart, null);

    Assert.assertEquals ("First structitem invalid", SongPartType.VERS, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(0)).getSongPartType());
    Assert.assertEquals ("Second structitem invalid", SongPartType.REFRAIN, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(1)).getSongPartType());
    Assert.assertEquals ("Third structitem invalid", SongPartType.REFRAIN, songWithTwoParts.findSongPart(songWithTwoParts.getStructItems().get(2)).getSongPartType());

  }
}
