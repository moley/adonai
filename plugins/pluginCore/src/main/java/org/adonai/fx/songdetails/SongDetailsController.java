package org.adonai.fx.songdetails;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import org.adonai.Key;
import org.adonai.fx.Consts;
import org.adonai.fx.ContentChangeableController;
import org.adonai.fx.renderer.UserCellRenderer;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.User;
import org.adonai.services.SongTransposeService;

public class SongDetailsController extends ContentChangeableController {

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


  @FXML
  public void initialize () {
    super.initialize();
    cboLeadVoice.setCellFactory(cellfactory -> new UserCellRenderer());
    spiSpeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300));
    spiSpeed.setEditable(true);

    btnRecalculateCurrentKey.setGraphic(Consts.createIcon("fas-calculator", Consts.ICON_SIZE_VERY_SMALL));
    btnRecalculateOriginKey.setGraphic(Consts.createIcon("fas-calculator", Consts.ICON_SIZE_VERY_SMALL));
    cboCurrentKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboOriginalKey.setItems(FXCollections.observableArrayList(Key.values()));


  }

  @Override protected void save() {
    currentSong.setPreset(txtPreset.getText());
    currentSong.setTitle(txtTitle.getText());
    currentSong.setSpeed(spiSpeed.getValue());
    currentSong.setLeadVoice(cboLeadVoice.getSelectionModel().getSelectedItem());
    currentSong.setOriginalKey(cboOriginalKey.getSelectionModel().getSelectedItem() != null ? cboOriginalKey.getSelectionModel().getSelectedItem().toString() : null);
    currentSong.setCurrentKey(cboCurrentKey.getSelectionModel().getSelectedItem() != null ? cboCurrentKey.getSelectionModel().getSelectedItem().toString() : null);
    Key newKey = cboCurrentKey.getValue();

    if (currentSong.getCurrentKey() != null) {
      Key previousKey = Key.fromString(currentSong.getCurrentKey());


      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setHeaderText(
          "You have changed the key of the song. Do you want to transpose all chords from " + currentSong.getCurrentKey() + " to " + newKey.name() + "?");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.transpose(currentSong, previousKey, newKey);
      }
    }
    currentSong.setCurrentKey(newKey.name());

  }

  public void loadData() {

    cboLeadVoice.setItems(FXCollections.observableArrayList(configuration.getUsers()));

    txtPreset.setText(currentSong.getPreset());
    txtTitle.setText(currentSong.getTitle());
    if (currentSong.getSpeed() != null)
      spiSpeed.getValueFactory().setValue(currentSong.getSpeed());
    else
      spiSpeed.getValueFactory().setValue(0);

    //set lead voice
    if (currentSong.getLeadVoice() != null) {
      cboLeadVoice.getSelectionModel().select(currentSong.getLeadVoice());
    } else
      cboLeadVoice.getSelectionModel().clearSelection();

    //set current key
    if (currentSong.getCurrentKey() != null) {
      cboCurrentKey.getSelectionModel().select(Key.fromString(currentSong.getCurrentKey()));
    } else
      cboCurrentKey.getSelectionModel().clearSelection();

    //set original key
    if (currentSong.getOriginalKey() != null) {
      cboOriginalKey.getSelectionModel().select(Key.fromString(currentSong.getOriginalKey()));
    } else
      cboOriginalKey.getSelectionModel().clearSelection();


    btnRecalculateCurrentKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        SongTransposeService songTransposeService = new SongTransposeService();
        currentSong.setOriginalKey(cboOriginalKey.getSelectionModel().getSelectedItem().name());
        songTransposeService.recalculateCurrent(currentSong);

      }
    });
    btnRecalculateOriginKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        currentSong.setCurrentKey(cboCurrentKey.getSelectionModel().getSelectedItem().name());
        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.recalculateOrigin(currentSong);
      }
    });
  }
}
