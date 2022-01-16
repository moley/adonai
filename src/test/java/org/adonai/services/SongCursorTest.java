package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.junit.Assert;
import org.junit.Test;

public class SongCursorTest {


  @Test
  public void toStringMethod () {

    Song song = SongBuilder.instance().withPart(SongPartType.REFRAIN).withPartId("1").withLine().withLinePart("Max is the min under the average", null).get();
    SongCursor songCursor = new SongCursor();
    songCursor.setPositionInLinePart(42);
    songCursor.setCurrentLine(song.getFirstPart().getFirstLine());
    songCursor.setCurrentLinePart(song.getFirstPart().getFirstLine().getFirstLinePart());
    songCursor.setCurrentSong(song);
    songCursor.setCurrentSongStructItem(song.getStructItems().get(0));

    Assert.assertTrue ("Invalid toString (" + songCursor.toString() + ")", songCursor.toString().contains("line Max is the"));

  }
}
