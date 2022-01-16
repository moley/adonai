package org.adonai.services;

import org.adonai.SongTestData;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.junit.Assert;
import org.junit.Test;

public class MoveChordServiceTest {

  @Test
  public void toLeft () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();
    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 1, 0);
    MoveChordService moveChordService = new MoveChordService();
    moveChordService.toLeft(cursor);

    Line line = songWithOnePart.getFirstPart().getFirstLine();
    LinePart linePart1 = line.getLineParts().get(0);
    LinePart linePart2 = line.getLineParts().get(1);
    Assert.assertEquals ("That i", linePart1.getText());
    Assert.assertEquals ("sone testline", linePart2.getText());
  }

  @Test
  public void toRight () {
    Song songWithOnePart = SongTestData.getSongWithOnePart();

    SongCursor cursor = new SongCursor(songWithOnePart, 0, 0, 1, 0);
    MoveChordService moveChordService = new MoveChordService();
    moveChordService.toRight(cursor);

    Line line = songWithOnePart.getFirstPart().getFirstLine();
    LinePart linePart1 = line.getLineParts().get(0);
    LinePart linePart2 = line.getLineParts().get(1);
    Assert.assertEquals ("That iso", linePart1.getText());
    Assert.assertEquals ("ne testline", linePart2.getText());

  }
}
