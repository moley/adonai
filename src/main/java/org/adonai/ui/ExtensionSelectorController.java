package org.adonai.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.Extension;
import org.adonai.ExtensionIndex;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 15.09.16.
 */
public class ExtensionSelectorController {

  @FXML
  ListView<Extension> lviExtensions;

  @FXML
  Button btnSelect;

  @FXML
  Button btnCancel;

  @FXML
  TextField txtSearch;

  ConfigurationService configurationService = new ConfigurationService();

  Configuration configuration;

  ExtensionIndex extensionIndex;

  ExtensionType currentType = ExtensionType.SONG;

  private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionSelectorController.class);



  private void refreshListView(final String filter) {
    LOGGER.info("refreshListView (" + filter + ")");
    if (extensionIndex != null) {
      lviExtensions.setItems(FXCollections.observableArrayList(extensionIndex.getFiles(currentType, filter)));
      if (lviExtensions.getItems().size() == 1) {
        lviExtensions.getSelectionModel().selectFirst();
        LOGGER.info("Selecting first (" + lviExtensions.getItems().get(0));
      }
    }
  }

  @FXML
  public void initialize() {
    configuration = configurationService.get();

    txtSearch.setOnKeyTyped(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
        LOGGER.info("handle KeyReleased on txtSearch " + event.getCode());
        refreshListView(txtSearch.getText());
        if (event.getCode().equals(KeyCode.DOWN)) {
          lviExtensions.requestFocus();
          lviExtensions.getSelectionModel().selectFirst();
          event.consume();
        }
      }
    });

    lviExtensions.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
          event.consume();
          close();
        }
      }
    });


    btnSelect.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        event.consume();
        close();
      }
    });

    btnCancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        event.consume();
        lviExtensions.getSelectionModel().clearSelection();
        close();
      }
    });


  }

  public void close() {
    Stage stage = (Stage) btnSelect.getScene().getWindow();
    stage.close();
  }

  public File getSelectedExtension() {
    LOGGER.info("getSelectedExtension " + lviExtensions.getSelectionModel().getSelectedItem());
    return lviExtensions.getSelectionModel().getSelectedItem() != null ? lviExtensions.getSelectionModel().getSelectedItem().getFile(): null;
  }

  public void init(ExtensionType type) {
    currentType = type;
    extensionIndex  = new ExtensionIndex(configuration.getExtensionPaths());

    refreshListView(txtSearch.getText());


  }
}
