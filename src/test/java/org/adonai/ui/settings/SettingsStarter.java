package org.adonai.ui.settings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.adonai.screens.ScreenManager;
import org.adonai.ui.Consts;

import java.io.IOException;

public class SettingsStarter extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    ScreenManager screenManager = new ScreenManager();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/adonai/settings.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/adonai.css");
    screenManager.layoutOnScreen(primaryStage);

    primaryStage.setTitle("Settings");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

}