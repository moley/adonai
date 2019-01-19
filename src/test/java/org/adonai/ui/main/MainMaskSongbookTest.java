package org.adonai.ui.main;

import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.junit.Test;

public class MainMaskSongbookTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    mainMaskPage = new MainMaskPage(stage, this);
  }

  @Test
  public void addNewSession () throws InterruptedException {
    mainMaskPage.stepToSongbook();
  }
}
