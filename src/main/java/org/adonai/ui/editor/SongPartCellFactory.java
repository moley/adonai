package org.adonai.ui.editor;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.services.SongInfoService;

public class SongPartCellFactory implements Callback<ListView<SongPart>, ListCell<SongPart>> {

  private Song song;

  private SongInfoService songService = new SongInfoService();

  public SongPartCellFactory (final Song song) {
    this.song = song;
  }
  @Override
  public ListCell<SongPart> call(ListView<SongPart> param) {
    ListCell<SongPart> cell = new ListCell<SongPart>() {

      @Override
      protected void updateItem(SongPart item, boolean empty) {
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
