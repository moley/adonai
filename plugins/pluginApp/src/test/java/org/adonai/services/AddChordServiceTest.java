package org.adonai.services;

import org.junit.Assert;
import org.junit.Test;
import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.Chord;
import org.adonai.SongTestData;

public class AddChordServiceTest {

  @Test
  public void add () {

    Song song = SongTestData.getSongWithOnePart();
    Line linePart = song.getSongParts().get(0).getLines().get(0);
    Assert.assertEquals (2, linePart.getLineParts().size());

    AddChordService addChordService = new AddChordService();
    addChordService.addChord(new SongCursor(song, 0, 0, 0, 4), new Chord("A"));
    Assert.assertEquals (3, linePart.getLineParts().size());
    Assert.assertEquals ("D", linePart.getLineParts().get(0).getChord());
    Assert.assertEquals ("That", linePart.getLineParts().get(0).getText());
    Assert.assertEquals ("A", linePart.getLineParts().get(1).getChord());
    Assert.assertEquals (" is", linePart.getLineParts().get(1).getText());
    Assert.assertEquals ("G", linePart.getLineParts().get(2).getChord());
    Assert.assertEquals ("one testline", linePart.getLineParts().get(2).getText());


  }
}
