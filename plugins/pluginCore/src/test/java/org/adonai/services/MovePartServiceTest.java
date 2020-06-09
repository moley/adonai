package org.adonai.services;

import org.adonai.SongTestData;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.junit.Assert;
import org.junit.Test;

public class MovePartServiceTest {


  private MovePartService movePartService = new MovePartService();

  @Test
  public void moveFirstPartUp () {
    Song song = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(song, 0, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartUp(cursor);

    SongPart part1 = song.findSongPart(song.getStructItems().get(0));
    SongPart part2 = song.findSongPart(song.getStructItems().get(1));

    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, song.getStructItems().size());
    Assert.assertEquals (2, song.getSongParts().size());

    Assert.assertEquals (SongPartType.VERS, part1.getSongPartType());
    Assert.assertEquals (SongPartType.REFRAIN, part2.getSongPartType());

  }

  @Test
  public void moveValidPartUp () {
    Song song = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(song, 1, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartUp(cursor);

    SongPart part1 = song.findSongPart(song.getStructItems().get(0));
    SongPart part2 = song.findSongPart(song.getStructItems().get(1));

    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, song.getStructItems().size());
    Assert.assertEquals (2, song.getSongParts().size());
    Assert.assertEquals (SongPartType.REFRAIN, part1.getSongPartType());
    Assert.assertEquals (SongPartType.VERS, part2.getSongPartType());

  }

  @Test
  public void movePartUpInMultiVerseSong () {
    Song song = SongTestData.getSongWithTwoVerses();
    System.out.println ("Before: " + song.getStructItems());
    SongCursor cursor = new SongCursor(song, 1, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartUp(cursor);
    System.out.println ("After: " + song.getStructItems());


    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (4, song.getStructItems().size());
    Assert.assertEquals ("VERS 2", song.getStructItems().get(0).getText());
    Assert.assertEquals ("VERS 1", song.getStructItems().get(1).getText());
    Assert.assertEquals ("VERS 1", song.getStructItems().get(2).getText());
    Assert.assertEquals ("VERS 2", song.getStructItems().get(3).getText());

  }

  @Test
  public void movePartDownInMultiVerseSong () {
    Song song = SongTestData.getSongWithTwoVerses();
    System.out.println ("Before: " + song.getStructItems());
    SongCursor cursor = new SongCursor(song, 0, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartDown(cursor);
    System.out.println ("After: " + song.getStructItems());


    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (4, song.getStructItems().size());
    Assert.assertEquals ("VERS 2", song.getStructItems().get(0).getText());
    Assert.assertEquals ("VERS 1", song.getStructItems().get(1).getText());
    Assert.assertEquals ("VERS 1", song.getStructItems().get(2).getText());
    Assert.assertEquals ("VERS 2", song.getStructItems().get(3).getText());

  }

  @Test
  public void moveFirstPartDown () {
    Song song = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(song, 0, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartDown(cursor);

    SongPart part1 = song.findSongPart(song.getStructItems().get(0));
    SongPart part2 = song.findSongPart(song.getStructItems().get(1));

    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, song.getStructItems().size());
    Assert.assertEquals (2, song.getSongParts().size());
    Assert.assertEquals (SongPartType.REFRAIN, part1.getSongPartType());
    Assert.assertEquals (SongPartType.VERS, part2.getSongPartType());

  }

  @Test
  public void moveLastPartDown () {
    Song song = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(song, 1, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartDown(cursor);

    SongPart part1 = song.findSongPart(song.getStructItems().get(0));
    SongPart part2 = song.findSongPart(song.getStructItems().get(1));

    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, song.getStructItems().size());
    Assert.assertEquals (2, song.getSongParts().size());

    Assert.assertEquals (SongPartType.VERS, part1.getSongPartType());
    Assert.assertEquals (SongPartType.REFRAIN, part2.getSongPartType());

  }
}
