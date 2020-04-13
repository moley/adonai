package org.adonai.uitests.pages;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.adonai.model.SongPartType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

public class AddPartPage extends AbstractPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddPartPage.class);


  public AddPartPage(final ApplicationTest applicationTest) {
    super (applicationTest);
  }

  public void search (final String query) {
    LOGGER.info("Search <" + query + ">");
    applicationTest.clickOn(getTxtSearch());
    applicationTest.write(query);
    pressAndRelease(KeyCode.DOWN);
    LOGGER.info("Text found: <" + getTxtSearch().getText() + ">");
    LOGGER.info("Items found: " + getLviTypes().getItems());
    LOGGER.info("Selected item: " + getLviTypes().getSelectionModel().getSelectedItem());
    applicationTest.doubleClickOn(getLviTypes());

    LOGGER.info("Searched " + query);
  }

  public void cancelSearch (final String query) {
    LOGGER.info("Search " + query);
    applicationTest.write(query);
    pressAndRelease(KeyCode.DOWN);
    LOGGER.info("Selected item: " + getLviTypes().getSelectionModel().getSelectedItem());
    pressAndRelease(KeyCode.ESCAPE);

    LOGGER.info("Searched " + query);
  }



  private TextField getTxtSearch () {
    return applicationTest.lookup(nodeWithUserData("addpart.txtSearch")).query();
  }

  private ListView<SongPartType> getLviTypes () {
    return applicationTest.lookup(nodeWithUserData("addpart.lviTypes")).query();
  }





}
