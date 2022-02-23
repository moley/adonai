package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import org.adonai.model.Status;

public class StatusRenderer extends ListCell<Status> {

  @Override protected void updateItem(Status item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    }
    else {
      setText(item.name());
    }
  }
}
