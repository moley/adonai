package org.adonai.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class ContentChangeableController extends AbstractController{

  @FXML
  private Button btnSave;

  @FXML
  private Button btnCancel;

  private EventHandler<ActionEvent> onSongContentChange;

  private Stage stage;

  @FXML
  public void initialize () {
    btnSave.setGraphic(Consts.createIcon("far-save", Consts.ICON_SIZE_TOOLBAR));
    btnCancel.setGraphic(Consts.createIcon("far-window-close", Consts.ICON_SIZE_TOOLBAR));

    btnSave.setOnAction(new EventHandler<ActionEvent>() {

      @Override public void handle(ActionEvent event) {
        save();
        if (getOnSongContentChange() != null)
          getOnSongContentChange().handle(event);

        getStage().close();
      }
    });

    btnCancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        getStage().close();
      }
    });

  }

  public EventHandler<ActionEvent> getOnSongContentChange() {
    return onSongContentChange;
  }

  public void setOnSongContentChange(EventHandler<ActionEvent> onSongContentChange) {
    this.onSongContentChange = onSongContentChange;
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  protected abstract void save ();
}
