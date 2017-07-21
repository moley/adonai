package org.adonai.services;

import org.junit.Test;
import org.adonai.Key;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartType;

public class SongTransposeServiceTest {

  private SongTransposeService songTransposeService = new SongTransposeService();

  private Song createSongInC () {
    SongBuilder songbuilder = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "C");
    songbuilder = songbuilder.withLinePart("a test", "F");
    songbuilder = songbuilder.withLinePart("of transformations", "G");
    songbuilder = songbuilder.withLinePart("of the song", "C");

    return songbuilder.get();

  }

  @Test
  public void transposeUp () {
    Song song = createSongInC();
    System.out.println ("In C:" + song.toString());
    songTransposeService.transpose(song, Key.C, Key.G);


    System.out.println ("In G:" + song.toString());

  }

  @Test
  public void transposeDown () {
    Song song = createSongInC();

  }

  @Test
  public void transposeUpBorder () {
    Song song = createSongInC();

  }

  @Test
  public void transposeDownBorder () {
    Song song = createSongInC();

  }


}
