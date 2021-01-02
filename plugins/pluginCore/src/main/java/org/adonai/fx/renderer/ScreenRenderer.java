package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import javafx.stage.Screen;
import org.adonai.model.Song;

public class ScreenRenderer extends ListCell<Screen> {

  @Override protected void updateItem(Screen item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    } else {
      setText("Screen " + item.getBounds().getMinX() + "-" + item.getBounds().getMinY() + " (" + item.getBounds().getWidth() + "x" + item.getBounds().getHeight() + ")");
      //setGraphic(item.getIcon());
    }
  }
}