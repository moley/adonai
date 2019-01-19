package org.adonai.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFxApplication extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/mainpage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/adonai.css");
    //primaryStage.setFullScreen(true);
    //primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    //primaryStage.setFullScreenExitHint("");

    primaryStage.setScene(scene);
    primaryStage.show();



  }

}
