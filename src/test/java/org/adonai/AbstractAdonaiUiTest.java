package org.adonai;

import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;

public abstract class AbstractAdonaiUiTest extends ApplicationTest {

  @Override
  public void start(Stage stage) throws Exception {
    System.setProperty("config", new File("src/test/resources/uitests").getAbsolutePath());
  }


}
