package org.adonai.fx.scope;

import javafx.scene.control.TreeItem;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.main.ScopeItem;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;

public class ScopeTreeProvider {

  public TreeItem getTree (final ApplicationEnvironment applicationEnvironment) {
    TreeItem rootItem = new TreeItem();
    SongBook currentSongBook = applicationEnvironment.getCurrentSongBook();
    ScopeItem scopeItemSongBook = applicationEnvironment.getScopeItem(currentSongBook);
    TreeItem treeItemSongBook = new TreeItem(scopeItemSongBook);
    for (Song song : scopeItemSongBook.getSongBook().getSongs()) {
      TreeItem treeItemSong = new TreeItem(new ScopeItem(scopeItemSongBook, song));
      treeItemSongBook.getChildren().add(treeItemSong);
    }
    rootItem.getChildren().add(treeItemSongBook);

    for (Session nextSession: applicationEnvironment.getCurrentConfiguration().getSessions()) {
      ScopeItem scopeItemSession = applicationEnvironment.getScopeItem(nextSession);
      TreeItem treeItemSession = new TreeItem(scopeItemSession);
      for (Integer song : nextSession.getSongs()) {
        Song nextSong = currentSongBook.findSong(song);
        TreeItem treeItemSong = new TreeItem(new ScopeItem(scopeItemSession, nextSong));
        treeItemSession.getChildren().add(treeItemSong);
      }
      rootItem.getChildren().add(treeItemSession);
    }

    return rootItem;

  }
}
