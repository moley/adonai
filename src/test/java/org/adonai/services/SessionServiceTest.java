package org.adonai.services;

import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.SongBuilder;
import org.junit.Assert;
import org.junit.Test;

public class SessionServiceTest {

  private SessionService sessionService = new SessionService();

  @Test
  public void moveUpFirst () {
    Song song1 = SongBuilder.instance().get();
    Song song2 = SongBuilder.instance().get();

    Session session = new Session();
    session.getSongs().add(song1.getId());
    session.getSongs().add(song2.getId());

    sessionService.moveUp(session, song2);
    Assert.assertEquals (song2.getId(), session.getSongs().get(0));
    Assert.assertEquals (song1.getId(), session.getSongs().get(1));

  }

  @Test
  public void moveUpNotFirst () {
    Song song1 = SongBuilder.instance().get();
    Song song2 = SongBuilder.instance().get();

    Session session = new Session();
    session.getSongs().add(song1.getId());
    session.getSongs().add(song2.getId());

    sessionService.moveUp(session, song1);
    Assert.assertEquals (song1.getId(), session.getSongs().get(0));
    Assert.assertEquals (song2.getId(), session.getSongs().get(1));

  }

  @Test
  public void moveDownLast () {
    Song song1 = SongBuilder.instance().get();
    Song song2 = SongBuilder.instance().get();

    Session session = new Session();
    session.getSongs().add(song1.getId());
    session.getSongs().add(song2.getId());

    sessionService.moveDown(session, song2);
    Assert.assertEquals (song1.getId(), session.getSongs().get(0));
    Assert.assertEquals (song2.getId(), session.getSongs().get(1));
  }

  @Test
  public void moveDownNotLast () {
    Song song1 = SongBuilder.instance().get();
    Song song2 = SongBuilder.instance().get();

    Session session = new Session();
    session.getSongs().add(song1.getId());
    session.getSongs().add(song2.getId());

    sessionService.moveDown(session, song1);
    Assert.assertEquals (song2.getId(), session.getSongs().get(0));
    Assert.assertEquals (song1.getId(), session.getSongs().get(1));
  }

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
