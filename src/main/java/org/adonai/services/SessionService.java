package org.adonai.services;

import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;

import java.util.ArrayList;
import java.util.List;

public class SessionService {

  public void addSong (Session session, Song song) {
    session.getSongs().add(song.getId());
  }

  public Session newSession (Configuration configuration) {
    Session session = new Session();
    session.setName("New session");
    configuration.getSessions().add(session);
    return session;
  }

  public List<Song> getSongs (final Session session, final SongBook songBook) {
    List<Song> sessionSongs = new ArrayList<Song>();
    for (Integer nextSong : session.getSongs()) {
      Song foundSong = songBook.findSong(nextSong);
      sessionSongs.add(foundSong);
    }
    return sessionSongs;
  }
}
