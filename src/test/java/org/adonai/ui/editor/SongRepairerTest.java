package org.adonai.ui.editor;

import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartType;
import org.junit.Assert;
import org.junit.Test;

public class SongRepairerTest {

  @Test
  public void removeEmptyLine () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    Assert.assertEquals (2, song.getFirstSongPart().getLines().size());
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    System.out.println (song.toString());
    Assert.assertEquals (1, song.getFirstSongPart().getLines().size());

  }
}
