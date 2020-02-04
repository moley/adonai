package org.adonai.ui.editor2;

import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import org.adonai.Key;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.User;
import org.adonai.services.SongTransposeService;
import org.adonai.ui.Consts;
import org.adonai.ui.UiUtils;

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

  @FXML
  private Spinner<Integer> spiSpeed;

  @FXML
  private Button btnRecalculateOriginKey;

  @FXML
  private Button btnRecalculateCurrentKey;


  private Song currentSong;


  private Configuration configuration;

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
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
    spiSpeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300));
    spiSpeed.setEditable(true);
    spiSpeed.getValueFactory().valueProperty().bindBidirectional(currentSong.speedProperty());

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

    btnRecalculateCurrentKey.setGraphic(Consts.createIcon("fa-calculator", Consts.ICON_SIZE_VERY_SMALL));
    btnRecalculateCurrentKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.recalculateCurrent(currentSong);

      }
    });
    btnRecalculateOriginKey.setGraphic(Consts.createIcon("fa-calculator", Consts.ICON_SIZE_VERY_SMALL));
    btnRecalculateOriginKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.recalculateOrigin(currentSong);
      }
    });

  }

}
