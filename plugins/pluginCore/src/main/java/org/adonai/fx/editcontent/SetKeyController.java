package org.adonai.fx.editcontent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;
import org.adonai.ApplicationEnvironment;
import org.adonai.Key;
import org.adonai.fx.AbstractController;
import org.adonai.fx.viewer.TextRenderer;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.services.SongCloneService;
import org.adonai.services.SongTransposeService;

@Slf4j
public class SetKeyController extends AbstractController {

  public Button btnSave;
  public Button btnCancel;
  public ComboBox cboTo;
  public Button btnFrom;
  public TextArea txtTextFrom;
  public TextArea txtTextTo;
  public GridPane panSetKey;
  public Label lblTitle;
  private boolean originalKey;

  private Song currentSong;
  private Song previewSong;

  private TextRenderer textRenderer = new TextRenderer();

  private SongTransposeService songTransposeService = new SongTransposeService();

  private SongCloneService songCloneService = new SongCloneService();

  public boolean isOriginalKey() {
    return originalKey;
  }

  public void setOriginalKey(boolean originalKey) {
    this.originalKey = originalKey;
  }

  @FXML
  public void initialize () {
    cboTo.setItems(FXCollections.observableArrayList(Key.values()));
    panSetKey.setPadding(new Insets(20));

    txtTextFrom.setEditable(false);
    txtTextFrom.setFocusTraversable(false);
    txtTextTo.setEditable(false);
    txtTextFrom.setFocusTraversable(false);


  }

  public void renderContent () {
    SongPart originPart = currentSong.getFirstPart(SongPartType.REFRAIN);
    SongPart previewPart = previewSong.getFirstPart(SongPartType.REFRAIN);

    if (originPart == null) {
      originPart = currentSong.getFirstPart();
      previewPart = previewSong.getFirstPart();
    }

    txtTextFrom.setText(textRenderer.getRenderedText(originPart, isOriginalKey()));
    txtTextTo.setText(textRenderer.getRenderedText(previewPart, isOriginalKey()));
  }

  public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);
    this.currentSong = applicationEnvironment.getCurrentSong();
    this.previewSong = songCloneService.cloneSong(this.currentSong);

    String fromChord = originalKey ? currentSong.getOriginalKey(): currentSong.getCurrentKey();
    log.info("From Chord: <" + fromChord + ">");
    btnFrom.setText(fromChord);
    lblTitle.setText(originalKey ? "SET ORIGINAL KEY": "SET CURRENT KEY");

    if (! fromChord.trim().isEmpty())
      cboTo.getSelectionModel().select(Key.fromString(fromChord));


    cboTo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
       log.info("Change chord from " + btnFrom.getText() + " to "+ cboTo.getSelectionModel().getSelectedItem().toString());

       if (originalKey) {
         previewSong.setOriginalKey(cboTo.getSelectionModel().getSelectedItem().toString());
         if (! fromChord.trim().isEmpty())
           songTransposeService.recalculateOrigin(previewSong);
       }
       else {
         previewSong.setCurrentKey(cboTo.getSelectionModel().getSelectedItem().toString());
         if (! fromChord.trim().isEmpty())
           songTransposeService.recalculateCurrent(previewSong);
       }

       renderContent();
    });

    renderContent();



    btnSave.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        if (originalKey) {
          currentSong.setOriginalKey(cboTo.getSelectionModel().getSelectedItem().toString());
          songTransposeService.recalculateOrigin(currentSong);
        }
        else {
          currentSong.setCurrentKey(cboTo.getSelectionModel().getSelectedItem().toString());
          songTransposeService.recalculateCurrent(currentSong);
        }
        getStage().close();
      }
    });

    btnCancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        getStage().close();
      }
    });

  }
}
