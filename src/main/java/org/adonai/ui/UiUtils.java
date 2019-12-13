package org.adonai.ui;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(UiUtils.class);

  public static  String getLayout (final Window windows) {
    return windows.getX() + "-" + windows.getY() + "-" + windows.getWidth() + "-" + windows.getHeight();
  }
  public static void close (final Stage stage) {
    stage.fireEvent( new WindowEvent( stage, WindowEvent.WINDOW_CLOSE_REQUEST));
  }

  public static double getNodeOffset (ScrollPane scrollPane, Node node) {
    double nodeY = node.localToScreen(node.getLayoutBounds()).getMinY();
    double scrollPaneHeight = scrollPane.getContent().getBoundsInLocal().getHeight();

    double offsetY = nodeY/scrollPaneHeight;
    return offsetY;
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
