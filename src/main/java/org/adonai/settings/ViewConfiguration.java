package org.adonai.settings;

import org.adonai.api.Configuration;

public class ViewConfiguration implements Configuration {

  

  @Override public String getMaskFilename() {
    return "settings_view.fxml";
  }
}
