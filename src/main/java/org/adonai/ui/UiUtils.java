package org.adonai.ui;

import javafx.geometry.Bounds;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UiUtils {

  public static void close (final Stage stage) {
    stage.fireEvent( new WindowEvent( stage, WindowEvent.WINDOW_CLOSE_REQUEST));
  }

  public static Bounds getBounds (Control control) {
    return control.localToScreen(control.getLayoutBounds());
  }
}
