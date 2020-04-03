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
import org.adonai.ApplicationEnvironment;
import org.adonai.ui.mainpage.MainPageController;
import org.adonai.ui.screens.ScreenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaFxApplication extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(JavaFxApplication.class);

  private static ApplicationEnvironment applicationEnvironment;

  public static void main(String[] args) {
    launch(JavaFxApplication.class, args);
  }

  public static ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  public static void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    JavaFxApplication.applicationEnvironment = applicationEnvironment;
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    LOGGER.info("Starting adonai in folder " + new File("").getAbsolutePath());


    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/mainpage.fxml"));
    loader.setClassLoader(getClass().getClassLoader());
    Parent root = loader.load();

    MainPageController mainPageController = loader.getController();
    mainPageController.setApplicationEnvironment(applicationEnvironment);

    Scene scene = new Scene(root, Consts.getDefaultWidth(), Consts.getDefaultHeight());
    UiUtils.applyCss(scene);
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
