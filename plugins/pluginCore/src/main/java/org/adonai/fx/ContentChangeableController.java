package org.adonai.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class ContentChangeableController extends AbstractController{

  @FXML
  private Button btnSave;

  @FXML
  private Button btnCancel;

  private EventHandler<ActionEvent> onSongContentChange;


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



  protected abstract void save ();
}
