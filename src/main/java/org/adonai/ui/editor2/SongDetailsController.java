package org.adonai.ui.editor2;

import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.adonai.Key;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.User;
import org.adonai.services.SongTransposeService;

public class SongDetailsController {

  @FXML
  private ComboBox<Key> cboCurrentKey;

  @FXML
  private ComboBox<Key> cboOriginalKey;


  @FXML
  private ComboBox<User> cboLeadVoice;

  @FXML
  private TextField txtPreset;

  @FXML
  private TextField txtTitle;


  private Song currentSong;


  private Configuration configuration;

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }


  public Song getCurrentSong() {
    return currentSong;
  }

  public void setCurrentSong(Song currentSong) {
    this.currentSong = currentSong;
  }



  public void init() {

    cboCurrentKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboOriginalKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboLeadVoice.setItems(FXCollections.observableArrayList(configuration.getUsers()));

    txtPreset.textProperty().bindBidirectional(currentSong.presetProperty());
    txtTitle.textProperty().bindBidirectional(currentSong.titleProperty());

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