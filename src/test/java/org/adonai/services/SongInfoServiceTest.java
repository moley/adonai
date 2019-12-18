package org.adonai.services;

import java.util.Collection;
import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartDescriptorStrategy;
import org.junit.Assert;
import org.junit.Test;

public class SongInfoServiceTest {

  private SongInfoService songInfoService = new SongInfoService();

  @Test
  public void getRealWithoutCurrent () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongPart currentPart = song.getSongParts().get(0);
    Collection<SongPart> realSongPartsWithoutCurrent = songInfoService.getRealSongPartsWithoutCurrent(song, currentPart);
    Assert.assertFalse ("current song part must not be contained", realSongPartsWithoutCurrent.contains(currentPart));
    Assert.assertEquals ("Number of parts invalid", 1, realSongPartsWithoutCurrent.size());
  }

  @Test
  public void getPreview () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    String preview = songInfoService.getPreview(song, song.getSongParts().get(0));
    Assert.assertEquals ("Invalid preview", "VERS (This is the first te...)", preview);
  }

  @Test
  public void getStructureRealLong () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongPart songPart = song.getSongParts().get(0);
    Assert.assertNull (songPart.getReferencedSongPart());
    Assert.assertEquals ("VERS", songInfoService.getStructure(song, songPart, SongPartDescriptorStrategy.LONG));
  }

  @Test
  public void getStructureRealShort () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongPart songPart = song.getSongParts().get(0);
    Assert.assertNull (songPart.getReferencedSongPart());
    Assert.assertEquals ("V", songInfoService.getStructure(song, songPart, SongPartDescriptorStrategy.SHORT));
  }

  @Test
  public void getStructureRealShortWithQuantity () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongPart songPart = song.getSongParts().get(0);
    songPart.setQuantity("3");
    Assert.assertNull (songPart.getReferencedSongPart());
    Assert.assertEquals ("V 3x", songInfoService.getStructure(song, songPart, SongPartDescriptorStrategy.SHORT));
  }

  @Test
  public void getStructureRefLongWithQuantity () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongPart songPart = song.getSongParts().get(2);
    songPart.setQuantity("2x");
    Assert.assertNotNull (songPart.getReferencedSongPart());
    Assert.assertEquals ("VERS 2x", songInfoService.getStructure(song, songPart, SongPartDescriptorStrategy.LONG));
  }

  @Test
  public void getStructureRefLong () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongPart songPart = song.getSongParts().get(2);
    Assert.assertNotNull (songPart.getReferencedSongPart());
    Assert.assertEquals ("VERS", songInfoService.getStructure(song, songPart, SongPartDescriptorStrategy.LONG));
  }

  @Test
  public void getStructureRefShort () {
    Song song = SongTestData.getSongWithTwoPartsAndOneReference();
    SongPart songPart = song.getSongParts().get(2);
    Assert.assertNotNull (songPart.getReferencedSongPart());
    Assert.assertEquals ("V", songInfoService.getStructure(song, songPart, SongPartDescriptorStrategy.SHORT));
  }

  @Test
  public void getStructureNoType () {
    Song song = new Song();
    SongPart songPart = new SongPart();
    Assert.assertEquals ("", songInfoService.getStructure(song, songPart, SongPartDescriptorStrategy.SHORT));
  }
}
