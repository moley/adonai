package org.adonai.ui.settings;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.screens.ScreenManager;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class SettingsStarter extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    System.setProperty("user.home", "build/testcases/" + getClass().getSimpleName());
    ScreenManager screenManager = new ScreenManager();
    ConfigurationService configurationService = new ConfigurationService();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/settings.fxml"));
    loader.setResources(ResourceBundle.getBundle("languages.adonai"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/adonai.css");
    screenManager.layoutOnScreen(primaryStage);

    primaryStage.setTitle("Settings");
    primaryStage.setScene(scene);
    primaryStage.show();

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        configurationService.save();
        try {
          System.out.println (FileUtils.readFileToString(configurationService.getConfigFile(), "UTF-8") );

        } catch (IOException e) {
          throw new IllegalStateException(e);
        }

      }
    });

  }

}