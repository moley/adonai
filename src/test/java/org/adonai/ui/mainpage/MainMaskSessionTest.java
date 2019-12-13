package org.adonai.ui.mainpage;

import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.model.ConfigurationService;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.TestUtil;
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

  @Test
  public void addNewSongToSession () throws InterruptedException {
    mainMaskPage.stepToSession(0);
    mainMaskPage.add();

    //TODO Asserts
  }

  @Test
  public void removeSongFromSession () {
    mainMaskPage.stepToSession(0);
    mainMaskPage.remove();
  }

  @Test
  public void clickOnListStepsToSongDetails () throws InterruptedException {
    mainMaskPage.stepToSession(0);
    Thread.sleep(2000);
    Assert.assertEquals ("SESSION1", mainMaskPage.getCurrentContentText());
    Assert.assertEquals ("session", mainMaskPage.getCurrentTypeText());
    doubleClickOn(mainMaskPage.getLviSession(), MouseButton.PRIMARY);
    Thread.sleep(2000);
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
