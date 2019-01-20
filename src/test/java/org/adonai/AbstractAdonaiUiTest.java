package org.adonai;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.CreateTestDataUiTests;
import org.junit.BeforeClass;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;

import static org.adonai.ui.Consts.ADONAI_HOME_PROP;

public abstract class AbstractAdonaiUiTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    TestDataCreator.main(new String [0]);
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
