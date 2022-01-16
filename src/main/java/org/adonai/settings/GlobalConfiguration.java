package org.adonai.settings;

import org.adonai.api.Configuration;

public class GlobalConfiguration implements Configuration {
  @Override public String getMaskFilename() {
    return "settings_global.fxml";
  }
}
