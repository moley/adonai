package org.adonai.fx.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Screen;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.renderer.ScreenRenderer;
import org.adonai.fx.renderer.ScreenStringConverter;
import org.adonai.model.Model;
import org.adonai.settings.ViewConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsViewConfigurationsController extends AbstractSettingsController{

  private ScreenManager screenManager = new ScreenManager();

  private Logger log = LoggerFactory.getLogger(ViewConfiguration.class);

  @FXML
  private ComboBox<Screen> cboAdminScreen;

  public void setModel (final Model model) {
    super.setModel(model);
    log.info("initialize");
    cboAdminScreen.setCellFactory(cell-> new ScreenRenderer());
    cboAdminScreen.setConverter(new ScreenStringConverter());
    cboAdminScreen.setItems(FXCollections.observableArrayList(screenManager.getAllScreens()));

    cboAdminScreen.getSelectionModel().select(getApplicationEnvironment().getAdminScreen());

    cboAdminScreen.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Screen>() {
      @Override public void changed(ObservableValue<? extends Screen> observable, Screen oldValue, Screen newValue) {
        getApplicationEnvironment().setAdminScreen(newValue);
        screenManager.layoutOnScreen(getApplicationEnvironment().getMainStage(), newValue);
        screenManager.layoutOnScreen(getStage(), newValue);
      }
    });


  }

  public boolean isVisible () {
    return screenManager.getAllScreens().size() > 1;
  }




}
