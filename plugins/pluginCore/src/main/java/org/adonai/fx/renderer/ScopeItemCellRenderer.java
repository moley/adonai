package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import org.adonai.fx.main.ScopeItem;

public class ScopeItemCellRenderer extends ListCell<ScopeItem> {

  @Override protected void updateItem(ScopeItem item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    }
    else {
      setText(item.getName());
      setGraphic(item.getIcon());
    }
  }
}
