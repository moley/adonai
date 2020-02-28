package org.adonai.actions;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.adonai.model.Model;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.settings.SettingsController;

public class ConfigurationAction {

  public final static int CONFIGDIALOG_WIDTH = 1400;
  public final static int CONFIGDIALOG_HEIGHT = 950;


  public void openConfigurations (final Double x, final Double y, final Model model, final EventHandler<WindowEvent> onHiding ) {

    MaskLoader<SettingsController> maskLoader = new MaskLoader();
    Mask<SettingsController>settingsMask = maskLoader.load("settings");
    settingsMask.setSize(CONFIGDIALOG_WIDTH, CONFIGDIALOG_HEIGHT);
    settingsMask.setPosition(x, y);
    settingsMask.getController().setModel(model);
    settingsMask.show();

    settingsMask.getStage().setOnHiding(onHiding);


  }
}
