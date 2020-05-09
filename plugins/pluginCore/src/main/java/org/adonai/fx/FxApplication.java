package org.adonai.fx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.main.MainController;
import org.pf4j.DefaultPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxApplication extends Application {

  private static final Logger log = LoggerFactory.getLogger(FxApplication.class);

  @Override public void start(Stage primaryStage) throws Exception {

    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(new DefaultPluginManager());

    MaskLoader maskLoader = new MaskLoader();
    Mask<MainController> mask = maskLoader.load(applicationEnvironment, "main");
    MainController mainController = mask.getController();
    mainController.setApplicationEnvironment(applicationEnvironment);

    primaryStage.setScene(mask.getScene());

    ScreenManager screenManager = new ScreenManager();
    primaryStage.setX(screenManager.getPrimary().getBounds().getMinX() + 5);
    primaryStage.setY(screenManager.getPrimary().getBounds().getMinY() + 5);
    primaryStage.setWidth(screenManager.getPrimary().getBounds().getWidth() - 10);
    primaryStage.setHeight(screenManager.getPrimary().getBounds().getHeight() - 10);

    log.info("Primary stage Bounds: " + primaryStage.getWidth() + "-" + primaryStage.getHeight());
    primaryStage.toFront();
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(FxApplication.class, args);
  }

}
