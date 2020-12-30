package org.adonai.fx.search;

import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.adonai.fx.AbstractController;
import org.adonai.model.NamedElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchController<T extends NamedElement> extends AbstractController {

  @FXML private ListView<T> lviItems;
  @FXML private TextField txtSearchQuery;

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

  private FilteredList<? extends NamedElement> filteredData;

  private T selectedElement;


  @FXML
  public void initialize() {
    selectedElement = null;
    txtSearchQuery.setOnKeyPressed(event -> {
      LOGGER.info("event keyreleased " + event.getCode() + " notified at txtSearchQuery");
      if (event.getCode().equals(KeyCode.ESCAPE)) {
        filteredData.setPredicate(s-> true);
      }

      if (event.getCode().equals(KeyCode.DOWN))
        lviItems.requestFocus();

      if (event.getCode().equals(KeyCode.ENTER) && ! lviItems.getSelectionModel().isEmpty()) {
        selectedElement = lviItems.getSelectionModel().getSelectedItem();
        getStage().close();
      }

    });
    txtSearchQuery.textProperty().addListener(obs->{
      refreshFilter();
    });

    lviItems.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        selectedElement = lviItems.getSelectionModel().getSelectedItem();
        getStage().close();
      }
    });

    lviItems.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        selectedElement = lviItems.getSelectionModel().getSelectedItem();
        getStage().close();
      }
    });

  }

  public void refreshFilter () {
    String filter = txtSearchQuery.getText();
    if(filter == null || filter.length() == 0) {
      filteredData.setPredicate(s -> true);
    }
    else {
      filteredData.setPredicate(s -> s.getName().toUpperCase().contains(filter.toUpperCase()));
    }

  }

  public void setFilteredSongList(FilteredList<T> filteredData) {
    this.filteredData = filteredData;
    lviItems.setItems(filteredData);
  }

  public void setPreset (final String preset) {
    txtSearchQuery.textProperty().setValue(preset);
    refreshFilter();
  }

  public T getSelectedElement() {
    return selectedElement;
  }


  public void setListViewListCellCallback(Callback<ListView<T>, ListCell<T>> listViewListCellCallback) {
    lviItems.setCellFactory(listViewListCellCallback);

  }
}
