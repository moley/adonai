package org.adonai.services;

import java.util.UUID;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;

public class AddPartService {

  private SongRepairer songRepairer = new SongRepairer();


  /**
   * add a complete song part to a song
   * @param songCursor  current position
   * @param copiedPart    a reference to a songpart if an existing part should be added, can be <code>null</code>
   * @param songPartType  a type of the songpart, if a new songpart should be added, can be <code>null</code>
   * @return song struct item to focus afterwards
   */
  public SongStructItem addPartBefore (final SongCursor songCursor, SongPart copiedPart, SongPartType songPartType) {
    Song song = songCursor.getCurrentSong();
      SongStructItem currentSongStructItem = songCursor.getCurrentSongStructItem();
    int index = song.getIndex(currentSongStructItem);
    SongStructItem newStructItem = new SongStructItem();

    SongPart newSongpart;
    if (copiedPart != null) {
      newSongpart = copiedPart;
    }
    else {
      newSongpart = new SongPart();
      newSongpart.setSongPartType(songPartType);
      newSongpart.setId(UUID.randomUUID().toString());
      newSongpart.newLine();
      song.getSongParts().add(newSongpart);
    }

    newStructItem.setPartId(newSongpart.getId());
    song.getStructItems().add(index , newStructItem);

    songRepairer.repairSong(songCursor.getCurrentSong());

    return newStructItem;
  }

  /**
   * add a complete song part to a song
   * @param songCursor    current position
   * @param copiedPart    a reference to a songpart if an existing part should be added, can be <code>null</code>
   * @param songPartType  a type of the songpart, if a new songpart should be added, can be <code>null</code>
   * @return song struct item to focus afterwards
   */
  public SongStructItem addPartAfter (final SongCursor songCursor, SongPart copiedPart, SongPartType songPartType) {
    Song song = songCursor.getCurrentSong();
    SongStructItem currentSongStructItem = songCursor.getCurrentSongStructItem();
    int index = song.getIndex(currentSongStructItem);
    SongStructItem newStructItem = new SongStructItem();

    SongPart newSongpart;
    if (copiedPart != null) {
      newSongpart = copiedPart;
    }
    else {
      newSongpart = new SongPart();
      newSongpart.setSongPartType(songPartType);
      newSongpart.setId(UUID.randomUUID().toString());
      newSongpart.newLine();
      song.getSongParts().add(newSongpart);
    }

    newStructItem.setPartId(newSongpart.getId());
    song.getStructItems().add(index + 1, newStructItem);

    songRepairer.repairSong(songCursor.getCurrentSong());

    return newStructItem;
  }

}
