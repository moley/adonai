package org.adonai.export;

import org.adonai.ApplicationEnvironment;
import org.adonai.model.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class DefaultExportConfigurationCreatorTest {

  @Test
  public void create () {

    Configuration configuration = new Configuration();

    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();

    DefaultExportConfigurationCreator defaultExportConfigurationCreator = new DefaultExportConfigurationCreator();
    defaultExportConfigurationCreator.createDefaultExportConfigurations(applicationEnvironment, configuration);

    int expectedNumberOfDefaultConfigurations = 3;

    Assert.assertEquals ("Number of default export configurations invalid ("+ configuration.getExportConfigurations() + ")", expectedNumberOfDefaultConfigurations, configuration.getExportConfigurations().size());

    defaultExportConfigurationCreator.createDefaultExportConfigurations(applicationEnvironment, configuration);

    Assert.assertEquals ("Number of default export configurations invalid ("+ configuration.getExportConfigurations() + ")", expectedNumberOfDefaultConfigurations, configuration.getExportConfigurations().size());

    applicationEnvironment.dispose();

  }

}