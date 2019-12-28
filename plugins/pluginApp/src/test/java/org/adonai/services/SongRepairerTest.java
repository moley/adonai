package org.adonai.services;

import java.util.List;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPart;
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
    Assert.assertEquals (1, song.getFirstSongPart().getLines().size());
  }

  @Test
  public void synchronizeChordsNoKey () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    song.setOriginalKey("D");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getSongParts().get(0).getLines().get(0).getLineParts().get(0);
    Assert.assertNull ("Chord must be not changed when currentKey is null", linePart.getOriginalChord());

  }

  @Test
  public void synchronizeChordsNoOriginKey () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    song.setCurrentKey("D");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getSongParts().get(0).getLines().get(0).getLineParts().get(0);
    Assert.assertNull ("Chord must be not changed when originalKey is null", linePart.getOriginalChord());

  }

  @Test
  public void synchronizeChordsChordToOriginChord () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    song.setCurrentKey("D");
    song.setOriginalKey("C");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getSongParts().get(0).getLines().get(0).getLineParts().get(0);
    Assert.assertEquals ("Invalid origin chord", "C", linePart.getOriginalChord());

  }

  @Test
  public void synchronizeChordsOriginChordToChord () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", null,  "C").get();
    song.setCurrentKey("D");
    song.setOriginalKey("C");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getSongParts().get(0).getLines().get(0).getLineParts().get(0);
    Assert.assertEquals ("Invalid origin chord", "D", linePart.getChord());

  }

  @Test
  public void example1 () {
    Song song = new Song();
    song.setCurrentKey("D");
    song.setOriginalKey("A");

    LinePart linePart = new LinePart("", "D", null);
    LinePart linePar2 = new LinePart("", "A", null);
    LinePart linePar3 = new LinePart("", "G", null);
    Line line = new Line();
    line.getLineParts().addAll(List.of(linePart, linePar2, linePar3));
    SongPart songPart = new SongPart();

    songPart.getLines().add(line);

    song.getSongParts().add(songPart);

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);

    Assert.assertEquals("D", linePart.getChord());
    Assert.assertEquals("A", linePart.getOriginalChord());

    Assert.assertEquals("A", linePar2.getChord());
    Assert.assertEquals("E", linePar2.getOriginalChord());

    Assert.assertEquals("G", linePar3.getChord());
    Assert.assertEquals("D", linePar3.getOriginalChord());
  }
}
