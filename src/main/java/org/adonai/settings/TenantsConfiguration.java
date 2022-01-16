package org.adonai.settings;

import org.adonai.api.Configuration;

public class TenantsConfiguration implements Configuration {
  @Override public String getMaskFilename() {
    return "settings_tenants.fxml";
  }
}
