package org.adonai.services;

import java.util.List;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.SongPart;
import org.junit.Assert;
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

  @Test  //C->G     //A->D
  public void recalculateOrigin () {
    Song song = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "A", "F").get();
    song.setOriginalKey("C");
    song.setCurrentKey("G");
    songTransposeService.recalculateOrigin(song);
    Assert.assertEquals ("D", song.getSongParts().get(0).getFirstLine().getFirstLinePart().getOriginalChord());

  }

  @Test  //C->G     //A->D
  public void recalculateOriginCrossToBb () {
    Song song = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "G").get();
    song.setOriginalKey("Db");
    song.setCurrentKey("G");
    songTransposeService.recalculateOrigin(song);
    Assert.assertEquals ("Db", song.getSongParts().get(0).getFirstLine().getFirstLinePart().getOriginalChord());
  }

  @Test   //C->G     //F->Bb
  public void recalculateCurrent () {
    Song song = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "Bb", "F").get();
    song.setOriginalKey("C");
    song.setCurrentKey("G");
    songTransposeService.recalculateCurrent(song);
    Assert.assertEquals ("C", song.getSongParts().get(0).getFirstLine().getFirstLinePart().getChord());
  }




}
