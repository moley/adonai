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
    ScopeItem scopeItemRoot = new ScopeItem(applicationEnvironment.getCurrentTenant());
    rootItem.setValue(scopeItemRoot);
    SongBook currentSongBook = applicationEnvironment.getCurrentSongBook();
    ScopeItem scopeItemSongBook = applicationEnvironment.getScopeItem(currentSongBook);
    TreeItem treeItemSongBook = new TreeItem(scopeItemSongBook, scopeItemSongBook.getIcon());
    for (Song song : scopeItemSongBook.getSongBook().getSongs()) {
      ScopeItem scopeItem = new ScopeItem(scopeItemSongBook, song);
      TreeItem treeItemSong = new TreeItem(scopeItem, scopeItem.getIcon());
      treeItemSongBook.getChildren().add(treeItemSong);
    }
    rootItem.getChildren().add(treeItemSongBook);

    for (Session nextSession: applicationEnvironment.getCurrentConfiguration().getSessions()) {
      ScopeItem scopeItemSession = applicationEnvironment.getScopeItem(nextSession);
      TreeItem treeItemSession = new TreeItem(scopeItemSession, scopeItemSession.getIcon());
      for (Integer song : nextSession.getSongs()) {
        Song nextSong = currentSongBook.findSong(song);
        ScopeItem scopeItem = new ScopeItem(scopeItemSession, nextSong);
        TreeItem treeItemSong = new TreeItem(scopeItem, scopeItem.getIcon());
        treeItemSession.getChildren().add(treeItemSong);
      }
      rootItem.getChildren().add(treeItemSession);
    }

    return rootItem;

  }
}
