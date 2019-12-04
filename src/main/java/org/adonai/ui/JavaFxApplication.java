package org.adonai.ui;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaFxApplication extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(JavaFxApplication.class);

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    LOGGER.info("Starting adonai in folder " + new File("").getAbsolutePath());

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/mainpage.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/adonai.css");
    //primaryStage.setMaximized(true);
    //primaryStage.setFullScreen(true);
    //primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    //primaryStage.setFullScreenExitHint("");

    primaryStage.setScene(scene);
    primaryStage.show();



  }

}
