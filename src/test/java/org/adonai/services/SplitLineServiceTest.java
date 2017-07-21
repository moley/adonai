package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.SongTestData;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;

public class SplitLineServiceTest {

  private SplitLineService splitLineService = new SplitLineService();

  @Test
  public void splitLineBeginningOfSecondLinePart () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 1, 0);

    System.out.println(songWithOnePart);
    LinePart focusedLinePart = splitLineService.splitLine(cursor);
    System.out.println(songWithOnePart);

    Assert.assertEquals (2, cursor.getCurrentSongPart().getLines().size());
    Line firstLine = cursor.getCurrentSongPart().getLines().get(0);
    Assert.assertEquals ("Number of songparts on first line invalid", 1, firstLine.getLineParts().size());
    Assert.assertEquals ("First line chord invalid", "D", firstLine.getFirstLinePart().getChord());
    Assert.assertEquals ("First line text invalid", "That is", firstLine.getFirstLinePart().getText());

    Line secondLine = cursor.getCurrentSongPart().getLines().get(1);
    Assert.assertEquals ("Number of songparts on first line invalid", 1, secondLine.getLineParts().size());
    Assert.assertEquals ("Second line second part chord invalid", "G", secondLine.getLineParts().get(0).getChord());
    Assert.assertEquals ("Second line second part text invalid", "one testline", secondLine.getLineParts().get(0).getText());

    Assert.assertEquals ("Focused linepart invalid", secondLine.getFirstLinePart(), focusedLinePart);
  }

  @Test
  public void splitLineBeginning () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();
    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 0);
    System.out.println(songWithOnePart);
    LinePart focusedLinePart = splitLineService.splitLine(cursor);
    System.out.println(songWithOnePart);

    Assert.assertEquals (2, cursor.getCurrentSongPart().getLines().size());
    Assert.assertTrue ("Empty part not added in first line", cursor.getCurrentSongPart().getLines().get(0).getFirstLinePart().getText().trim().isEmpty());
    Assert.assertEquals ("Wrong part focused", cursor.getCurrentSongPart().getLines().get(1).getFirstLinePart(), focusedLinePart);
  }

  @Test
  public void splitLineEnd () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();
    int lastIndex = songWithOnePart.getSongParts().get(0).getLines().get(0).getFirstLinePart().getText().length();
    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, lastIndex);
    System.out.println(songWithOnePart);
    LinePart focusedLinePart = splitLineService.splitLine(cursor);
    System.out.println(songWithOnePart);
    Assert.assertEquals (2, songWithOnePart.getSongParts().get(0).getLines().size());
    Assert.assertTrue ("Wrong type", cursor.getCurrentSongPart().getLines().get(1).getFirstLinePart().getText().trim().isEmpty());
    Assert.assertEquals ("Wrong part focused", cursor.getCurrentSongPart().getLines().get(1).getFirstLinePart(), focusedLinePart);
    Assert.assertEquals (1, focusedLinePart.getText().length());
  }

  @Test
  public void splitLineMidOfLinePart () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 0, 4);

    System.out.println(songWithOnePart);
    LinePart focusedLinePart = splitLineService.splitLine(cursor);
    System.out.println(songWithOnePart);

    Assert.assertEquals (2, cursor.getCurrentSongPart().getLines().size());
    Line firstLine = cursor.getCurrentSongPart().getLines().get(0);
    Assert.assertEquals ("Number of songparts on first line invalid", 1, firstLine.getLineParts().size());
    Assert.assertEquals ("First line chord invalid", "D", firstLine.getFirstLinePart().getChord());
    Assert.assertEquals ("First line text invalid", "That", firstLine.getFirstLinePart().getText());

    Line secondLine = cursor.getCurrentSongPart().getLines().get(1);
    Assert.assertEquals ("Number of songparts on first line invalid", 2, secondLine.getLineParts().size());
    Assert.assertNull ("Second line first part chord invalid", secondLine.getLineParts().get(0).getChord());
    Assert.assertEquals ("Second line first part text invalid", " is", secondLine.getLineParts().get(0).getText());
    Assert.assertEquals ("Second line second part chord invalid", "G", secondLine.getLineParts().get(1).getChord());
    Assert.assertEquals ("Second line second part text invalid", "one testline", secondLine.getLineParts().get(1).getText());

    Assert.assertEquals ("Focused linepart invalid", secondLine.getFirstLinePart(), focusedLinePart);
  }
}
