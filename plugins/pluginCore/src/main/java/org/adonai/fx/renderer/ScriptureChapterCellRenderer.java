package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import org.adonai.bibles.Chapter;

public class ScriptureChapterCellRenderer extends ListCell<Chapter> {

  @Override protected void updateItem(Chapter item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    }
    else {
      setText(item.getNumber() + " (" + item.getVerses().size() + ")");
    }
  }
}