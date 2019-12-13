package org.adonai.ui;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.AbstractAdonaiUiTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

public class SmallUiTest extends ApplicationTest {

  private TextField txtSometing;

  protected static final Logger LOGGER = LoggerFactory.getLogger(SmallUiTest.class);


  @Override
  public void start(Stage stage) throws Exception {
    txtSometing = new TextField();
    Scene scene = new Scene(txtSometing, Consts.getDefaultWidth(), Consts.getDefaultHeight(), true);
    scene.getStylesheets().add("/adonai.css");
    stage.setScene(scene);
    stage.setAlwaysOnTop(true);
    stage.setResizable(false);
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (KeyCode.ESCAPE == event.getCode()) {
        UiUtils.close(stage);
      }
    });
    stage.initStyle(StageStyle.UNDECORATED);

    stage.show();
    LOGGER.info("Scene: " + txtSometing.getScene());
  }

  @Test
  public void someTest () {


  }



}