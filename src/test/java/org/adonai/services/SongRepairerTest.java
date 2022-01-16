package org.adonai.services;

import java.util.HashMap;
import java.util.List;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.junit.Assert;
import org.junit.Test;

public class SongRepairerTest {


  @Test
  public void getAggregatedNumberOfParts () {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withPartReference("1");
    builder = builder.withPart(SongPartType.VERS).withRemarks("remarks").withLine().withPartId("1").withLinePart("First verse", "C");
    builder = builder.withPart(SongPartType.REFRAIN).withLine().withPartId("2").withLinePart("Refrain", "C");
    builder = builder.withPart(SongPartType.VERS).withLine().withPartId("3").withQuantity(3).withRemarks("Cool").withLinePart("Second verse", "C");
    Song song = builder.get();
    //2x VERS, 1x REFRAIN

    SongRepairer songRepairer = new SongRepairer();
    HashMap<SongPartType, Integer> aggregatedNumberOfTypes = songRepairer.getAggregatedNumberOfTypes(song);
    Assert.assertEquals (Integer.valueOf("2"), aggregatedNumberOfTypes.get(SongPartType.VERS));
    Assert.assertEquals (Integer.valueOf("1"), aggregatedNumberOfTypes.get(SongPartType.REFRAIN));

  }
  @Test
  public void orderOptions () {
    SongBuilder builder = SongBuilder.instance();
    builder = builder.withPartReference("1");
    builder = builder.withPart(SongPartType.VERS).withRemarks("remarks").withLine().withPartId("1").withLinePart("First verse", "C");
    builder = builder.withPart(SongPartType.REFRAIN).withLine().withPartId("2").withLinePart("Refrain", "C");
    builder = builder.withPart(SongPartType.VERS).withLine().withPartId("3").withQuantity(3).withRemarks("Cool").withLinePart("Second verse", "C");
    Song song = builder.get();

    System.out.println ("Song: " + song.getStructItems());
    System.out.println ("Song: " + song.getSongParts());

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);

    Assert.assertEquals ("VERS 1", song.getStructItems().get(0).getText());
    Assert.assertTrue (song.getStructItems().get(0).isFirstOccurence());
    Assert.assertEquals ("VERS 1", song.getStructItems().get(1).getText());
    Assert.assertFalse (song.getStructItems().get(1).isFirstOccurence());
    Assert.assertEquals ("REFRAIN", song.getStructItems().get(2).getText());
    Assert.assertEquals ("VERS 2", song.getStructItems().get(3).getText());
  }


  @Test
  public void removeEmptyLine () {
    Song song = SongBuilder.instance().disableRepairer().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    Assert.assertEquals (2, song.getFirstPart().getLines().size());
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    Assert.assertEquals (1, song.getFirstPart().getLines().size());
  }

  @Test
  public void synchronizeChordsNoKey () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    song.setOriginalKey("D");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getFirstPart().getLines().get(0).getLineParts().get(0);
    Assert.assertNull ("Chord must be not changed when currentKey is null", linePart.getOriginalChord());

  }

  @Test
  public void synchronizeChordsNoOriginKey () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    song.setCurrentKey("D");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getFirstPart().getLines().get(0).getLineParts().get(0);
    Assert.assertNull ("Chord must be not changed when originalKey is null", linePart.getOriginalChord());

  }

  @Test
  public void synchronizeChordsChordToOriginChord () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", "D").get();
    song.setCurrentKey("D");
    song.setOriginalKey("C");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getFirstPart().getLines().get(0).getLineParts().get(0);
    Assert.assertEquals ("Invalid origin chord", "C", linePart.getOriginalChord());

  }

  @Test
  public void synchronizeChordsOriginChordToChord () {
    Song song = SongBuilder.instance().withPart(SongPartType.VERS).withLine().withLine().withLinePart("Hallo", null,  "C").get();
    song.setCurrentKey("D");
    song.setOriginalKey("C");
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);
    LinePart linePart = song.getFirstPart().getLines().get(0).getLineParts().get(0);
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
    SongStructItem songStructItem = new SongStructItem();
    songStructItem.setPartId(songPart.getId());
    song.getStructItems().add(songStructItem);

    song.getSongParts().add(songPart);

    SongRepairer songRepairer = new SongRepairer();
    System.out.println (song.getSongParts().size());
    songRepairer.repairSong(song);

    System.out.println (song.getSongParts().size());

    Assert.assertEquals("D", linePart.getChord());
    Assert.assertEquals("A", linePart.getOriginalChord());

    Assert.assertEquals("A", linePar2.getChord());
    Assert.assertEquals("E", linePar2.getOriginalChord());

    Assert.assertEquals("G", linePar3.getChord());
    Assert.assertEquals("D", linePar3.getOriginalChord());
  }
}
