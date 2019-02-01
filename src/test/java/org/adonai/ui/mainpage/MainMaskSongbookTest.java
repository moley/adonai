package org.adonai.ui.mainpage;

import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.imports.ImportSongWizardPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainMaskSongbookTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;



  @Override
  public void start(Stage stage) throws Exception {
    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData(false);
    super.start(stage);
    mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();
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
    Assert.assertEquals ("SONGBOOK", mainMaskPage.getCurrentContentText());
    doubleClickOn(mainMaskPage.getLviSongs(), MouseButton.PRIMARY);
    Assert.assertEquals ("SONG 'Song1'", mainMaskPage.getCurrentContentText());
    Assert.assertTrue ("SongEditorPane is not visible", mainMaskPage.getSongEditorPane().isVisible());
  }

  @Test
  public void applyMp3ToCurrentSong () {

  }

  @Test
  public void exportAllSongs () {

  }
}
