package org.adonai.uitests;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.uitests.pages.ImportSongWizardPage;
import org.adonai.uitests.pages.MainMaskPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainMaskSongbookTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;


  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData( false);
    super.start(stage);
    mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();
  }

  @Test
  public void search () throws InterruptedException {
    mainMaskPage.stepToSongbook();
    int unfilteredNumber = mainMaskPage.getLviSongs().getItems().size();
    mainMaskPage.search("Song1");
    Assert.assertEquals(1, mainMaskPage.getLviSongs().getItems().size());
    mainMaskPage.getApplicationTest().type(KeyCode.ESCAPE);
    Assert.assertEquals(unfilteredNumber, mainMaskPage.getLviSongs().getItems().size());
  }

  @Test
  public void addNewSongInSongbook () throws InterruptedException {
    mainMaskPage.stepToSongbook();
    int sizeSessionsBefore = mainMaskPage.getSongsInSongbook().size();
    mainMaskPage.add();

    final String TITLE_TEST = "Test";
    ImportSongWizardPage importSongWizardPage = new ImportSongWizardPage(this);
    importSongWizardPage.newSong(TITLE_TEST);
    mainMaskPage.stepToSongbook();
    int sizeSessionsAfter = mainMaskPage.getSongsInSongbook().size();
    Assert.assertEquals("Number of songs in songbook did not increase", sizeSessionsBefore + 1, sizeSessionsAfter);
    String newSongTitle = mainMaskPage.getSongsInSongbook().get(mainMaskPage.getSongsInSongbook().size() - 1).getTitle();
    Assert.assertEquals ("Title invalid", TITLE_TEST.toUpperCase(), newSongTitle);
  }

  @Test
  public void clickOnListStepsToSongDetails () {
    mainMaskPage.stepToSongbook();
    Assert.assertEquals ("", mainMaskPage.getCurrentContentText());
    doubleClickOn(mainMaskPage.getLviSongs(), MouseButton.PRIMARY);
    Assert.assertEquals ("1 - SONG1", mainMaskPage.getCurrentContentText());
    Assert.assertEquals ("song", mainMaskPage.getCurrentTypeText());
    Assert.assertTrue ("SongEditorPane is not visible", mainMaskPage.getSongEditorPane().isVisible());
  }

  @Test
  public void applyMp3ToCurrentSong () {

  }

  @Test
  public void exportAllSongs () {

  }
}
