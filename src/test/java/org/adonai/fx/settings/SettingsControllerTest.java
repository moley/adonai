package org.adonai.fx.settings;

import org.adonai.ApplicationEnvironment;
import org.junit.Assert;
import org.junit.Test;

public class SettingsControllerTest {

  @Test
  public void getMasks() {
    ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment();
    SettingsController settingsController = new SettingsController();
    settingsController.setApplicationEnvironment(applicationEnvironment);
    Assert.assertNotNull("getMasks return null", settingsController.getMasks());


  }
}
