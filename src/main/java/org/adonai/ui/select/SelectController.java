package org.adonai.ui.select;

import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.adonai.ui.UiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectController<T> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(SelectController.class);


  @FXML TextField txtSearchQuery;

  @FXML
  ListView<T> lviSelectItems;

  private FilteredList<T> filteredData;


  private T selectedItem;

  public T getSelectedItem () {
    return selectedItem;
  }

  public ListView<T> getLviSelectItems () {
    return lviSelectItems;
  }

  public void setFilteredData (final FilteredList<T> filteredData) {
    this.filteredData = filteredData;
    lviSelectItems.setItems(filteredData);
  }

  public void clearSelection () {
    this.selectedItem = null;
  }

  public void initialize() {
    txtSearchQuery.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
          filteredData.setPredicate(s-> true);
        }
        else if (event.getCode().equals(KeyCode.DOWN)) {
          lviSelectItems.requestFocus();
          lviSelectItems.getSelectionModel().selectFirst();
        }
      }
    });
    txtSearchQuery.textProperty().addListener(obs->{
      String filter = txtSearchQuery.getText();
      if(filter == null || filter.length() == 0) {
        filteredData.setPredicate(s -> true);
      }
      else {
        filteredData.setPredicate(new Predicate<T>() {
          @Override public boolean test(T s) {
            return s.toString().toUpperCase().contains(filter.toUpperCase());
          }
        });
      }
    });

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
        LOGGER.info("handle key event " + event.getCode());

        if (event.getCode().equals(KeyCode.UP) && lviSelectItems.getSelectionModel().isSelected(0)) {
          txtSearchQuery.requestFocus();
          txtSearchQuery.setText("");
        }
        if (event.getCode().equals(KeyCode.ENTER)) {
          selectedItem = getLviSelectItems().getSelectionModel().getSelectedItem();
          LOGGER.info("Select " + selectedItem);
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
