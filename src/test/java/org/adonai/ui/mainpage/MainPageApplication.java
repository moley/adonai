package org.adonai.ui.mainpage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.screens.ScreenManager;
import org.adonai.ui.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPageApplication extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainPageApplication.class);

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/mainpage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.getDefaultWidth(), Consts.getDefaultHeight(), false);

    scene.getStylesheets().add("/adonai.css");

    if (LOGGER.isDebugEnabled()) {
      for (String next : Font.getFamilies()) {
        LOGGER.debug("Font Family " + next + " found");
      }
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
