package org.adonai.fx.editcontent;

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
import org.adonai.ApplicationEnvironment;
import org.adonai.Key;
import org.adonai.fx.AbstractController;
import org.adonai.fx.viewer.TextRenderer;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;

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

  private TextRenderer textRenderer = new TextRenderer();

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

  public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);
    this.currentSong = applicationEnvironment.getCurrentSong();

    String fromChord = originalKey ? currentSong.getOriginalKey(): currentSong.getCurrentKey();
    btnFrom.setText(fromChord);
    lblTitle.setText(originalKey ? "SET ORIGINAL KEY": "SET CURRENT KEY");

    cboTo.getSelectionModel().select(Key.fromString(fromChord));

    SongPart previewPart = currentSong.getFirstPart(SongPartType.REFRAIN);

    txtTextFrom.setText(textRenderer.getRenderedText(previewPart));
    txtTextTo.setText(textRenderer.getRenderedText(previewPart));

    btnSave.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

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
