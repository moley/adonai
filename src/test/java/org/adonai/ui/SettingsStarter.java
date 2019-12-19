package org.adonai.ui;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.model.ConfigurationService;
import org.adonai.ui.screens.ScreenManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsStarter extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(SettingsStarter.class);


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

    Scene scene = new Scene(root, Consts.getDefaultWidth(), Consts.getDefaultHeight());
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
          LOGGER.info(FileUtils.readFileToString(configurationService.getConfigFile(), "UTF-8") );

        } catch (IOException e) {
          throw new IllegalStateException(e);
        }

      }
    });

  }

}