package org.adonai.ui.main;

import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.junit.Assert;
import org.junit.Test;

public class MainMaskSessionsTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    mainMaskPage = new MainMaskPage(stage, this);
    mainMaskPage.stepToSessions();
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
}
