package org.adonai.fx;

import java.io.File;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.firstStart.FirstStartController;
import org.adonai.fx.main.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxApplication extends Application {

  private MaskLoader maskLoader = new MaskLoader();


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

  @Override public void start(Stage stage) throws Exception {

    File adonaiHome = Consts.getAdonaiHome();

    if (! adonaiHome.exists()) {
      log.info("Adonai home path '" + adonaiHome.getAbsolutePath() + "' not available, seems to be the first start.");
      Stage firstStartStage = new Stage(StageStyle.UNDECORATED);

      Mask<FirstStartController> mask = maskLoader.loadWithStage("firstStart");
      FirstStartController firstStartController = mask.getController();
      firstStartController.setStage(firstStartStage);
      firstStartStage.setScene(mask.getScene());
      ScreenManager screenManager = new ScreenManager();
      firstStartStage.initStyle(StageStyle.UNDECORATED);
      screenManager.layoutOnScreen(firstStartStage, 200, screenManager.getPrimary());
      firstStartStage.toFront();
      firstStartStage.showAndWait();
    }
    else {
      log.info("Adonai home path '" + adonaiHome.getAbsolutePath() + "' is available");
    }

    showMainMask(stage);

  }

  private void showMainMask (Stage primaryStage) {
    Mask<MainController> mask = maskLoader.loadWithStage("main");
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
