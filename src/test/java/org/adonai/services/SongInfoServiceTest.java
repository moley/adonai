package org.adonai.services;

import org.adonai.SongTestData;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartDescriptorStrategy;
import org.junit.Assert;
import org.junit.Test;

public class SongInfoServiceTest {

  private SongInfoService songInfoService = new SongInfoService();

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
