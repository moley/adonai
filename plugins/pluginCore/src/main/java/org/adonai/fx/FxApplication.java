package org.adonai.fx;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.main.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxApplication extends Application {

  private static final Logger log = LoggerFactory.getLogger(FxApplication.class);

  private static ApplicationEnvironment applicationEnvironment;

  public static void main(String[] args) {
    launch(FxApplication.class, args);
  }

  public static ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  public static void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    FxApplication.applicationEnvironment = applicationEnvironment;
  }

  @Override public void start(Stage primaryStage) throws Exception {

    MaskLoader maskLoader = new MaskLoader();
    Mask<MainController> mask = maskLoader.load("main");
    MainController mainController = mask.getController();
    mainController.setApplicationEnvironment(applicationEnvironment);

    primaryStage.setScene(mask.getScene());

    ScreenManager screenManager = new ScreenManager();
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setX(screenManager.getPrimary().getBounds().getMinX() + 5);
    primaryStage.setY(screenManager.getPrimary().getBounds().getMinY() + 5);
    primaryStage.setWidth(screenManager.getPrimary().getBounds().getWidth() - 10);
    primaryStage.setHeight(screenManager.getPrimary().getBounds().getHeight() - 30);
    primaryStage.setMaxHeight(screenManager.getPrimary().getBounds().getHeight() - 30);

    log.info("Primary stage Bounds: " + primaryStage.getWidth() + "-" + primaryStage.getHeight());
    primaryStage.toFront();
    primaryStage.show();


  }


}
