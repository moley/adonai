package org.adonai.ui.settings;

import org.adonai.AdonaiProperties;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;

public class AbstractSettingsController {

  private ConfigurationService configurationService = new ConfigurationService();

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  public Configuration getConfiguration() {
    return configurationService.get(adonaiProperties.getCurrentTenant());
  }

}
