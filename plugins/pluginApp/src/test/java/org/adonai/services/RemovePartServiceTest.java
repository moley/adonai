package org.adonai.services;

import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.junit.Assert;
import org.junit.Test;

public class RemovePartServiceTest {

  RemovePartService removePartService = new RemovePartService();

  @Test
  public void removeFirstOfTwoParts () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);

    SongPart focusedPart = songWithTwoParts.findSongPart(removePartService.removePart(cursor));

    Assert.assertEquals ("Number of parts after removal invalid", 1, songWithTwoParts.getSongParts().size());
    Assert.assertEquals ("Number of structitems after removal invalid", 1, songWithTwoParts.getStructItems().size());
    SongPart notRemovedPart = songWithTwoParts.getFirstPart();
    Assert.assertEquals ("Next part not focused", notRemovedPart, focusedPart);
  }

  @Test
  public void removeLastOfTwoParts () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);

    SongPart focusedPart = songWithTwoParts.findSongPart(removePartService.removePart(cursor));
    Assert.assertEquals ("Number of parts after removal invalid", 1, songWithTwoParts.getSongParts().size());
    Assert.assertEquals ("Number of structitems after removal invalid", 1, songWithTwoParts.getStructItems().size());

    SongPart notRemovedPart = songWithTwoParts.getFirstPart();
    Assert.assertEquals ("Next part not focused", notRemovedPart, focusedPart);
  }

  @Test
  public void removeOnePart () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();
    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 0);

    SongPart focusedPart = songWithOnePart.findSongPart(removePartService.removePart(cursor));

    Assert.assertEquals ("Number of parts after removal invalid", 1, songWithOnePart.getSongParts().size());
    Assert.assertEquals ("Number of structitems after removal invalid", 1, songWithOnePart.getStructItems().size());

    SongPart notRemovedPart = songWithOnePart.getFirstPart();
    Assert.assertEquals ("One part not focused", notRemovedPart, focusedPart);
    Assert.assertTrue ("Part not empty", notRemovedPart.getFirstLine().getText().trim().isEmpty());
  }
}
