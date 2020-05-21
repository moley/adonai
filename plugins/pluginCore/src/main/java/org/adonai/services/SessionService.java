package org.adonai.services;

import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;

import java.util.ArrayList;
import java.util.List;

public class SessionService {

  public void addSongAfter (Session session, Song afterSong, Song newSong) {

  }

  public void addSong (Session session, Song song) {
    session.getSongs().add(song.getId());
  }
  public void removeSong (Session session, Song song) {
    Integer id = song.getId();
    if (!session.getSongs().remove(id)) {
      throw new IllegalStateException("ID " + id + " could not be removed from session " + session);
    }
  }

  public Session newSession (Configuration configuration) {
    Session session = new Session();
    session.setName("New session");
    configuration.getSessions().add(session);
    return session;
  }

  public boolean removeSession (final Configuration configuration, final Session session) {
    return configuration.getSessions().remove(session);
  }

  public List<Song> getSongs (final Session session, final SongBook songBook) {
    List<Song> sessionSongs = new ArrayList<Song>();
    for (Integer nextSong : session.getSongs()) {
      Song foundSong = songBook.findSong(nextSong);
      if (foundSong == null)
        throw new IllegalStateException("Song with id " + nextSong + " not found");
      sessionSongs.add(foundSong);
    }
    return sessionSongs;
  }
}
