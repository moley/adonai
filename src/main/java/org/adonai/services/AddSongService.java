package org.adonai.services;

import org.adonai.model.*;

public class AddSongService {

  public Song addSong (final SongBook songBook) {
    Song newSong = new Song();
    newSong.setTitle("NEW SONG");
    return addSong(newSong, songBook);
  }

  public Song addSong (final Song newSong, final SongBook songBook) {
    int lastNumber = 0;
    if (! songBook.getSongs().isEmpty())
      lastNumber = songBook.getSongs().get(songBook.getSongs().size() - 1).getId();

    int newNumber = lastNumber + 1;
    newSong.setId(newNumber);

    SongPart newSongpart = new SongPart();
    newSong.getSongParts().add(newSongpart);

    Line line = new Line(" ");
    newSongpart.getLines().add(line);

    songBook.getSongs().add(newSong);

    return newSong;
  }

}
