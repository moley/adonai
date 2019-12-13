package org.adonai.ui.mainpage;

import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.model.ConfigurationService;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.TestUtil;
import org.adonai.ui.selectsong.SelectSongPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainMaskSessionTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;



  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData(TestUtil.getDefaultTestDataPath(), false);
    super.start(stage);
    mainMaskPage = new MainMaskPage(this);
    mainMaskPage.openStage();
  }

  @Override
  public void stop () {
    mainMaskPage.closeStage();
  }

  @Test
  public void addNewSongToSession () throws InterruptedException {
    mainMaskPage.stepToSession(0);
    int numberOfSongs = mainMaskPage.getSongsInSession().size();
    mainMaskPage.add();

    SelectSongPage selectSongPage = new SelectSongPage(this);
    selectSongPage.search("Song4");

    int numberOfSongsAfter = mainMaskPage.getSongsInSession().size();
    LOGGER.info("Number of songs after add");
    Assert.assertEquals ("Number of songs not increased", numberOfSongs + 1, numberOfSongsAfter);
  }

  @Test
  public void removeSongFromSession () {
    mainMaskPage.stepToSession(0);
    int numberOfSongs = mainMaskPage.getSongsInSession().size();
    mainMaskPage.remove();
    int numberOfSongsAfter = mainMaskPage.getSongsInSession().size();
    Assert.assertEquals ("Number of songs not decreased", numberOfSongs -1, numberOfSongsAfter);

  }

  @Test
  public void clickOnListStepsToSongDetails () throws InterruptedException {
    mainMaskPage.stepToSession(0);
    Assert.assertEquals ("SESSION1", mainMaskPage.getCurrentContentText());
    Assert.assertEquals ("session", mainMaskPage.getCurrentTypeText());
    doubleClickOn(mainMaskPage.getLviSession(), MouseButton.PRIMARY);
    Assert.assertEquals ("1 - SONG1", mainMaskPage.getCurrentContentText());
    Assert.assertTrue ("song", mainMaskPage.getSongEditorPane().isVisible());
  }

  @Test
  public void exportSongsInCurrentSession () throws InterruptedException {
    mainMaskPage.stepToSession(0);
    mainMaskPage.exportFileExists("Session1/Session1_Chords.pdf", false);
    mainMaskPage.export();
    mainMaskPage.exportFileExists("Session1/Session1_Chords.pdf", true);
  }



}
