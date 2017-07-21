package org.adonai.ui.selectsong;

import javafx.scene.control.TableView;
import org.adonai.model.Song;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SelectSongPage {

  private ApplicationTest applicationTest;

  public SelectSongPage (final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }

  public boolean isInSongSelection () {
    return getSongTable() != null;
  }

  private TableView<Song> getSongTable () {
    NodeQuery tabSongs = applicationTest.lookup("#tabSongs");
    TableView<Song> tabSongsTable = tabSongs.query();
    return tabSongsTable;
  }

}
