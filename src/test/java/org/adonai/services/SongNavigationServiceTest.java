package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.model.LinePart;
import org.adonai.SongTestData;
import org.adonai.model.Song;

public class SongNavigationServiceTest {

  SongNavigationService songService = new SongNavigationService();

  @Test
  public void stepToPreviousLineNonFirst () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToPreviousLine(cursor);
    Assert.assertEquals ("Focus invalid", songWithOnePart.getFirstSongPart().getFirstLine().getFirstLinePart(), focusedLinePart);

  }

  @Test
  public void stepToPreviousLineFirst () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 0);
    LinePart focusedLinePart = songService.stepToPreviousLine(cursor);
    Assert.assertEquals ("Focus invalid", songWithOnePart.getFirstSongPart().getFirstLine().getFirstLinePart(), focusedLinePart);

  }

  @Test
  public void stepToNextLineLast () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToNextLine(cursor);
    Assert.assertEquals ("Focus invalid", songWithOnePart.getFirstSongPart().getLastLine().getFirstLinePart(), focusedLinePart);

  }

  @Test
  public void stepToNextLineNotLast () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 0);
    LinePart focusedLinePart = songService.stepToNextLine(cursor);
    Assert.assertEquals ("Focus invalid", songWithOnePart.getFirstSongPart().getLastLine().getFirstLinePart(), focusedLinePart);

  }





  @Test
  public void stepToPreviousPartNonFirst () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 1, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToPreviousPart(cursor);
    Assert.assertEquals ("Focus invalid", songWithOnePart.getFirstSongPart().getFirstLine().getFirstLinePart(), focusedLinePart);

  }

  @Test
  public void stepToPreviousPartFirst () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToPreviousPart(cursor);
    Assert.assertEquals ("Focus invalid", cursor.getCurrentLinePart(), focusedLinePart);

  }

  @Test
  public void stepToNextPartNonLast () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToNextPart(cursor);
    Assert.assertEquals ("Focus invalid", songWithOnePart.getLastSongPart().getFirstLine().getFirstLinePart(), focusedLinePart);

  }

  @Test
  public void stepToNextPartLast () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 1, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToNextPart(cursor);
    Assert.assertEquals ("Focus invalid", cursor.getCurrentLinePart(), focusedLinePart);

  }

  @Test
  public void stepToPreviousLastPartInLine () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 1, 0);
    LinePart focusedLinePart = songService.stepToPreviousLinePart(cursor);
    Assert.assertEquals ("Focus invalid", focusedLinePart, songWithOnePart.getFirstSongPart().getFirstLine().getFirstLinePart());

  }

  @Test
  public void stepToPreviousFirstPartInLine () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToPreviousLinePart(cursor);
    Assert.assertEquals ("Focus invalid", focusedLinePart, songWithOnePart.getFirstSongPart().getFirstLine().getLastLinePart());
  }

  @Test
  public void stepToPreviousVeryFirstPartInLine () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 0);
    LinePart focusedLinePart = songService.stepToPreviousLinePart(cursor);
    Assert.assertEquals ("Focused line invalid", focusedLinePart, cursor.getCurrentLinePart());

  }

  @Test
  public void stepToNextFirstPartInLine () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 0);
    LinePart focusedLinePart = songService.stepToNextLinePart(cursor);
    Assert.assertEquals ("Focused line invalid", focusedLinePart, songWithOnePart.getFirstSongPart().getFirstLine().getLastLinePart());

  }

  @Test
  public void stepToNextLastPartInLine () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToNextLinePart(cursor);
    Assert.assertEquals ("Focused line invalid", focusedLinePart, songWithOnePart.getLastSongPart().getFirstLine().getFirstLinePart());

  }

  @Test
  public void stepToNextVeryLastPartInLine () {
    Song songWithOnePart = SongTestData.getSongWithTwoPartsTwoLines();

    SongCursor cursor = new SongCursor(songWithOnePart, 1, 1, 0, 0);
    LinePart focusedLinePart = songService.stepToNextLinePart(cursor);
    Assert.assertEquals ("Focused line invalid", focusedLinePart, cursor.getCurrentLinePart());
  }
}
