package org.adonai.export;

import org.adonai.model.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class DefaultExportConfigurationCreatorTest {

  @Test
  public void create () {

    Configuration configuration = new Configuration();

    DefaultExportConfigurationCreator defaultExportConfigurationCreator = new DefaultExportConfigurationCreator();
    defaultExportConfigurationCreator.createDefaultExportConfigurations(configuration);

    int expectedNumberOfDefaultConfigurations = 3;

    Assert.assertEquals ("Number of default export configurations invalid", expectedNumberOfDefaultConfigurations, configuration.getExportConfigurations().size());

    defaultExportConfigurationCreator.createDefaultExportConfigurations(configuration);

    Assert.assertEquals ("Number of default export configurations invalid", expectedNumberOfDefaultConfigurations, configuration.getExportConfigurations().size());

  }

}
