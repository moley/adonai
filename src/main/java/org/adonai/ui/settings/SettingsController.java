package org.adonai.ui.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.adonai.ui.Consts;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsController {

  @FXML
  private ListView<SettingsItem> lviConfigurationList;

  @FXML
  private StackPane panConfigurationDetails;

  private HashMap<String, Parent> panes = new HashMap<String, Parent>();

  ObservableList<SettingsItem> configurations = FXCollections.observableArrayList();

  private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);







  @FXML
  public void initialize() throws IOException {
    LOGGER.info("initializing SettingsController");



    lviConfigurationList.setItems(configurations);


    Collection<String> settingsPanes = new ArrayList<String>();

    List<String> files = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream("screens/"), Charsets.toCharset("UTF-8"));
    LOGGER.info("Found " + files.size() + " settings screens");
    for (String next: files) {
      if (next.startsWith("settings_")) {
        LOGGER.info("adding settings page " + next);
        settingsPanes.add("/screens/" + next);
      }
    }

    for (String next: settingsPanes) {

      FXMLLoader loader = new FXMLLoader(getClass().getResource(next));
      loader.setResources(ResourceBundle.getBundle("languages.adonai"));
      Parent root = loader.load();
      ResourceBundle resources = loader.getResources();


      String id = next.substring(next.lastIndexOf("/") + 1).replace(".fxml", "");
      String name = resources.getString(id);
      String icon = resources.getString(id + "_icon");

      panes.put(id, root);
      panConfigurationDetails.getChildren().add(root);
      configurations.add(new SettingsItem(id, name, icon));
    }

    for (Node node: panConfigurationDetails.getChildren()) {
      node.setStyle("-fx-background-color: white");
    }

    panConfigurationDetails.getChildren().get(0).toFront();

    lviConfigurationList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SettingsItem>() {
      @Override
      public void changed(ObservableValue<? extends SettingsItem> observable, SettingsItem oldValue, SettingsItem newValue) {

        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Changed from " + oldValue + " to " + newValue);

        if (newValue != null) {
          Parent parent = panes.get(newValue.getId());
          parent.toFront();
        }


      }
    });

    lviConfigurationList.setCellFactory(listView -> new ListCell<SettingsItem>() {
      public void updateItem(SettingsItem settingsItem, boolean empty) {
        super.updateItem(settingsItem, empty);
        if (empty) {
          setText(null);
          setGraphic(null);
        } else {
          if (settingsItem.getIcon() != null && ! settingsItem.getIcon().trim().isEmpty()) {
            ImageView imageView = Consts.createImageView(settingsItem.getIcon(), Consts.ICON_SIZE_LARGE);
            setGraphic(imageView);
          }
          else
            setGraphic(null);
          setText(settingsItem.getName());
        }
      }

    });

    lviConfigurationList.getSelectionModel().selectFirst();








  }
}
