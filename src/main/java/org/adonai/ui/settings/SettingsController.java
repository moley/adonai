package org.adonai.ui.settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class SettingsController {

  @FXML
  private ListView lviConfigurationList;

  @FXML
  private StackPane panConfigurationDetails;

  private HashMap<String, Parent> panes = new HashMap<String, Parent>();

  ObservableList configurations = FXCollections.observableArrayList();



  @FXML
  public void initialize() throws IOException {
    System.out.println ("initializing SettingsController");



    lviConfigurationList.setItems(configurations);



    Collection<String> settingsPanes = new ArrayList<String>();

    settingsPanes.add("/org/adonai/settings_configurations.fxml");
    settingsPanes.add("/org/adonai/settings_export.fxml");


    for (String next: settingsPanes) {

      FXMLLoader loader = new FXMLLoader(getClass().getResource(next));
      Parent root = loader.load();

      String id = next.substring(next.lastIndexOf("/") + 1).replace(".fxml", "");

      panes.put(id, root);
      panConfigurationDetails.getChildren().add(root);
      configurations.add(id);
    }

    panConfigurationDetails.getChildren().get(0).toFront();





  }
}
