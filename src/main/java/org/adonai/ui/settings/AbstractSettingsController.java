package org.adonai.ui.settings;

import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;

public class AbstractSettingsController {

  private ConfigurationService configurationService = new ConfigurationService();

  public Configuration getConfiguration() {
    return configurationService.get();
  }

}
