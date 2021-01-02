package org.adonai.settings;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Screen;
import org.adonai.api.Configuration;
import org.adonai.fx.ScreenManager;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
public class ViewConfiguration implements Configuration {

  

  @Override public String getMaskFilename() {
    return "settings_view.fxml";
  }
}
