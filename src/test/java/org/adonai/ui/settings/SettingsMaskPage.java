package org.adonai.ui.settings;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.adonai.ui.Consts;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ResourceBundle;

public class SettingsMaskPage {

  Scene  scene;
  Stage stage;
  ApplicationTest applicationTest;

  public SettingsMaskPage (final Stage stage, final ApplicationTest applicationTest) throws IOException {
    this.stage = stage;
    this.applicationTest = applicationTest;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/settings.fxml"));
    loader.setResources(ResourceBundle.getBundle("languages.adonai"));
    Parent root = loader.load();
    scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/adonai.css");
    stage.setScene(scene);
    stage.show();
  }
}
