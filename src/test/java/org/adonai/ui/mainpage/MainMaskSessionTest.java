package org.adonai.ui.mainpage;

import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.junit.Assert;
import org.junit.Test;

public class MainMaskSessionTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    mainMaskPage = new MainMaskPage(this);
    mainMaskPage.openStage();
  }

  @Test
  public void addNewSongToSession () throws InterruptedException {
    mainMaskPage.stepToSession(0);
    mainMaskPage.add();
    Thread.sleep(3000);
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
    Assert.assertEquals ("SESSION 'Session1'", mainMaskPage.getCurrentContentText());
    doubleClickOn(mainMaskPage.getLviSession(), MouseButton.PRIMARY);
    Thread.sleep(2000);
    Assert.assertEquals ("SONG 'Song1'", mainMaskPage.getCurrentContentText());
    Assert.assertTrue ("SongEditorPane is not visible", mainMaskPage.getSongEditorPane().isVisible());
  }

  @Test
  public void applyMp3ToCurrentSong () {
    throw new IllegalStateException("TODO");
  }

  @Test
  public void exportSongsInCurrentSession () {
    throw new IllegalStateException("TODO");
  }



}
