package org.adonai.uitests;

import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.screens.ScreenManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class ScreenManagerTest extends AbstractAdonaiUiTest {

  @BeforeClass public static void beforeClass() {
    TestUtil.initialize();
  }

  @Override public void start(Stage stage) throws Exception {
    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData(TestUtil.getDefaultTestDataPath(), false);
    super.start(stage);
  }

  @Test public void getPrimary() throws InterruptedException {
    ScreenManager screenManager = new ScreenManager();
    Assert.assertNotNull(screenManager.getPrimary());
  }

  @Test
  public void getExternalOrPrimary () {
    ScreenManager screenManager = new ScreenManager();
    Assert.assertEquals (1600, screenManager.getExternalOrPrimaryScreen().getVisualBounds().getWidth(), 0);
    Assert.assertEquals (1200, screenManager.getExternalOrPrimaryScreen().getVisualBounds().getHeight(), 0);
  }

  @Test
  public void layoutMid () {

    Stage stage = Mockito.mock(Stage.class);
    ScreenManager screenManager = new ScreenManager();
    screenManager.layoutOnScreen(stage);
    Mockito.verify(stage, Mockito.times(1)).setX(0);
    Mockito.verify(stage, Mockito.times(1)).setY(0);
    Mockito.verify(stage, Mockito.times(1)).setWidth(1600);
    Mockito.verify(stage, Mockito.times(1)).setHeight(1200);
  }
}
