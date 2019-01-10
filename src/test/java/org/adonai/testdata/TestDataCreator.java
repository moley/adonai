package org.adonai.testdata;

import org.adonai.model.*;
import org.adonai.services.AddSongService;
import org.adonai.services.SessionService;
import org.adonai.ui.Consts;

import java.io.File;

public class TestDataCreator {

  public static void main(String[] args) {

    AddSongService addSongService = new AddSongService();
    SessionService sessionService = new SessionService();

    System.setProperty(Consts.ADONAI_HOME_PROP, new File("").getAbsoluteFile().getAbsolutePath());
    ConfigurationService configurationService = new ConfigurationService();

    SongBook songBook = new SongBook();

    Configuration configuration = new Configuration();
    configuration.getSongBooks().add(songBook);

    Song song1 = createSong("Song1");
    Song song2 = createSong("Song2");
    Song song3 = createSong("Song3");
    Song song4 = createSong("Song4");
    addSongService.addSong(song1, songBook);
    addSongService.addSong(song2, songBook);
    addSongService.addSong(song3, songBook);
    addSongService.addSong(song4, songBook);

    Session session1 = new Session();
    session1.setName("Session1");
    sessionService.addSong(session1, song1);
    sessionService.addSong(session1, song2);
    configuration.getSessions().add(session1);

    Session session2 = new Session();
    session2.setName("Session2");
    sessionService.addSong(session2, song1);
    sessionService.addSong(session2, song2);
    sessionService.addSong(session2, song3);
    sessionService.addSong(session2, song4);
    configuration.getSessions().add(session2);
    configurationService.set(configuration);

  }

  private static Song createSong (final String title) {
    Song song = new Song();
    song.setTitle(title);
    return song;
  }
}
