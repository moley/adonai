package org.adonai.fx;

import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(UiUtils.class);

  public static void applyCss (final Scene scene) {

    String url = UiUtils.class.getResource("/default.css").toExternalForm();
    LOGGER.info("apply css from " + url);
    scene.getStylesheets().add(url);
  }

  public static void close (final Window stage) {
    stage.fireEvent( new WindowEvent( stage, WindowEvent.WINDOW_CLOSE_REQUEST));
  }

  public static Bounds getBounds (Parent control) {
    if (control == null)
      throw new IllegalArgumentException("Argument 'control' must not be null");
    return control.localToScreen(control.getLayoutBounds());
  }





}
