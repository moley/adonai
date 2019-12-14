package org.adonai.ui.mainpage;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import org.adonai.ui.AbstractPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

public class SelectAdditionalPage extends AbstractPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainMaskPage.class);

  ApplicationTest applicationTest;


  public SelectAdditionalPage(final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }

  public void select (final String name) {
    applicationTest.write(name);
    pressAndRelease(applicationTest, KeyCode.DOWN);
    applicationTest.clickOn(getBtnSelect()).sleep(1000);
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
    return applicationTest.lookup(nodeWithUserData("  extensionselector.lviExtensions")).query();
  }

}