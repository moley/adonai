package org.adonai.ui.select;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.ui.UiUtils;


public class SelectController<T> {

  @FXML
  ListView<T> lviSelectItems;

  private T selectedItem;

  public T getSelectedItem () {
    return selectedItem;
  }

  public ListView<T> getLviSelectItems () {
    return lviSelectItems;
  }

  public void clearSelection () {
    this.selectedItem = null;
  }

  public void initialize() {
    lviSelectItems.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          selectedItem = getLviSelectItems().getSelectionModel().getSelectedItem();
          close();
        }
      }
    });

    lviSelectItems.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
          selectedItem = getLviSelectItems().getSelectionModel().getSelectedItem();
          close();
        }
      }
    });

  }

  private Stage getStage () {
    return (Stage) lviSelectItems.getScene().getWindow();
  }

  public void close() {
    UiUtils.close(getStage());
  }

}
