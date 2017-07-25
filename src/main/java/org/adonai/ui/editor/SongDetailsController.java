package org.adonai.ui.editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import org.adonai.Key;
import org.adonai.model.Song;
import org.adonai.services.SongTransposeService;

import java.util.Optional;

public class SongDetailsController {


  private PartEditor partEditor;
  @FXML
  private ComboBox<Key> cboCurrentKey;

  @FXML
  private ComboBox<Key> cboOriginalKey;




  public void setPartEditor(PartEditor partEditor) {
    this.partEditor = partEditor;

    cboCurrentKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboOriginalKey.setItems(FXCollections.observableArrayList(Key.values()));

    Song currentSong = partEditor.getSongEditor().getSong();

    if (currentSong.getCurrentKey() != null) {
      cboCurrentKey.getSelectionModel().select(Key.fromString(currentSong.getCurrentKey()));
    }
    else
      cboCurrentKey.getSelectionModel().clearSelection();

    if (currentSong.getOriginalKey() != null) {
      cboOriginalKey.getSelectionModel().select(Key.fromString(currentSong.getOriginalKey()));
    }
    else
      cboOriginalKey.getSelectionModel().clearSelection();

    cboOriginalKey.valueProperty().addListener(new ChangeListener<Key>() {
      @Override
      public void changed(ObservableValue<? extends Key> observable, Key oldValue, Key newValue) {
        currentSong.setOriginalKey(newValue.name());
      }
    });

    cboOriginalKey.valueProperty().addListener(new ChangeListener<Key>() {
      @Override
      public void changed(ObservableValue<? extends Key> observable, Key oldValue, Key newValue) {
        currentSong.setOriginalKey(newValue.name());
      }
    });

    cboCurrentKey.valueProperty().addListener(new ChangeListener<Key>() {
      @Override
      public void changed(ObservableValue<? extends Key> observable, Key oldValue, Key newValue) {
        if (currentSong.getCurrentKey() != null) {
          Key previousKey = Key.fromString(currentSong.getCurrentKey());

          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setHeaderText("You have changed the key of the song. Do you want to transpose all chords from " +
                              currentSong.getCurrentKey() + " to " + newValue.name() + "?");
          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK){
            SongTransposeService songTransposeService = new SongTransposeService();
            songTransposeService.transpose(currentSong, previousKey, newValue);
          }
        }
        currentSong.setCurrentKey(newValue.name());
      }


    });

  }

}
