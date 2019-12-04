package org.adonai.ui.settings;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.ui.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;

public class SettingsTest extends AbstractAdonaiUiTest {

  private SettingsMaskPage settingsMaskPage;


  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    settingsMaskPage = new SettingsMaskPage(stage, this);
  }

  @Test
  public void cloneDefault () throws InterruptedException {
     //TODO
  }

}
