package org.adonai.settings;

import org.adonai.api.Configuration;
import org.pf4j.Extension;

@Extension(ordinal=1)
public class ViewConfiguration implements Configuration {
  @Override public String getMaskFilename() {
    return "settings_view.fxml";
  }
}
