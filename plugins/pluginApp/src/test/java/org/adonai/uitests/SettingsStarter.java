package org.adonai.uitests;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.AdonaiProperties;
import org.adonai.model.ConfigurationService;
import org.adonai.ui.Consts;
import org.adonai.ui.UiUtils;
import org.adonai.ui.screens.ScreenManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsStarter extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(SettingsStarter.class);

  private AdonaiProperties adonaiProperties = new AdonaiProperties();


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
    UiUtils.applyCss(scene);
    screenManager.layoutOnScreen(primaryStage);

    primaryStage.setTitle("Settings");
    primaryStage.setScene(scene);
    primaryStage.show();

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        configurationService.save(adonaiProperties.getCurrentTenant());
        try {
          LOGGER.info(FileUtils.readFileToString(configurationService.getConfigFile(adonaiProperties.getCurrentTenant()), "UTF-8") );

        } catch (IOException e) {
          throw new IllegalStateException(e);
        }

      }
    });

  }

}