package org.adonai.settings;

import org.adonai.api.Configuration;

public class ExportConfiguration implements Configuration {
  @Override public String getMaskFilename() {
    return "settings_export.fxml";
  }
}
