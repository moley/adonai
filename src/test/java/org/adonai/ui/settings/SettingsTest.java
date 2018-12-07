package org.adonai.ui.settings;

import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.junit.Test;

public class SettingsTest extends AbstractAdonaiUiTest {

  private SettingsMaskPage settingsMaskPage;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    settingsMaskPage = new SettingsMaskPage(stage, this);
  }

  @Test
  public void cloneDefault () throws InterruptedException {
    Thread.sleep(1000);

  }

}
