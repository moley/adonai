package org.adonai.testdata;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.adonai.AdonaiProperties;
import org.adonai.ApplicationEnvironment;
import org.adonai.export.ExportConfiguration;
import org.adonai.model.Configuration;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Model;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.TenantModel;
import org.adonai.model.User;
import org.adonai.services.AddSongService;
import org.adonai.services.ModelService;
import org.adonai.services.SessionService;
import org.adonai.fx.Consts;
import org.adonai.uitests.TestUtil;
import org.apache.commons.io.FileUtils;
import org.pf4j.DefaultPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDataCreator {

  protected static final Logger LOGGER = LoggerFactory.getLogger(TestDataCreator.class);

  public static void main(String[] args) throws IOException {
    try {
      new TestDataCreator().createTestData(false);
    } catch (Exception e) {
      LOGGER.error(e.getLocalizedMessage(), e);
      System.exit(1);
    }
  }

  public Configuration createTestData(final boolean preview) throws IOException {
    LOGGER.info("Create testdata");

    Consts.setAdonaiHome(TestUtil.getDefaultTestDataPath());
    File testDataPath = Consts.getAdonaiHome();

    AddSongService addSongService = new AddSongService();
    SessionService sessionService = new SessionService();

    LOGGER.info("Clean testdir " + testDataPath.getAbsolutePath());
    FileUtils.deleteDirectory(testDataPath);


    Model model = new Model();

    AdonaiProperties adonaiProperties = new AdonaiProperties();
    adonaiProperties.setCurrentTenant("tenant1");
    adonaiProperties.save();

    for (String nextTenant : Arrays.asList("tenant1", "tenant2")) {

      SongBook songBook = new SongBook();

      ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(new DefaultPluginManager());
      ModelService modelService = new ModelService(applicationEnvironment);
      modelService.addTenant(model, nextTenant);
      TenantModel tenantModel = model.getTenantModel(nextTenant);
      tenantModel.load();
      Configuration configuration = tenantModel.get();
      configuration.getSongBooks().add(songBook);

      User user1 = new User();
      user1.setMail("user1@gmail.com");
      user1.setUsername("user1");

      User user2 = new User();
      user2.setMail("user2@gmail.com");
      user2.setUsername("user2");

      Song song1 = createSong("Song1_" + nextTenant, false, null);
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

      SongPart songPart2 = new SongPart();
      songPart2.setSongPartType(SongPartType.REFRAIN);

      Line line2 = new Line();
      LinePart linePart21 = new LinePart();
      linePart21.setText("This is a ");
      linePart21.setChord("C");
      LinePart linePart22 = new LinePart();
      linePart22.setText("very nice song");
      linePart22.setChord("F");
      line2.getLineParts().addAll(Arrays.asList(linePart21, linePart22));
      songPart2.getLines().add(line2);

      song1.getSongParts().addAll(Arrays.asList(songPart, songPart2));
      song1.setPreset("preset");
      song1.setLeadVoice(user1);
      Song song2 = createSong("Song2_" + nextTenant, true, 180);
      Song song3 = createSong("Song3_" + nextTenant, true, 90);
      Song song4 = createSong("Song4_" + nextTenant, true, 120);
      addSongService.addSong(song1, songBook);
      addSongService.addSong(song2, songBook);
      addSongService.addSong(song3, songBook);
      addSongService.addSong(song4, songBook);

      Session session1 = new Session();
      session1.setName("Session1_" + nextTenant);
      sessionService.addSong(session1, song1);
      sessionService.addSong(session1, song2);
      configuration.getSessions().add(session1);

      Session session2 = new Session();
      session2.setName("Session2_" + nextTenant);
      sessionService.addSong(session2, song1);
      sessionService.addSong(session2, song2);
      sessionService.addSong(session2, song3);
      sessionService.addSong(session2, song4);
      configuration.getSessions().add(session2);

      configuration.getUsers().addAll(Arrays.asList(user1, user2));

      File exportDir = new File(testDataPath, "export");
      File extensionPath = new File(testDataPath, "additionals");
      exportDir.mkdirs();
      extensionPath.mkdirs();

      configuration.setExportPath(exportDir.getAbsolutePath());
      configuration.getExportPathAsFile().mkdirs();
      configuration.getExtensionPaths().add(extensionPath.getAbsolutePath());

      new File(extensionPath, "SomeMp3.mp3").createNewFile();
      new File(extensionPath, "AnotherMp3.mp3").createNewFile();

      model.save();

      for (ExportConfiguration nextConfiguration1 : configuration.getExportConfigurations()) {
        nextConfiguration1.setOpenPreview(preview);
      }

      model.save();
    }

    LOGGER.info("Model: " + model.toString());

    return model.getCurrentTenantModel().get();

  }

  private static Song createSong(final String title, final boolean withDefaultPart, final Integer bpm) {
    AddSongService addSongService = new AddSongService();
    Song newSong = addSongService.createSong(title, withDefaultPart);
    newSong.setSpeed(bpm);
    return newSong;
  }
}
