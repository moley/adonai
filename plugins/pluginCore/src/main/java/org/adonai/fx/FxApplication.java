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
    applicationEnvironment.setMainStage(primaryStage);

    primaryStage.setScene(mask.getScene());

    ScreenManager screenManager = new ScreenManager();
    primaryStage.initStyle(StageStyle.UNDECORATED);
    screenManager.layoutOnScreen(primaryStage, applicationEnvironment.getAdminScreen());
    log.info("Primary stage Bounds: " + primaryStage.getWidth() + "-" + primaryStage.getHeight());
    primaryStage.toFront();
    primaryStage.show();


  }


}
