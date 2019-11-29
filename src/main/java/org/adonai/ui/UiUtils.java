package org.adonai.ui;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UiUtils {

  public static void close (final Stage stage) {
    stage.fireEvent( new WindowEvent( stage, WindowEvent.WINDOW_CLOSE_REQUEST));
  }

  public static Bounds getBounds (Parent control) {
    if (control == null)
      throw new IllegalStateException("Control must not be null");
    return control.localToScreen(control.getLayoutBounds());
  }

  public static void hideOnEsc (final Stage stage) {
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent escEvent) -> {
      if (KeyCode.ESCAPE == escEvent.getCode()) {
        escEvent.consume();
        stage.close();
      }
    });

  }

  public static void hideOnFocusLost (final Stage stage) {
    stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (! isNowFocused) {
        stage.hide();
      }
    });
  }


}
