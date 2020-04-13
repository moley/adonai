package org.adonai.settings;

import org.adonai.api.Configuration;
import org.pf4j.Extension;

@Extension(ordinal=1)
public class ExportConfiguration implements Configuration {
  @Override public String getMaskFilename() {
    return "settings_export.fxml";
  }
}
