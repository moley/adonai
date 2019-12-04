package org.adonai.services;

import org.adonai.SongTestData;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.junit.Assert;
import org.junit.Test;

public class MergeLineServiceTest {

  MergeLineService songService = new MergeLineService();


  @Test
  public void mergePositionFirstLineOfFirstPartDoesNothing () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);
    LinePart focusedLinePart = songService.mergeLine(cursor);

    Assert.assertEquals ("Focus incorrect", cursor.getCurrentLinePart(), focusedLinePart);
  }

  @Test
  public void mergePositionFirstLineOfSecondPartDoesNothing () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    Assert.assertEquals ("Wrong focused part", focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getSongParts().get(0).getLines().size());
    Assert.assertEquals (1, songWithTwoParts.getSongParts().get(1).getLines().size());

  }

  @Test
  public void mergePositionSecondLineOfFirstPartMergesLineWithPreviousLine () {
    Song songWithTwoParts = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 1, 0, 0);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    Assert.assertEquals ("Wrong focused part", focusedLinePart, songWithTwoParts.getFirstSongPart().getFirstLine().getLastLinePart());
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (1, songWithTwoParts.getFirstSongPart().getLines().size());
  }

  @Test
  public void mergePositionNotAtBeginningOfFirstPartDoesNothing () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 3);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    Assert.assertEquals ("Focus incorrect", cursor.getCurrentLinePart(), focusedLinePart);
  }

  @Test
  public void mergePositionAtBeginningOfSecondPartDoesNothing () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 1, 0);

    LinePart focusedLinePart = songService.mergeLine(cursor);

    Assert.assertEquals ("Focus incorrect", cursor.getCurrentLinePart(), focusedLinePart);
  }
}
