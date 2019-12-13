package org.adonai.ui.mainpage;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.TestUtil;
import org.adonai.ui.editor.SongEditorPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMaskSongTest extends AbstractAdonaiUiTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(MainMaskSongTest.class);


  private MainMaskPage mainMaskPage;

  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    LOGGER.info("start called with stage " + System.identityHashCode(stage));
    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData(TestUtil.getDefaultTestDataPath(), false);
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


}
