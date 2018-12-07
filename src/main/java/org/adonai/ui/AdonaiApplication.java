package org.adonai.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.adonai.screens.ScreenManager;

import java.io.IOException;

/**
 * Created by OleyMa on 02.09.16.
 */
public class AdonaiApplication extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    ScreenManager screenManager = new ScreenManager();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/main.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/adonai.css");
    screenManager.layoutOnScreen(primaryStage);

    primaryStage.setTitle("Adonai");
    primaryStage.setScene(scene);
    primaryStage.show();



  }
}
