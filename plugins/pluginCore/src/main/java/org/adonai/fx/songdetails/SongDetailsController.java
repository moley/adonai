package org.adonai.fx.songdetails;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.WindowEvent;
import org.adonai.Key;
import org.adonai.actions.ConnectSongWithMp3Action;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.ExtensionSelectorController;
import org.adonai.fx.renderer.UserCellRenderer;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.User;
import org.adonai.services.SongTransposeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongDetailsController extends AbstractController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongDetailsController.class);

  @FXML private Label lblCurrentMp3;

  @FXML private Button btnAssignMp3;

  @FXML private ComboBox<Key> cboCurrentKey;

  @FXML private ComboBox<Key> cboOriginalKey;

  @FXML private ComboBox<User> cboLeadVoice;

  @FXML private TextField txtPreset;

  @FXML private TextField txtTitle;

  @FXML private Spinner<Integer> spiSpeed;

  @FXML private Button btnRecalculateOriginKey;

  @FXML private Button btnRecalculateCurrentKey;

  private Song currentSong;

  private Configuration configuration;

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public void setCurrentSong(Song currentSong) {
    this.currentSong = currentSong;
  }

  @FXML public void initialize() {
    cboLeadVoice.setCellFactory(cellfactory -> new UserCellRenderer());
    spiSpeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300));
    spiSpeed.setEditable(true);

    btnRecalculateCurrentKey.setGraphic(Consts.createIcon("fas-calculator", Consts.ICON_SIZE_VERY_SMALL));
    btnRecalculateCurrentKey.setTooltip(new Tooltip("Calculate current key with chords of origin key"));
    btnRecalculateOriginKey.setGraphic(Consts.createIcon("fas-calculator", Consts.ICON_SIZE_VERY_SMALL));
    btnRecalculateOriginKey.setTooltip(new Tooltip("Calculate origin key with chords of current key"));

    cboCurrentKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboOriginalKey.setItems(FXCollections.observableArrayList(Key.values()));

  }

  public void save() {
    currentSong.setPreset(txtPreset.getText());
    currentSong.setTitle(txtTitle.getText());
    currentSong.setSpeed(spiSpeed.getValue());
    currentSong.setLeadVoice(cboLeadVoice.getSelectionModel().getSelectedItem());
    currentSong.setOriginalKey(cboOriginalKey.getSelectionModel().getSelectedItem() != null ?
        cboOriginalKey.getSelectionModel().getSelectedItem().toString() :
        null);
    currentSong.setCurrentKey(cboCurrentKey.getSelectionModel().getSelectedItem() != null ?
        cboCurrentKey.getSelectionModel().getSelectedItem().toString() :
        null);
    currentSong.setSpeed(spiSpeed.getValue());
  }

  public void loadData() {

    cboLeadVoice.setItems(FXCollections.observableArrayList(configuration.getUsers()));
    lblCurrentMp3.setText(getMp3Label(currentSong));

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
        currentSong.setCurrentKey(cboCurrentKey.getSelectionModel().getSelectedItem().name());
        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.recalculateCurrent(currentSong);

      }
    });
    btnRecalculateOriginKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        currentSong.setOriginalKey(cboOriginalKey.getSelectionModel().getSelectedItem().name());
        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.recalculateOrigin(currentSong);
      }
    });

    btnAssignMp3.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        ConnectSongWithMp3Action connectSongWithMp3Action = new ConnectSongWithMp3Action();
        connectSongWithMp3Action.connect(configuration, currentSong, new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            LOGGER.info("handle onHiding event");

            ExtensionSelectorController extensionSelectorController = connectSongWithMp3Action
                .getExtensionSelectorController();
            File selectedExtension = extensionSelectorController.getSelectedExtension();

            if (selectedExtension != null) {
              String songExtension = selectedExtension.getAbsolutePath();
              Additional additional = new Additional();
              additional.setAdditionalType(AdditionalType.AUDIO);
              additional.setLink(songExtension);
              AdditionalsImporter additionalsImporter = new AdditionalsImporter();
              additionalsImporter.refreshCache(currentSong, additional, true);
              currentSong.setAdditional(additional);
              lblCurrentMp3.setText(getMp3Label(currentSong));

              LOGGER.info("connect song " + currentSong + " with songfile " + selectedExtension);
            } else
              LOGGER.info("no extension selected");
          }
        });

      }
    });
  }

  private String getMp3Label(final Song song) {
    Additional additional = song.getAdditional(AdditionalType.AUDIO);
    return additional != null ?
        additional.getLink()
            .substring((additional.getLink().lastIndexOf("/") >= 0 ? additional.getLink().lastIndexOf("/") + 1: 0)) :
        "n.a.";
  }

}
