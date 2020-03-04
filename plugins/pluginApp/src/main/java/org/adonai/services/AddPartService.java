package org.adonai.services;

import java.util.UUID;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongStructItem;

public class AddPartService {


  /**
   * add a complete song part to a song
   * @param songCursor  current position
   * @return song struct item to focus afterwards
   */
  public SongStructItem addPartBefore (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongStructItem currentSongPart = songCursor.getCurrentSongStructItem();
    int index = song.getIndex(currentSongPart);
    SongStructItem newStructItem = new SongStructItem();
    SongPart newSongpart = new SongPart();
    newSongpart.setId(UUID.randomUUID().toString());
    newSongpart.newLine();
    newStructItem.setPartId(newSongpart.getId());
    song.getStructItems().add(index , newStructItem);
    song.getSongParts().add(newSongpart);

    return newStructItem;
  }

  /**
   * add a complete song part to a song
   * @param songCursor  current position
   * @return song struct item to focus afterwards
   */
  public SongStructItem addPartAfter (final SongCursor songCursor) {
    Song song = songCursor.getCurrentSong();
    SongStructItem currentSongPart = songCursor.getCurrentSongStructItem();
    int index = song.getIndex(currentSongPart);
    SongStructItem newStructItem = new SongStructItem();
    SongPart newSongpart = new SongPart();
    newSongpart.setId(UUID.randomUUID().toString());
    newSongpart.newLine();
    newStructItem.setPartId(newSongpart.getId());
    song.getStructItems().add(index + 1, newStructItem);
    song.getSongParts().add(newSongpart);

    return newStructItem;
  }

  /**
   * adds a complete song part to the end of the song
   * @param song song
   * @return  new structitem
   */
  public SongStructItem addPart (final Song song) {
    SongStructItem newStructItem = new SongStructItem();
    SongPart newSongpart = new SongPart();
    newSongpart.setId(UUID.randomUUID().toString());
    newSongpart.newLine();
    newStructItem.setPartId(newSongpart.getId());
    song.getStructItems().add(newStructItem);
    song.getSongParts().add(newSongpart);
    return newStructItem;
  }
}
