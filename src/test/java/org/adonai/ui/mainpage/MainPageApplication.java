package org.adonai.ui.mainpage;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.player.Mp3Player;
import org.adonai.screens.ScreenManager;
import org.adonai.ui.Consts;

public class MainPageApplication extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/mainpage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT, false);

    scene.getStylesheets().add("/adonai.css");

    for (String next: Font.getFamilies()) {
      System.out.println ("Font Family " + next  + " found");
    }

    ScreenManager screenManager = new ScreenManager();
    screenManager.layoutOnScreen(primaryStage);

    primaryStage.setScene(scene);
    primaryStage.initStyle(StageStyle.UNDECORATED);
    //primaryStage.setFullScreen(true);
    //primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    //primaryStage.setFullScreenExitHint("");

    primaryStage.show();




  }

  public static void main(String[] args) {
    launch(args);
  }
}
