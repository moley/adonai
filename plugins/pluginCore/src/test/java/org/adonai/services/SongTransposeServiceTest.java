package org.adonai.services;

import org.adonai.Key;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartType;
import org.junit.Assert;
import org.junit.Test;

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
    LinePart linePart = song.getFirstPart().getFirstLine().getFirstLinePart();
    Assert.assertEquals ("C", linePart.getChord());
    songTransposeService.transpose(song, Key.C, Key.G);
    Assert.assertEquals ("G", linePart.getChord());
  }

  @Test
  public void transposeDown () {
    Song song = createSongInC();
    LinePart linePart = song.getFirstPart().getFirstLine().getFirstLinePart();
    Assert.assertEquals ("C", linePart.getChord());
    songTransposeService.transpose(song, Key.C, Key.Bb);
    Assert.assertEquals ("Bb", linePart.getChord());

  }

  @Test
  public void transposeUpBorder () {
    SongBuilder songbuilder = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "H");
    Song song = songbuilder.get();
    LinePart linePart = song.getFirstPart().getFirstLine().getFirstLinePart();
    Assert.assertEquals ("H", linePart.getChord());
    songTransposeService.transpose(song, Key.H, Key.Bb);
    Assert.assertEquals ("Bb", linePart.getChord());
  }

  @Test
  public void transposeDownBorder () {
    SongBuilder songbuilder = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "Bb");
    Song song = songbuilder.get();
    LinePart linePart = song.getFirstPart().getFirstLine().getFirstLinePart();
    Assert.assertEquals ("Bb", linePart.getChord());
    songTransposeService.transpose(song, Key.Bb, Key.H);
    Assert.assertEquals ("H", linePart.getChord());

  }

  @Test  //C->G     //A->D
  public void recalculateOrigin () {
    Song song = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "A", "F").get();
    song.setOriginalKey("C");
    song.setCurrentKey("G");
    songTransposeService.recalculateOrigin(song);
    Assert.assertEquals ("D", song.getFirstPart().getFirstLine().getFirstLinePart().getOriginalChord());

  }

  @Test  //C->G     //A->D
  public void recalculateOriginCrossToBb () {
    Song song = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "G").get();
    song.setOriginalKey("Db");
    song.setCurrentKey("G");
    songTransposeService.recalculateOrigin(song);
    Assert.assertEquals ("Db", song.getFirstPart().getFirstLine().getFirstLinePart().getOriginalChord());
  }

  @Test   //C->G     //F->Bb
  public void recalculateCurrent () {
    Song song = new SongBuilder().withPart(SongPartType.VERS).withLine().withLinePart("This is", "Bb", "F").get();
    song.setOriginalKey("C");
    song.setCurrentKey("G");
    songTransposeService.recalculateCurrent(song);
    Assert.assertEquals ("C", song.getFirstPart().getFirstLine().getFirstLinePart().getChord());
  }

  @Test
  public void recalculateCurrentError () {
    Song song = new SongBuilder().withPart(SongPartType.INTRO).withLine().withLinePart("", "H5", "H5").get();
    song.setCurrentKey("A");
    song.setOriginalKey("H");
    songTransposeService.recalculateCurrent(song);
    Assert.assertEquals ("A5", song.getFirstPart().getFirstLine().getFirstLinePart().getChord());

  }




}
