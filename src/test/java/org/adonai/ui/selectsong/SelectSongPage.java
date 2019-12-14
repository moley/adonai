package org.adonai.ui.selectsong;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.adonai.model.Song;
import org.adonai.ui.AbstractPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class SelectSongPage extends AbstractPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(SelectSongPage.class);

  ApplicationTest applicationTest;


  public SelectSongPage(final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }

  public void search (final String query) {
    LOGGER.info("Search " + query);
    applicationTest.clickOn(getTxtSearch(), MouseButton.PRIMARY);
    applicationTest.write(query);
    pressAndRelease(applicationTest, KeyCode.DOWN);
    LOGGER.info("Selected item: " + getLviSelectedSongs().getSelectionModel().getSelectedItem());
    applicationTest.doubleClickOn(getLviSelectedSongs(), MouseButton.PRIMARY);

    LOGGER.info("Searched " + query);
  }



  private TextField getTxtSearch () {
    return applicationTest.lookup(nodeWithUserData("select.txtSearchQuery")).query();
  }

  private ListView<Song> getLviSelectedSongs () {
    return applicationTest.lookup(nodeWithUserData("select.lviSelectItems")).query();
  }





}
