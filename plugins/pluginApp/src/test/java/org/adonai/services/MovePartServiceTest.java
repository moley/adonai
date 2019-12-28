package org.adonai.services;

import org.adonai.SongTestData;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPartType;
import org.junit.Assert;
import org.junit.Test;

public class MovePartServiceTest {


  private MovePartService movePartService = new MovePartService();

  @Test
  public void moveFirstPartUp () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartUp(cursor);
    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, songWithOnePart.getSongParts().size());
    Assert.assertEquals (SongPartType.VERS, songWithOnePart.getSongParts().get(0).getSongPartType());
    Assert.assertEquals (SongPartType.REFRAIN, songWithOnePart.getSongParts().get(1).getSongPartType());

  }

  @Test
  public void moveLastPartUp () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(songWithOnePart, 1, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartUp(cursor);

    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, songWithOnePart.getSongParts().size());
    Assert.assertEquals (SongPartType.REFRAIN, songWithOnePart.getSongParts().get(0).getSongPartType());
    Assert.assertEquals (SongPartType.VERS, songWithOnePart.getSongParts().get(1).getSongPartType());

  }

  @Test
  public void moveFirstPartDown () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartDown(cursor);
    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, songWithOnePart.getSongParts().size());
    Assert.assertEquals (SongPartType.REFRAIN, songWithOnePart.getSongParts().get(0).getSongPartType());
    Assert.assertEquals (SongPartType.VERS, songWithOnePart.getSongParts().get(1).getSongPartType());

  }

  @Test
  public void moveLastPartDown () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(songWithOnePart, 1, 1, 0, 0);
    LinePart focusedLinePart = movePartService.movePartDown(cursor);
    Assert.assertEquals (focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, songWithOnePart.getSongParts().size());
    Assert.assertEquals (SongPartType.VERS, songWithOnePart.getSongParts().get(0).getSongPartType());
    Assert.assertEquals (SongPartType.REFRAIN, songWithOnePart.getSongParts().get(1).getSongPartType());

  }
}
