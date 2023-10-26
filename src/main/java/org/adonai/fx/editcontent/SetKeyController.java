package org.adonai.fx.editcontent;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;
import org.adonai.ApplicationEnvironment;
import org.adonai.Key;
import org.adonai.fx.AbstractController;
import org.adonai.fx.TextRenderer;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.services.SongCloneService;
import org.adonai.services.SongTransposeService;

@Slf4j public class SetKeyController extends AbstractController {

  public Button btnSave;
  public Button btnCancel;
  public ComboBox<Key> cboTo;
  public Button btnFrom;
  public TextArea txtTextFrom;
  public TextArea txtTextTo;
  public GridPane panSetKey;
  public Label lblTitle;
  private KeyType keyType;

  private Song currentSong;
  private Song previewSong;

  private TextRenderer textRenderer = new TextRenderer();

  private SongTransposeService songTransposeService = new SongTransposeService();

  private SongCloneService songCloneService = new SongCloneService();

  @FXML public void initialize() {
    cboTo.setItems(FXCollections.observableArrayList(Key.values()));
    panSetKey.setPadding(new Insets(20));

    txtTextFrom.setEditable(false);
    txtTextFrom.setFocusTraversable(false);
    txtTextTo.setEditable(false);
    txtTextFrom.setFocusTraversable(false);

  }

  public void renderContent() {
    SongPart originPart = currentSong.getFirstPart(SongPartType.REFRAIN);
    SongPart previewPart = previewSong.getFirstPart(SongPartType.REFRAIN);

    if (originPart == null) {
      originPart = currentSong.getFirstPart();
      previewPart = previewSong.getFirstPart();
    }

    txtTextFrom.setText(textRenderer.getRenderedText(originPart, getKeyType()));
    txtTextTo.setText(textRenderer.getRenderedText(previewPart, getKeyType()));
  }

  public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);
    this.currentSong = applicationEnvironment.getCurrentSong();
    this.previewSong = songCloneService.cloneSong(this.currentSong);

    String fromChord = currentSong.getChord(keyType);
    if (fromChord == null)
      fromChord = "";

    log.info("From Chord: <" + fromChord + ">");
    btnFrom.setText(fromChord);
    switch (keyType) {
    case CURRENT:
      lblTitle.setText("SET CURRENT KEY");
      break;
    case ORIGINAL:
      lblTitle.setText("SET ORIGINAL KEY");
      break;
    case CURRENT_CAPO:
      lblTitle.setText("SET CURRENT CAPO KEY");
      break;
    default:
      throw new IllegalStateException("Invalid keytype " + keyType);
    }

    if (!fromChord.trim().isEmpty())
      cboTo.getSelectionModel().select(Key.fromString(fromChord));

    cboTo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      log.info(
          "Change chord from " + btnFrom.getText() + " to " + cboTo.getSelectionModel().getSelectedItem().toString());

      switch (keyType) {
      case ORIGINAL:
        previewSong.setOriginalKey(cboTo.getSelectionModel().getSelectedItem().toString());
        if (!btnFrom.getText().trim().isEmpty())
          songTransposeService.recalculateOrigin(previewSong);
        break;
      case CURRENT:
        previewSong.setCurrentKey(cboTo.getSelectionModel().getSelectedItem().toString());
        if (!btnFrom.getText().trim().isEmpty())
          songTransposeService.recalculateCurrent(previewSong);
        break;
      case CURRENT_CAPO:
        previewSong.setCurrentKeyCapo(cboTo.getSelectionModel().getSelectedItem().toString());
        if (!btnFrom.getText().trim().isEmpty())
          songTransposeService.recalculateCurrent(previewSong);
        break;
      }

      renderContent();
    });

    renderContent();

    btnSave.setOnAction(event -> {

      switch (keyType) {
      case ORIGINAL:
        currentSong.setOriginalKey(cboTo.getSelectionModel().getSelectedItem().toString());
        if (!btnFrom.getText().trim().isEmpty())
          songTransposeService.recalculateOrigin(currentSong);
        break;
      case CURRENT:
        currentSong.setCurrentKey(cboTo.getSelectionModel().getSelectedItem().toString());
        if (!btnFrom.getText().trim().isEmpty())
          songTransposeService.recalculateCurrent(currentSong);
      case CURRENT_CAPO:
        currentSong.setCurrentKeyCapo(cboTo.getSelectionModel().getSelectedItem().toString());
        if (!btnFrom.getText().trim().isEmpty())
          songTransposeService.recalculateCurrentCapo(currentSong);
      }

      getStage().close();
    });

    btnCancel.setOnAction(event -> getStage().close());

  }

  public KeyType getKeyType() {
    return keyType;
  }

  public void setKeyType(KeyType keyType) {
    this.keyType = keyType;
  }
}
