package org.adonai.fx;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.InitStep;
import org.adonai.fx.main.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FxApplication extends Application {

  private final MaskLoader<MainController> maskLoader = new MaskLoader<>();

  private final ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

  private static final Logger log = LoggerFactory.getLogger(FxApplication.class);

  public static void main(String[] args) {
    launch(FxApplication.class, args);
  }

  public ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  @Override public void start(Stage stage) throws Exception {

    for (InitStep nextInitStep : getApplicationEnvironment().getExtensions(InitStep.class)) {
      if (nextInitStep.isExecuted(getApplicationEnvironment())) {
        log.info("Execute initStep " + nextInitStep.getClass().getName());
        nextInitStep.execute(getApplicationEnvironment());
        log.info("Finished initStep " + nextInitStep.getClass().getName());

      } else
        log.info("InitStep " + nextInitStep.getClass().getName() + " is not executed");
    }

    showMainMask(stage);

  }

  private void showMainMask(Stage primaryStage) {
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
