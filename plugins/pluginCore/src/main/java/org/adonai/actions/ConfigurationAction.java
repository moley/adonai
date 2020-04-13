package org.adonai.actions;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.adonai.ApplicationEnvironment;
import org.adonai.model.Model;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.ScreenManager;
import org.adonai.ui.settings.SettingsController;

public class ConfigurationAction {

  public final static int CONFIGDIALOG_WIDTH = 1400;
  public final static int CONFIGDIALOG_HEIGHT = 950;

  private ScreenManager screenManager = new ScreenManager();

  private final ApplicationEnvironment applicationEnvironment;

  public ConfigurationAction (final ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }


  public void openConfigurations (final Model model, final EventHandler<WindowEvent> onHiding ) {

    MaskLoader<SettingsController> maskLoader = new MaskLoader();
    Mask<SettingsController>settingsMask = maskLoader.load(applicationEnvironment, "settings");
    settingsMask.setSize(CONFIGDIALOG_WIDTH, CONFIGDIALOG_HEIGHT);
    screenManager.layoutOnScreen(settingsMask.getStage(), 40);
    settingsMask.getController().setModel(model);
    settingsMask.show();

    settingsMask.getStage().setOnHiding(onHiding);


  }
}
