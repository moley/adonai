package org.adonai.ui.mainpage;

import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.TestUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainMaskSessionsTest extends AbstractAdonaiUiTest {

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
    mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();

  }
  @Test
  public void addNewSongToSession () {
    mainMaskPage.stepToSessions();
    int sizeSessionsBefore = mainMaskPage.getSessions().size();
    mainMaskPage.add();
    int sizeSessionsAfter = mainMaskPage.getSessions().size();
    Assert.assertEquals("Number of sessions did not increase", sizeSessionsBefore + 1, sizeSessionsAfter);
  }

  @Test
  public void removeSongFromSession () {
    mainMaskPage.stepToSessions();
    int sizeSessionsBefore = mainMaskPage.getSessions().size();
    mainMaskPage.remove();
    int sizeSessionsAfter = mainMaskPage.getSessions().size();
    Assert.assertEquals("Number of sessions did not decrease", sizeSessionsBefore - 1, sizeSessionsAfter);
  }


  @Test
  public void clickOnListStepsToSessionDetails () {
    mainMaskPage.stepToSessions();
    Assert.assertEquals ("sessions", mainMaskPage.getCurrentTypeText());
    Assert.assertEquals ("", mainMaskPage.getCurrentContentText());

    doubleClickOn(mainMaskPage.getLviSessions(), MouseButton.PRIMARY);
    Assert.assertEquals ("SESSION1", mainMaskPage.getCurrentContentText());
    Assert.assertEquals ("session", mainMaskPage.getCurrentTypeText());
    Assert.assertTrue ("SessionList is not visible", mainMaskPage.getLviSession().isVisible());
  }



  @Test
  public void exportSongsInSessions () {
    mainMaskPage.stepToSessions();
    Assert.assertEquals ("Session1", mainMaskPage.getSelectedSession());
    mainMaskPage.exportFileExists("Session1/Session1_Chords.pdf", false);
    mainMaskPage.export();
    mainMaskPage.exportFileExists("Session1/Session1_Chords.pdf", true);
  }
}
