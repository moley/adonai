package org.adonai.ui.main;

import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.junit.Assert;
import org.junit.Test;

public class MainMaskSessionTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    mainMaskPage = new MainMaskPage(stage, this);
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


}
