package org.adonai.ui.search;

import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.adonai.model.NamedElement;


public class SearchController {

  @FXML
  TextField txtSearchQuery;

  private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

  private FilteredList<? extends NamedElement> filteredData;


  @FXML
  public void initialize() {
    txtSearchQuery.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
          filteredData.setPredicate(s-> true);
        }
      }
    });
    txtSearchQuery.textProperty().addListener(obs->{
      String filter = txtSearchQuery.getText();
      if(filter == null || filter.length() == 0) {
        filteredData.setPredicate(s -> true);
      }
      else {
        filteredData.setPredicate(s -> s.getName().toUpperCase().contains(filter.toUpperCase()));
      }
    });

  }

  public void setFilteredSongList(FilteredList<? extends NamedElement> filteredData) {
    this.filteredData = filteredData;
  }

  public void setPreset (final String preset) {
    txtSearchQuery.textProperty().setValue(preset);

  }
}
