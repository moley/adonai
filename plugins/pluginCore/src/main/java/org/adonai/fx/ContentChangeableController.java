package org.adonai.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ContentChangeableController extends AbstractController{

  private EventHandler<ActionEvent> onSongContentChange;

  private Stage stage;

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
}
