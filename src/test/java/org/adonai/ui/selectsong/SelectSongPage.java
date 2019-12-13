package org.adonai.ui.selectsong;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.adonai.model.Song;
import org.adonai.ui.AbstractPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

public class SelectSongPage extends AbstractPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(SelectSongPage.class);

  ApplicationTest applicationTest;


  public SelectSongPage(final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }

  public void search (final String query) {
    applicationTest.write(query);
    applicationTest.type(KeyCode.DOWN);
    applicationTest.type(KeyCode.ENTER);
  }



  private TextField getTxtSearch () {
    return applicationTest.lookup(nodeWithUserData("select.txtSearchQuery")).query();
  }

  private ListView<Song> getLviSelectedSongs () {
    return applicationTest.lookup(nodeWithUserData("select.lviSelectItems")).query();
  }





}
