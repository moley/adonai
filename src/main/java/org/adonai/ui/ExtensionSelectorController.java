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


  private void refreshListView(final String filter) {
    if (extensionIndex != null) {
      lviExtensions.setItems(FXCollections.observableArrayList(extensionIndex.getFiles(currentType, filter)));
    }
  }

  @FXML
  public void initialize() {
    configuration = configurationService.get();

    txtSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
        refreshListView(txtSearch.getText());
        if (event.getCode().equals(KeyCode.DOWN))
          lviExtensions.requestFocus();
      }
    });

    lviExtensions.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
          close();
      }
    });

    refreshListView(txtSearch.getText());

    btnSelect.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        close();
      }
    });

    btnCancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        lviExtensions.getSelectionModel().clearSelection();
        close();
      }
    });


  }

  public void close() {
    Stage stage = (Stage) btnSelect.getScene().getWindow();
    stage.fireEvent(
      new WindowEvent(
        stage,
        WindowEvent.WINDOW_CLOSE_REQUEST
      )
    );
  }

  public File getSelectedExtension() {
    return lviExtensions.getSelectionModel().getSelectedItem() != null ? lviExtensions.getSelectionModel().getSelectedItem().getFile(): null;
  }

  public void init(ExtensionType type) {
    currentType = type;
    extensionIndex  = new ExtensionIndex(configuration.getExtensionPaths());

  }
}
