package org.adonai.ui.select;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class SelectController<T> {

  @FXML
  ListView<T> lviSelectItems;

  public T getSelectedItem () {
    return lviSelectItems.getSelectionModel().getSelectedItem();
  }

  public ListView<T> getLviSelectItems () {
    return lviSelectItems;
  }

  public void initialize() {
    lviSelectItems.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          close();
        }
      }
    });

    lviSelectItems.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
          close();
      }
    });

  }

  private Stage getStage () {
    return (Stage) lviSelectItems.getScene().getWindow();
  }

  public void close() {
    Stage stage = getStage();
    stage.fireEvent( new WindowEvent( stage, WindowEvent.WINDOW_CLOSE_REQUEST));
  }

}
