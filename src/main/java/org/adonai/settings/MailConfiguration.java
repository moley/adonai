package org.adonai.settings;

import org.adonai.api.Configuration;

public class MailConfiguration implements Configuration {
  @Override public String getMaskFilename() {
    return "settings_mail.fxml";
  }
}
