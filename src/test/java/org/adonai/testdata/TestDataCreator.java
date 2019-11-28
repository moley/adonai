package org.adonai.testdata;

import org.adonai.AbstractAdonaiUiTest;
import org.adonai.export.ExportConfiguration;
import org.adonai.model.*;
import org.adonai.services.AddSongService;
import org.adonai.services.SessionService;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class TestDataCreator {

  protected static final Logger LOGGER = Logger.getLogger(TestDataCreator.class.getName());


  public void createTestData (final boolean preview) throws IOException {
    LOGGER.info("Create testdata");

    AddSongService addSongService = new AddSongService();
    SessionService sessionService = new SessionService();

    File testData = new File("build/testdata");
    FileUtils.deleteDirectory(testData);

    System.setProperty(Consts.ADONAI_HOME_PROP, testData.getAbsoluteFile().getAbsolutePath());
    ConfigurationService configurationService = new ConfigurationService();

    SongBook songBook = new SongBook();

    Configuration configuration = configurationService.get();
    configuration.getSongBooks().add(songBook);

    User user1 = new User();
    user1.setMail("user1@gmail.com");
    user1.setUsername("user1");

    User user2 = new User();
    user2.setMail("user2@gmail.com");
    user2.setUsername("user2");

    Song song1 = createSong("Song1");
    song1.setCurrentKey("C");
    song1.setOriginalKey("G");

    SongPart songPart = new SongPart();
    songPart.setSongPartType(SongPartType.VERS);

    Line line = new Line();
    LinePart linePart1 = new LinePart();
    linePart1.setText("This is a ");
    linePart1.setChord("C");
    LinePart linePart2 = new LinePart();

    linePart2.setText("very nice song");
    linePart2.setChord("F");
    line.getLineParts().addAll(Arrays.asList(linePart1, linePart2));
    songPart.getLines().add(line);

    song1.getSongParts().add(songPart);
    song1.setPreset("preset");
    song1.setLeadVoice(user1);
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




    configuration.getUsers().addAll(Arrays.asList(user1, user2));



    File exportDir = new File (testData, "export");
    File extensionPath = new File (testData, "additionals");
    exportDir.mkdirs();
    extensionPath.mkdirs();


    configuration.setExportPath(exportDir.getAbsolutePath());
    configuration.getExportPathAsFile().mkdirs();
    configuration.getExtensionPaths().add(extensionPath.getAbsolutePath());

    new File (extensionPath, "SomeMp3.mp3").createNewFile();
    new File (extensionPath, "AnotherMp3.mp3").createNewFile();

    configurationService.set(configuration);

    for (ExportConfiguration nextConfiguration1 : configuration.getExportConfigurations()) {
      nextConfiguration1.setOpenPreview(preview);
    }

    configurationService.set(configuration);
    configurationService.close();

  }

  private static Song createSong (final String title) {
    Song song = new Song();
    song.setTitle(title);
    return song;
  }
}
