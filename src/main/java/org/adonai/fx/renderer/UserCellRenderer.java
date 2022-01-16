package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import org.adonai.model.User;

public class UserCellRenderer extends ListCell<User> {

  @Override protected void updateItem(User item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    }
    else {
      setText(item.getUsername());
    }
  }
}
