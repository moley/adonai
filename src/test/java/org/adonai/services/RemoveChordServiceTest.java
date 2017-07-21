package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.SongTestData;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;

public class RemoveChordServiceTest {

  @Test
  public void removeChordInFirstLinePart () {
    Song song = SongTestData.getSongWithOnePart();
    SongCursor songCursor = new SongCursor(song, 0, 0, 0, 0);
    System.out.println ("Before: " + song);
    RemoveChordService removeChordService = new RemoveChordService();
    removeChordService.removeChord(songCursor);

    System.out.println ("After: " + song);
    Line firstLine = song.getFirstSongPart().getFirstLine();
    Assert.assertEquals (2, firstLine.getLineParts().size());
    LinePart firstLinePart = firstLine.getFirstLinePart();
    LinePart secondLinePart = firstLine.getLastLinePart();
    Assert.assertEquals ("Text invalid", "That is", firstLinePart.getText());
    Assert.assertNull ("Chord invalid", firstLinePart.getChord());

    Assert.assertEquals ("Text invalid", "one testline", secondLinePart.getText());
    Assert.assertEquals ("Chord invalid", "G", secondLinePart.getChord());
    Assert.assertNull ("Position", songCursor.getPositionInLinePart());

  }

  @Test
  public void removeChordInNonFirstLinePart () {
    Song song = SongTestData.getSongWithOnePart();
    SongCursor songCursor = new SongCursor(song, 0, 0, 1, 0);
    System.out.println ("Before: " + song);
    RemoveChordService removeChordService = new RemoveChordService();
    removeChordService.removeChord(songCursor);

    System.out.println ("After: " + song);
    Line firstLine = song.getFirstSongPart().getFirstLine();
    Assert.assertEquals (1, firstLine.getLineParts().size());
    Assert.assertEquals ("Text invalid", "That isone testline", firstLine.getFirstLinePart().getText());
    Assert.assertEquals ("Chord invalid", "D", firstLine.getFirstLinePart().getChord());

    Assert.assertEquals ("Position", new Integer(7), songCursor.getPositionInLinePart());





  }
}
