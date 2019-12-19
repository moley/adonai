package org.adonai.ui;

import java.io.File;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.pages.SongEditorPage;
import org.adonai.ui.pages.MainMaskPage;
import org.adonai.ui.pages.SelectAdditionalPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMaskSongTest extends AbstractAdonaiUiTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(MainMaskSongTest.class);


  private MainMaskPage mainMaskPage;

  private Configuration configuration;

  private TestDataCreator testDataCreator = new TestDataCreator();

  private File testDataPath = TestUtil.getDefaultTestDataPath();


  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    LOGGER.info("start called with stage " + System.identityHashCode(stage));
    configuration = testDataCreator.createTestData(testDataPath, false);
    super.start(stage);
    mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();
  }

  @Override
  public void stop () {
    LOGGER.info("stop called");

  }

  @Test
  public void enterValidChord () throws InterruptedException {
    mainMaskPage.stepToSong(0);

    SongEditorPage songEditorPage = mainMaskPage.songEditorPage();
    String text = songEditorPage.getSongLinePartText(0, 0,0);
    Assert.assertEquals ("First textfield invalid content", "This is a", text.trim());
    songEditorPage.chord("G");
    Assert.assertFalse ("ChordEditor is visible after adding an valid chord", songEditorPage.isChordEditorVisible());

  }

  @Test
  public void enterInvalidChord () throws InterruptedException {
    mainMaskPage.stepToSong(0);

    SongEditorPage songEditorPage = mainMaskPage.songEditorPage();
    String text = songEditorPage.getSongLinePartText(0, 0,0);
    Assert.assertEquals ("First textfield invalid content", "This is a", text.trim());
    songEditorPage.chord("Ghh");
    Assert.assertTrue ("ChordEditor is not visible after adding an invalid chord", songEditorPage.isChordEditorVisible());

    key(KeyCode.ESCAPE);
    Assert.assertFalse ("ChordEditor is visible after escaping an invalid chord", songEditorPage.isChordEditorVisible());
  }

  @Test
  public void assignMp3 () throws InterruptedException {
    Song firstSong = configuration.getSongBooks().get(0).getSongs().get(0);
    Assert.assertEquals ("Number of additionals invalid before", 0, firstSong.getAdditionals().size());

    mainMaskPage.stepToSong(0);
    mainMaskPage.selectMp3();
    SelectAdditionalPage selectAdditionalPage = new SelectAdditionalPage(this);
    selectAdditionalPage.select("AnotherMp3");
    mainMaskPage.save ();

    Configuration configuration = testDataCreator.getConfiguration(testDataPath);

    for (Song next: configuration.getSongBooks().get(0).getSongs()) {
      LOGGER.info("Next: " + next.getAdditionals());
    }


    firstSong = configuration.getSongBooks().get(0).getSongs().get(0);
    Assert.assertTrue ("Wrong additional added", firstSong.getAdditionals().get(0).getLink().endsWith("build/testdata/additionals/AnotherMp3.mp3"));




  }


}
