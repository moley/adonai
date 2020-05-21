package org.adonai.fx.renderer;

import javafx.scene.control.ListCell;
import org.adonai.model.Song;
import org.adonai.model.SongStructItem;

public class SongCellRenderer extends ListCell<Song> {

  @Override protected void updateItem(Song item, boolean empty) {
    super.updateItem(item, empty);

    if (item == null || empty) {
      setText(null);
      setGraphic(null);
    } else {
      setText(item.getId() + " - " + item.getTitle());
      //setGraphic(item.getIcon());
    }
  }
}