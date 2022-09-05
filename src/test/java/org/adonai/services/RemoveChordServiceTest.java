package org.adonai.services;

import org.adonai.SongTestData;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.junit.Assert;
import org.junit.Test;

public class RemoveChordServiceTest {

  @Test
  public void removeChordInFirstLinePart () {
    Song song = SongTestData.getSongWithOnePart();
    SongCursor songCursor = new SongCursor(song, 0, 0, 0, 0);
    RemoveChordService removeChordService = new RemoveChordService();
    removeChordService.removeChord(songCursor);

    Line firstLine = song.getFirstPart().getFirstLine();
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
    RemoveChordService removeChordService = new RemoveChordService();
    removeChordService.removeChord(songCursor);

    Line firstLine = song.getFirstPart().getFirstLine();
    Assert.assertEquals (1, firstLine.getLineParts().size());
    Assert.assertEquals ("Text invalid", "That isone testline", firstLine.getFirstLinePart().getText());
    Assert.assertEquals ("Chord invalid", "D", firstLine.getFirstLinePart().getChord());

    Assert.assertEquals ("Position", Integer.valueOf(7), songCursor.getPositionInLinePart());





  }
}
