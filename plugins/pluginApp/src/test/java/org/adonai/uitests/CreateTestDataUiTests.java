package org.adonai.uitests;

import java.io.File;
import org.adonai.AdonaiProperties;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.SongBuilder;
import org.adonai.model.SongPartType;

public class CreateTestDataUiTests {

  private AdonaiProperties adonaiProperties = new AdonaiProperties();


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

    configurationService.setConfigFile(new File ("src/test/resources/uitests/config.xml"));
    configurationService.set(adonaiProperties.getCurrentTenant(), configuration);
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
