package org.adonai.plugin.publish.fx.initstep;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.InitStep;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.ScreenManager;

public class ChooseTenantInitStep implements InitStep {

  private final MaskLoader<ChooseTenantController> maskLoader = new MaskLoader<>();

  public void execute (ApplicationEnvironment applicationEnvironment) {
    Stage initStepStage = new Stage();
    Mask<ChooseTenantController> mask = maskLoader.loadWithStage("chooseTenant", getClass().getClassLoader());
    ChooseTenantController controller = mask.getController();
    controller.setStage(initStepStage);
    controller.setApplicationEnvironment(applicationEnvironment);
    initStepStage.setScene(mask.getScene());
    ScreenManager screenManager = new ScreenManager();
    initStepStage.initStyle(StageStyle.UNDECORATED);
    screenManager.layoutOnScreen(initStepStage, 200, screenManager.getPrimary());
    initStepStage.toFront();
    initStepStage.setAlwaysOnTop(true);
    initStepStage.showAndWait();
  }

  @Override public boolean isExecuted(ApplicationEnvironment applicationEnvironment) {
    return true;

  }


}
