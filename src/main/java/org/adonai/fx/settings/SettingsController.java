package org.adonai.fx.settings;

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
import org.adonai.api.Configuration;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsController extends AbstractController {

  @FXML private ListView<SettingsItem> lviConfigurationList;

  @FXML private StackPane panConfigurationDetails;

  private HashMap<String, Parent> panes = new HashMap<>();

  ObservableList<SettingsItem> configurations = FXCollections.observableArrayList();

  private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

  /**
   * gets masks readable from classpath
   * @return list of masks, guarented to be readable from classpath
   */
  public List<String> getMasks () {
    List<String> settingsPanes = new ArrayList<>();

    List<Configuration> extensions = getApplicationEnvironment().getExtensions(Configuration.class);
    for (Configuration next : extensions) {
      LOGGER.info("Add configuration mask " + next.getMaskFilename());
      String newResource = "/fxml/" + next.getMaskFilename();
      settingsPanes.add(newResource);
      if (getClass().getResource(newResource) == null) {
        throw new IllegalStateException(newResource + " was not found on classpath");
      }
    }

    return settingsPanes;
  }

  public void setModel(Model model) {
    LOGGER.info("initializing SettingsController");

    lviConfigurationList.setItems(configurations);


    for (String next : getMasks()) {

      FXMLLoader loader = new FXMLLoader(getClass().getResource(next));
      loader.setResources(ResourceBundle.getBundle("languages.adonai"));
      loader.setClassLoader(getClass().getClassLoader());

      Parent root = null;
      try {
        root = loader.load();


        AbstractSettingsController abstractSettingsController = loader.getController();
        abstractSettingsController.setApplicationEnvironment(getApplicationEnvironment());
        abstractSettingsController.setStage(getStage());
        abstractSettingsController.setModel(model);
        LOGGER.info("Set model of settings controller " + abstractSettingsController.getClass().getName());
        if (abstractSettingsController.isVisible()) {

          ResourceBundle resources = loader.getResources();

          String id = next.substring(next.lastIndexOf("/") + 1).replace(".fxml", "");
          String name = resources.getString(id);
          String icon = resources.getString(id + "_icon");

          panes.put(id, root);
          panConfigurationDetails.getChildren().add(root);
          configurations.add(new SettingsItem(id, name, icon));
        }
      } catch (Exception e) {
        throw new IllegalStateException("Error loading mask " + next, e);
      }
    }

    for (Node node : panConfigurationDetails.getChildren()) {
      node.setStyle("-fx-background-color: white");
    }

    panConfigurationDetails.getChildren().get(0).toFront();

    lviConfigurationList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SettingsItem>() {
      @Override public void changed(ObservableValue<? extends SettingsItem> observable, SettingsItem oldValue,
          SettingsItem newValue) {

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
          if (settingsItem.getIcon() != null && !settingsItem.getIcon().trim().isEmpty()) {
            ImageView imageView = Consts.createImageView(settingsItem.getIcon(), Consts.ICON_SIZE_SMALL);
            setGraphic(imageView);
          } else
            setGraphic(null);
          setText(settingsItem.getName());
        }
      }

    });

    lviConfigurationList.getSelectionModel().selectFirst();

  }


}
