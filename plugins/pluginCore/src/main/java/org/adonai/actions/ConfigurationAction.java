package org.adonai.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.settings.SettingsController;
import org.pf4j.Extension;

@Extension(ordinal = 1)
public class ConfigurationAction implements MainAction {

  private ScreenManager screenManager = new ScreenManager();


  @Override public String getIconname() {
    return null;
  }

  @Override public String getDisplayName() {
    return "Configuration";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        MaskLoader<SettingsController> maskLoader = new MaskLoader();
        Mask<SettingsController> settingsMask = maskLoader.loadWithStage( "settings");
        screenManager.layoutOnScreen(settingsMask.getStage(), applicationEnvironment.getAdminScreen());
        settingsMask.getController().setStage(settingsMask.getStage());
        settingsMask.getController().setApplicationEnvironment(applicationEnvironment);
        settingsMask.getController().setModel(applicationEnvironment.getModel());
        settingsMask.show();


      }
    };
  }
}
