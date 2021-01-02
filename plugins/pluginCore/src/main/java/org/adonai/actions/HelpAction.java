package org.adonai.actions;

import javafx.stage.Stage;
import org.adonai.fx.HelpController;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.main.MainController;

import static org.adonai.fx.FxApplication.getApplicationEnvironment;

public class HelpAction {

  private ScreenManager screenManager = new ScreenManager();


  public void show (MainController mainController) {
    MaskLoader<HelpController> maskLoader = new MaskLoader<>();
    Mask<HelpController> mask = maskLoader.load( "help");
    HelpController helpController = mask.getController();
    helpController.setMainController(mainController);
    helpController.render ();
    Stage stage = mask.getStage();
    screenManager.layoutOnScreen(stage, 100, mainController.getApplicationEnvironment().getAdminScreen());

    stage.show();
  }
}
