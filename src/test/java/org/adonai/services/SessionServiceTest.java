package org.adonai.services;

import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.junit.Assert;
import org.junit.Test;

public class SessionServiceTest {

  private SessionService sessionService = new SessionService();

  @Test
  public void addSong () {
    Song song = new Song();
    Session session = new Session();

    Assert.assertEquals("Session not empty before", 0, session.getSongs().size());
    sessionService.addSong(session, song);
    Assert.assertEquals("Song not added", 1, session.getSongs().size());
  }

  @Test
  public void newSessionAndGetSong () {
    Configuration configuration = new Configuration();
    Song song1 = new Song();
    song1.setId(42);
    song1.setTitle("Something");
    SongBook songBook = new SongBook();
    songBook.getSongs().add(song1);
    configuration.getSongBooks().add(songBook);
    Assert.assertEquals ("Invalid number of sesssions before", 0, configuration.getSessions().size());
    Session newSession = sessionService.newSession(configuration);
    newSession.getSongs().add(42);
    Assert.assertEquals ("Invalid number of sesssions before", 1, configuration.getSessions().size());
    Assert.assertNotNull("Name must be set in new session", configuration.getSessions().get(0).getName());
    Assert.assertEquals("Invalid song found", "Something", sessionService.getSongs(newSession, songBook).get(0).getTitle());


  }


}
