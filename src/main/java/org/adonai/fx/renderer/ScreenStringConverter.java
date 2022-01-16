package org.adonai.fx.renderer;

import javafx.stage.Screen;
import javafx.util.StringConverter;

public class ScreenStringConverter extends StringConverter<Screen> {

  @Override public String toString(Screen item) {
    if (item == null){
      return null;
    } else {
      return "Screen " + item.getBounds().getMinX() + "-" + item.getBounds().getMinY() + " (" + item.getBounds().getWidth() + "x" + item.getBounds().getHeight() + ")";
    }
  }

  @Override public Screen fromString(String string) {
    return null;
  }
}
