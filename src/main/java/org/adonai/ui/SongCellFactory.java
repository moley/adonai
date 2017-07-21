package org.adonai.ui;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.adonai.model.Song;

public class SongCellFactory implements Callback<ListView<Song>, ListCell<Song>> {
  @Override
  public ListCell<Song> call(ListView<Song> param) {
    ListCell<Song> cell = new ListCell<Song>() {

      @Override
      protected void updateItem(Song item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println (item + "-" + empty);
        if (item != null) {
          setText(item.getId() + "-" + item.getTitle());
        }
        else
          setText(null);

      }
    };
    return cell;
  }

}
