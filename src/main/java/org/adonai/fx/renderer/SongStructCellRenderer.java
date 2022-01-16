package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import org.adonai.model.SongStructItem;

public class SongStructCellRenderer extends ListCell<SongStructItem> {

  @Override protected void updateItem(SongStructItem item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    } else {
      setText(item.getText());
      //setGraphic(item.getIcon());
    }
  }
}