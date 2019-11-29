package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;

public class AddPartController {

  @FXML
  private ListView<String> lviTypes;

  @FXML
  private TextField txtSearch;

  private HashMap<String, SongPartType> newTypes = new HashMap<>();

  private HashMap<String, SongPart> existingTypes = new HashMap<String, SongPart>();


  private FilteredList<String> filteredNewPartList;


  ListView<String> getLviTypes () {
    return lviTypes;
  }

  public SongPart getSongPart (final String key) {
    return existingTypes.get(key);
  }

  public SongPartType getNewType (final String key) {
    return newTypes.get(key);
  }


  public void init (Song song) {

    txtSearch.requestFocus();




    List<String> newTypeItems = new ArrayList<>();

    for (SongPartType nextType: SongPartType.values()) {
      String item = "New " + nextType.getDisplayname();
      newTypeItems.add(item);
      newTypes.put(item, nextType);
    }

    for (SongPart nextPart: song.getSongParts()) {
      if (nextPart.getReferencedSongPart() == null && nextPart.getSongPartType() != null) {
        String text = nextPart.getFirstLine().getText();
        if (text.length() > 15)
          text = nextPart.getFirstLine().getText().substring(0, 10) + "...";
        String item = "Copy " + nextPart.getSongPartType().getDisplayname() + "'" + text + "'";
        newTypeItems.add(item);
        existingTypes.put(item, nextPart);
      }

    }

    filteredNewPartList = new FilteredList<String>(FXCollections.observableArrayList(newTypeItems), s->true);
    lviTypes.setItems(filteredNewPartList);


    txtSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
          filteredNewPartList.setPredicate(s-> true);
        }
        else if (event.getCode().equals(KeyCode.DOWN)) {
          lviTypes.requestFocus();
          lviTypes.getSelectionModel().selectFirst();
        }
        else if (event.getCode().equals(KeyCode.UP) && event.getSource().equals(lviTypes) && lviTypes.getSelectionModel().getSelectedIndex() == 0) {
          txtSearch.requestFocus();
        }
      }
    });
    txtSearch.textProperty().addListener(obs->{
      String filter = txtSearch.getText();
      if(filter == null || filter.length() == 0) {
        filteredNewPartList.setPredicate(s -> true);
      }
      else {
        filteredNewPartList.setPredicate(s -> s.toUpperCase().contains(filter.toUpperCase()));
      }
    });


  }
}
