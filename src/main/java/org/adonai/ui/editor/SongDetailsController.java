package org.adonai.ui.editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.adonai.Key;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.User;
import org.adonai.services.SongTransposeService;

import java.util.Optional;

public class SongDetailsController {


  private PartEditor partEditor;
  @FXML
  private ComboBox<Key> cboCurrentKey;

  @FXML
  private ComboBox<Key> cboOriginalKey;

  @FXML
  private Spinner<Integer> spTransposeInfo;

  @FXML
  private ComboBox<User> cboLeadVoice;




  public void setPartEditor(PartEditor partEditor) {
    this.partEditor = partEditor;

    Song currentSong = partEditor.getSongEditor().getSong();
    Configuration configuration = partEditor.getSongEditor().getConfiguration();

    cboCurrentKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboOriginalKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboLeadVoice.setItems(FXCollections.observableArrayList(configuration.getUsers()));

    //set transpose info: negative value is needed when using capo
    int initialValue = currentSong.getTransposeInfo() != null ? currentSong.getTransposeInfo(): 0;
    spTransposeInfo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-8, 8, initialValue));

    //set lead voice
    if (currentSong.getLeadVoice() != null) {
      cboLeadVoice.getSelectionModel().select(currentSong.getLeadVoice());
    }
    else
      cboLeadVoice.getSelectionModel().clearSelection();

    //save original key listener
    cboLeadVoice.valueProperty().addListener(new ChangeListener<User>() {
      @Override
      public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
        currentSong.setLeadVoice(newValue);
      }
    });


    //set current key
    if (currentSong.getCurrentKey() != null) {
      cboCurrentKey.getSelectionModel().select(Key.fromString(currentSong.getCurrentKey()));
    }
    else
      cboCurrentKey.getSelectionModel().clearSelection();

    //set original key
    if (currentSong.getOriginalKey() != null) {
      cboOriginalKey.getSelectionModel().select(Key.fromString(currentSong.getOriginalKey()));
    }
    else
      cboOriginalKey.getSelectionModel().clearSelection();

    spTransposeInfo.valueProperty().addListener(new ChangeListener<Integer>() {
      @Override
      public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
        currentSong.setTransposeInfo(newValue);
      }
    });

    //save original key listener
    cboOriginalKey.valueProperty().addListener(new ChangeListener<Key>() {
      @Override
      public void changed(ObservableValue<? extends Key> observable, Key oldValue, Key newValue) {
        currentSong.setOriginalKey(newValue.name());
      }
    });



    //save current key listener
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
