package org.adonai;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.TestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

public abstract class AbstractAdonaiUiTest extends ApplicationTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractAdonaiUiTest.class);

  @Override
  public void start(Stage stage) throws Exception {
    new TestDataCreator().createTestData(TestUtil.getDefaultTestDataPath(), false);
  }


  public void key (KeyCode ... keyCode) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        press(keyCode).release(keyCode);
      }
    });


    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }

  }


}
