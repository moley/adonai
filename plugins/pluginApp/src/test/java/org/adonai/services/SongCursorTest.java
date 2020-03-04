package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongStructItem;
import org.junit.Assert;
import org.junit.Test;

public class SongCursorTest {


  @Test
  public void toStringMethod () {
    SongCursor songCursor = new SongCursor();
    songCursor.setPositionInLinePart(42);
    songCursor.setCurrentLine(new Line("Hello world"));
    songCursor.setCurrentLinePart(new LinePart("Max"));
    songCursor.setCurrentSong(new Song());
    songCursor.setCurrentSongStructItem(new SongStructItem());

    Assert.assertTrue ("Invalid toString", songCursor.toString().contains("line Hello world"));

  }
}
