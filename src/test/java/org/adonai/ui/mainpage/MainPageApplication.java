package org.adonai.ui.mainpage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.adonai.ui.Consts;

public class MainPageApplication extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/mainpage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT, false);

    scene.getStylesheets().add("/adonai.css");

    primaryStage.setScene(scene);

    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    primaryStage.setFullScreenExitHint("");

    primaryStage.show();




  }

  public static void main(String[] args) {
    launch(args);
  }
}
