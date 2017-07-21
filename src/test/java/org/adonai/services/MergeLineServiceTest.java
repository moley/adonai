package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.SongTestData;
import org.adonai.model.LinePart;
import org.adonai.model.Song;

public class MergeLineServiceTest {

  MergeLineService songService = new MergeLineService();


  @Test
  public void mergePositionFirstLineOfFirstPartDoesNothing () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 0);
    System.out.println(songWithTwoParts);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    System.out.println(songWithTwoParts);

    Assert.assertEquals ("Focus incorrect", cursor.getCurrentLinePart(), focusedLinePart);
  }

  @Test
  public void mergePositionFirstLineOfSecondPartMergesPartWithPreviousPart () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 1, 0, 0, 0);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    System.out.println(songWithTwoParts);
    Assert.assertEquals ("Wrong focused part", focusedLinePart, cursor.getCurrentLinePart());
    Assert.assertEquals (1, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (2, songWithTwoParts.getFirstSongPart().getLines().size());
    System.out.println(songWithTwoParts);  }

  @Test
  public void mergePositionSecondLineOfFirstPartMergesLineWithPreviousLine () {
    Song songWithTwoParts = SongTestData.getSongWithTwoPartsTwoLines();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 1, 0, 0);
    System.out.println(songWithTwoParts);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    Assert.assertEquals ("Wrong focused part", focusedLinePart, songWithTwoParts.getFirstSongPart().getFirstLine().getLastLinePart());
    Assert.assertEquals (2, songWithTwoParts.getSongParts().size());
    Assert.assertEquals (1, songWithTwoParts.getFirstSongPart().getLines().size());
    System.out.println(songWithTwoParts);
  }

  @Test
  public void mergePositionNotAtBeginningOfFirstPartDoesNothing () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 0, 3);
    System.out.println(songWithTwoParts);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    System.out.println(songWithTwoParts);

    Assert.assertEquals ("Focus incorrect", cursor.getCurrentLinePart(), focusedLinePart);
  }

  @Test
  public void mergePositionAtBeginningOfSecondPartDoesNothing () {
    Song songWithTwoParts = SongTestData.getSongWithTwoParts();
    SongCursor cursor = new SongCursor(songWithTwoParts, 0, 0, 1, 0);
    System.out.println(songWithTwoParts);
    LinePart focusedLinePart = songService.mergeLine(cursor);
    System.out.println(songWithTwoParts);

    Assert.assertEquals ("Focus incorrect", cursor.getCurrentLinePart(), focusedLinePart);
  }
}
