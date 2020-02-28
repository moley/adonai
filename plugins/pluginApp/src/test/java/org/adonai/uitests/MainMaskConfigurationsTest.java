package org.adonai.uitests;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.uitests.pages.ConfigurationsPage;
import org.adonai.uitests.pages.MainMaskPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMaskConfigurationsTest extends AbstractAdonaiUiTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(MainMaskConfigurationsTest.class);


  private ConfigurationsPage configurationsPage;

  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    LOGGER.info("start called with stage " + System.identityHashCode(stage));
    super.start(stage);
    MainMaskPage mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();
    configurationsPage = mainMaskPage.configurations();
  }

  @Override
  public void stop () {
    LOGGER.info("stop called");
  }

  @Test
  public void cancel () {
    configurationsPage.pressAndRelease(KeyCode.ESCAPE);
  }




}
