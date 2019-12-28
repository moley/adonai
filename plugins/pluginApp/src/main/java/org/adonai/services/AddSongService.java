package org.adonai.services;

import org.adonai.model.*;

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
      newSong.getSongParts().add(songPart);
    }
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
