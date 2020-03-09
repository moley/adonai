package org.adonai.uitests.pages;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.osgi.service.TestFx;
import org.testfx.util.WaitForAsyncUtils;

public class SelectAdditionalPage extends AbstractPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainMaskPage.class);



  public SelectAdditionalPage(final ApplicationTest applicationTest) {
    super (applicationTest);
  }

  public void select (final String name) {
    LOGGER.info("select additional " + name);
    applicationTest.type(KeyCode.A);
    pressAndRelease(KeyCode.DOWN);
    LOGGER.info("Available additionals: " + getLviExtensions().getItems());
    LOGGER.info("Visible: " + getLviExtensions().isVisible());
    applicationTest.clickOn(getLviExtensions());
    LOGGER.info("LviExtensions focused: " + getLviExtensions().isFocused());
    applicationTest.clickOn(getBtnSelect());

    //waitUntilWindowsVisible(1);
  }

  private Button getBtnSelect () {
    return applicationTest.lookup(nodeWithUserData("extensionselector.btnSelect")).query();
  }

  private Button getBtnCancel () {
    return applicationTest.lookup(nodeWithUserData("extensionselector.btnCancel")).query();
  }

  private TextField getTxtSearch () {
    return applicationTest.lookup(nodeWithUserData("extensionselector.txtSearch")).query();
  }

  private ListView getLviExtensions () {
    return applicationTest.lookup(nodeWithUserData("extensionselector.lviExtensions")).query();
  }

}