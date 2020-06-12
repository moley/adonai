package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import org.adonai.fx.Consts;
import org.adonai.model.Additional;

public class AdditionalCellRenderer extends ListCell<Additional> {

  @Override protected void updateItem(Additional item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    }
    else {
      setText(item.getAdditionalType().name());
      setGraphic(Consts.createIcon(item.getAdditionalType().getIconName(), Consts.ICON_SIZE_TOOLBAR));
    }
  }
}
