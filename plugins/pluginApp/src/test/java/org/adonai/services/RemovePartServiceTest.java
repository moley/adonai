package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongPart;

public class RemovePartServiceTest {

  RemovePartService songService = new RemovePartService();

  @Test
  public void removeFirstOfTwoParts () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);

    SongPart focusedPart = songService.removePart(cursor);

    Assert.assertEquals ("Number of parts after removal invalid", 1, songWithTwoParts.getSongParts().size());
    SongPart notRemovedPart = songWithTwoParts.getSongParts().get(0);
    Assert.assertEquals ("Next part not focused", notRemovedPart, focusedPart);
  }

  @Test
  public void removeLastOfTwoParts () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);

    SongPart focusedPart = songService.removePart(cursor);

    Assert.assertEquals ("Number of parts after removal invalid", 1, songWithTwoParts.getSongParts().size());
    SongPart notRemovedPart = songWithTwoParts.getSongParts().get(0);
    Assert.assertEquals ("Next part not focused", notRemovedPart, focusedPart);
  }

  @Test
  public void removeOnePart () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();
    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 0);

    SongPart focusedPart = songService.removePart(cursor);

    Assert.assertEquals ("Number of parts after removal invalid", 1, songWithOnePart.getSongParts().size());
    SongPart notRemovedPart = songWithOnePart.getSongParts().get(0);
    Assert.assertEquals ("One part not focused", notRemovedPart, focusedPart);
    Assert.assertTrue ("Part not empty", notRemovedPart.getFirstLine().getText().trim().isEmpty());
  }
}
