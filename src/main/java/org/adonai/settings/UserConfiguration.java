package org.adonai.settings;

import org.adonai.api.Configuration;

public class UserConfiguration implements Configuration {
  @Override public String getMaskFilename() {
    return "settings_users.fxml";
  }
}
