package org.adonai.ui;

import org.adonai.model.*;

import java.io.File;

public class CreateTestDataUiTests {

  CreateTestDataUiTests () {
    System.setProperty("config", new File("src/test/resources/uitests").getAbsolutePath());
    ConfigurationService configurationService = new ConfigurationService();
    Configuration configuration = new Configuration();

    SongBook songBook = new SongBook();
    configuration.getSongBooks().add(songBook);

    songBook.getSongs().add(createSong1());
    songBook.getSongs().add(createSong2());

    Session session = new Session();
    session.setName("Demosession");
    session.getSongs().add(new Integer(2));
    session.getSongs().add(new Integer(1));
    configuration.getSessions().add(session);

    configurationService.set(configuration);
  }

  public static void main (final String [] args) {
    new CreateTestDataUiTests();


  }

  private Song createSong1 () {
    return SongBuilder.instance().withTitle("Song 1").withId("1").withPart(SongPartType.VERS).withLine().withLinePart("This is a demo", null).get();

  }

  private Song createSong2 () {
    return SongBuilder.instance().withTitle("Song 2").withId("2").withPart(SongPartType.VERS).withLine().withLinePart("This is a demo", "D").get();
  }
}
