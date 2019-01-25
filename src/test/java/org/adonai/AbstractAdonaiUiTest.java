package org.adonai;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.CreateTestDataUiTests;
import org.adonai.ui.mainpage.MainMaskPage;
import org.junit.BeforeClass;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.util.logging.Logger;

import static org.adonai.ui.Consts.ADONAI_HOME_PROP;

public abstract class AbstractAdonaiUiTest extends ApplicationTest {

  protected static final Logger LOGGER = Logger.getLogger(AbstractAdonaiUiTest.class.getName());

  @Override
  public void start(Stage stage) throws Exception {

    new TestDataCreator().createTestData(false);
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
