package org.adonai.fx.settings;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Screen;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.renderer.ScreenRenderer;
import org.adonai.fx.renderer.ScreenStringConverter;
import org.adonai.settings.ViewConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsViewConfigurationsController extends AbstractSettingsController{

  private ScreenManager screenManager = new ScreenManager();

  private Logger log = LoggerFactory.getLogger(ViewConfiguration.class);

  @FXML
  private ComboBox<Screen> cboAdminScreen;

  @FXML
  public void initialize () {
    log.info("initialize");
    cboAdminScreen.setCellFactory(cell-> new ScreenRenderer());
    cboAdminScreen.setConverter(new ScreenStringConverter());
    cboAdminScreen.setItems(FXCollections.observableArrayList(screenManager.getAllScreens()));


  }


}
