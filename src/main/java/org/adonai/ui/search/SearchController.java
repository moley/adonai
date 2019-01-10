package org.adonai.ui.search;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.logging.Logger;


public class SearchController {

  @FXML
  TextField txtSearchQuery;

  @FXML
  ListView lviSearchResults;

  private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());


  @FXML
  public void initialize() {
    txtSearchQuery.requestFocus();
    txtSearchQuery.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
          LOGGER.info("Searching for " + txtSearchQuery.getText());
        }


      }
    });

  }
}
