package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.ui.AbstractController;
import org.adonai.ui.Mask;
import org.adonai.ui.UiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddPartController extends AbstractController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddPartController.class);


  @FXML
  private ListView<String> lviTypes;

  @FXML
  private TextField txtSearch;

  private HashMap<String, SongPartType> newTypes = new HashMap<>();

  private HashMap<String, SongPart> existingTypes = new HashMap<String, SongPart>();


  private FilteredList<String> filteredNewPartList;

  private String selectedType;

  private Mask mask;


  private ListView<String> getLviTypes () {
    return lviTypes;
  }

  public String getSelectedType () {
    return selectedType;
  }

  public SongPart getSongPart (final String key) {
    return existingTypes.get(key);
  }

  public SongPartType getNewType (final String key) {
    return newTypes.get(key);
  }


  public void init (Mask mask, Song song) {
    this.mask = mask;

    UiUtils.hideOnEsc(mask.getStage());
    UiUtils.hideOnFocusLost(mask.getStage());


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

    lviTypes.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
          log();
          selectedType = getLviTypes().getSelectionModel().getSelectedItem();
          mask.getStage().close();
        } else if (event.getCode().equals(KeyCode.UP) && event.getSource().equals(lviTypes) && lviTypes.getSelectionModel().getSelectedIndex() == 0) {
          txtSearch.requestFocus();
          lviTypes.getSelectionModel().clearSelection();
        }
      }
    });
    lviTypes.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          log();
          selectedType = getLviTypes().getSelectionModel().getSelectedItem();
          mask.getStage().close();
        }
      }
    });


    txtSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        LOGGER.info("handle onKeyTyped of txtSearch with " + event.getCode() + "-" + event.getText());
        if (event.getCode().equals(KeyCode.ESCAPE)) {
          LOGGER.info("default search");
          filteredNewPartList.setPredicate(s-> true);
        }
        else if (event.getCode().equals(KeyCode.DOWN)) {
          LOGGER.info("step down");
          lviTypes.requestFocus();
          lviTypes.getSelectionModel().selectFirst();
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

  private void log () {
    LOGGER.info("Search: " + txtSearch.getText());
    LOGGER.info("Number of found types: " + lviTypes.getItems().size() + "(" + lviTypes.getItems() + ")");
    LOGGER.info("Selected type: " + lviTypes.getSelectionModel().getSelectedItem());
  }
}
