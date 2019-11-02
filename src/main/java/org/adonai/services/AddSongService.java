package org.adonai.services;

import org.adonai.model.*;

public class AddSongService {

  public Song createSong (String title) {
    Song newSong = new Song();
    newSong.setTitle(title != null ? title.toUpperCase(): "NEW SONG");
    SongPart songPart = new SongPart();
    songPart.setSongPartType(SongPartType.INTRO);
    Line newLine = new Line();
    newLine.getLineParts().add(new LinePart());
    songPart.getLines().add(newLine);
    newSong.getSongParts().add(songPart);
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

    newSong.setId(getNextNumber(songBook));

    SongPart newSongpart = new SongPart();
    newSong.getSongParts().add(newSongpart);

    Line line = new Line(" ");
    newSongpart.getLines().add(line);

    songBook.getSongs().add(newSong);

    return newSong;
  }

}
