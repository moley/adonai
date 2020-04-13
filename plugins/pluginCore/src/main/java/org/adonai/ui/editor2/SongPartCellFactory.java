package org.adonai.ui.editor2;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.adonai.model.Song;
import org.adonai.model.SongStructItem;
import org.adonai.services.SongInfoService;

public class SongPartCellFactory implements Callback<ListView<SongStructItem>, ListCell<SongStructItem>> {

  private Song song;

  private SongInfoService songService = new SongInfoService();

  public SongPartCellFactory (final Song song) {
    this.song = song;
  }
  @Override
  public ListCell<SongStructItem> call(ListView<SongStructItem> param) {
    ListCell<SongStructItem> cell = new ListCell<SongStructItem>() {

      @Override
      protected void updateItem(SongStructItem item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
          setText(songService.getPreview(song, item));
        }
        else
          setText(null);
      }
    };
    return cell;
  }

}
