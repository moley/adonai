package org.adonai.services;

import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;

public class AddSongService {

  public Song createSong (String title, final boolean withDefaultPart) {
    Song newSong = new Song();
    newSong.setTitle(title != null ? title.toUpperCase(): "NEW SONG");
    if (withDefaultPart) {
      SongPart songPart = new SongPart();
      songPart.setSongPartType(SongPartType.VERS);
      Line newLine = new Line();
      newLine.getLineParts().add(new LinePart());
      songPart.getLines().add(newLine);
      SongStructItem firstSongStructItem = new SongStructItem();
      firstSongStructItem.setPartId(songPart.getId());
      newSong.getStructItems().add(firstSongStructItem);
      newSong.getSongParts().add(songPart);
    }
    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(newSong);
    return newSong;
  }

  public Integer getNextNumber (final SongBook songBook) {
    int lastNumber = 0;
    if (! songBook.getSongs().isEmpty())
      lastNumber = songBook.getSongs().get(songBook.getSongs().size() - 1).getId();

    int newNumber = lastNumber + 1;

    return newNumber;
  }

  public Song addSong (final Song newSong, final SongBook songBook) {
    if (newSong.getSongParts().isEmpty())
      throw new IllegalStateException("You cannot add a song with no songparts");

    newSong.setId(getNextNumber(songBook));

    songBook.getSongs().add(newSong);

    return newSong;
  }

}
