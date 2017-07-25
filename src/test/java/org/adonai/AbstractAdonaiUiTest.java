package org.adonai;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;

public abstract class AbstractAdonaiUiTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    System.setProperty("config", new File("src/test/resources/uitests").getAbsolutePath());
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
