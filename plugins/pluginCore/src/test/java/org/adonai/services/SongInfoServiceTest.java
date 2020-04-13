package org.adonai.services;

import java.util.Collection;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartDescriptorStrategy;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.junit.Assert;
import org.junit.Test;

public class SongInfoServiceTest {

  private SongInfoService songInfoService = new SongInfoService();

  @Test
  public void getRealWithoutCurrent () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    System.out.println ("Parts: " + song.getSongParts());
    System.out.println ("StructItems: " + song.getStructItems());
    SongStructItem currentPart = song.getFirstStructItem();
    Collection<SongStructItem> realSongPartsWithoutCurrent = songInfoService.getRealSongPartsWithoutCurrent(song, currentPart);
    Assert.assertFalse ("current song part must not be contained", realSongPartsWithoutCurrent.contains(currentPart));
    Assert.assertEquals ("Number of parts invalid", 1, realSongPartsWithoutCurrent.size());
  }

  @Test
  public void getPreview () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    String preview = songInfoService.getPreview(song, song.getFirstStructItem());
    Assert.assertEquals ("Invalid preview", "VERS (This is the first te...)", preview);
  }

  @Test
  public void getStructureRealLong () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongStructItem songStructItem = song.getFirstStructItem();
    Assert.assertEquals ("VERS", songInfoService.getStructure(song, songStructItem, SongPartDescriptorStrategy.LONG));
  }

  @Test
  public void getStructureRealShort () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongStructItem songStructItem = song.getFirstStructItem();
    Assert.assertEquals ("V", songInfoService.getStructure(song, songStructItem, SongPartDescriptorStrategy.SHORT));
  }

  @Test
  public void getStructureRealShortWithQuantity () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongStructItem songStructItem = song.getFirstStructItem();
    songStructItem.setQuantity("3");
    Assert.assertEquals ("V 3x", songInfoService.getStructure(song, songStructItem, SongPartDescriptorStrategy.SHORT));
  }

  @Test
  public void getStructureRefLongWithQuantity () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongStructItem songStructItem = song.getStructItems().get(2);
    songStructItem.setQuantity("2x");
    Assert.assertEquals ("VERS 2x", songInfoService.getStructure(song, songStructItem, SongPartDescriptorStrategy.LONG));
  }

  @Test
  public void getStructureRefLong () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongStructItem songStructItem = song.getStructItems().get(2);
    Assert.assertEquals ("VERS", songInfoService.getStructure(song, songStructItem, SongPartDescriptorStrategy.LONG));
  }

  @Test
  public void getStructureRefShort () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongStructItem songStructItem = song.getStructItems().get(2);
    Assert.assertEquals ("V", songInfoService.getStructure(song, songStructItem, SongPartDescriptorStrategy.SHORT));
  }

  @Test(expected = IllegalStateException.class)
  public void getStructureNoType () {
    Song song = SongBuilder.instance().disableRepairer().withPart(SongPartType.BRIDGE).get();
    song.getSongParts().get(0).setSongPartType(null);
    Assert.assertEquals ("", songInfoService.getStructure(song, song.getFirstStructItem(), SongPartDescriptorStrategy.SHORT));
  }
}
