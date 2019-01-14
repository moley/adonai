package org.adonai.actions.add;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.stage.WindowEvent;
import org.adonai.actions.SelectAction;
import org.adonai.model.Song;
import org.adonai.ui.SongCellFactory;

import java.util.ArrayList;
import java.util.List;


public class AddSongToSessionAction extends SelectAction<Song> {

  public static String NEW_SONG_AND_ADD_TO_SESSION_LABEL = "Create new song and add to session";

  public void open (List<Song> objects, Control control, EventHandler<WindowEvent> onCloseEventHandler) {
    List<Song> allSongs = new ArrayList<Song>();
    Song newSongItem = new Song();
    newSongItem.setTitle(NEW_SONG_AND_ADD_TO_SESSION_LABEL);
    allSongs.add(newSongItem);
    allSongs.addAll(objects);
    super.open(allSongs, control, onCloseEventHandler);
    getSelectMask().getController().getLviSelectItems().setCellFactory(new SongCellFactory());
  }

}
