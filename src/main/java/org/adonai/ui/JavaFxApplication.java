package org.adonai.ui;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.ui.screens.ScreenManager;
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

    Scene scene = new Scene(root, Consts.getDefaultWidth(), Consts.getDefaultHeight());
    scene.getStylesheets().add("/adonai.css");
    primaryStage.initStyle(StageStyle.UNDECORATED);
    ScreenManager screenManager = new ScreenManager();
    Screen primaryScreen = screenManager.getPrimary();
    primaryStage.setX(primaryScreen.getVisualBounds().getMinX());
    primaryStage.setY(primaryScreen.getVisualBounds().getMinY());
    primaryStage.setWidth(primaryScreen.getVisualBounds().getWidth());
    primaryStage.setHeight(primaryScreen.getVisualBounds().getHeight());

    primaryStage.setScene(scene);
    primaryStage.show();



  }

}
