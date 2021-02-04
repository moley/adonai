package org.adonai.fx.scope;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.Consts;
import org.adonai.fx.main.ScopeItem;
import org.adonai.model.AdditionalType;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.TenantModel;

public class ScopeTreeProvider {

  public TreeItem getTree (final ApplicationEnvironment applicationEnvironment) {
    TreeItem rootItem = new TreeItem();
    TenantModel currentTenantModel = applicationEnvironment.getModel().getCurrentTenantModel();
    ScopeItem scopeItemRoot = new ScopeItem(currentTenantModel);
    rootItem.setValue(scopeItemRoot);
    SongBook currentSongBook = applicationEnvironment.getCurrentSongBook();
    ScopeItem scopeItemSongBook = applicationEnvironment.getScopeItem(currentSongBook);
    TreeItem treeItemSongBook = new TreeItem(scopeItemSongBook, scopeItemSongBook.getIcon());
    for (Song song : scopeItemSongBook.getSongBook().getSongs()) {
      ScopeItem scopeItem = new ScopeItem(scopeItemSongBook, song);

      Label lblId = new Label(song.getId().toString());
      final int WIDTH_ID = 50;
      lblId.setMaxWidth(WIDTH_ID);
      lblId.setMinWidth(WIDTH_ID);

      Label lblName = new Label(song.getName());
      final int WIDTH_NAME = 400;
      lblName.setMaxWidth(WIDTH_NAME);
      lblName.setMinWidth(WIDTH_NAME);

      final int WIDTH_ORIGINKEY = 50;
      Button btnOriginalKey = new Button();
      btnOriginalKey.setText(song.getOriginalKey());
      btnOriginalKey.setMaxWidth(WIDTH_ORIGINKEY);
      btnOriginalKey.setMinWidth(WIDTH_ORIGINKEY);

      final int WIDTH_CURRENTKEY = 70;
      Button btnCurrentKey = new Button();
      btnCurrentKey.setText(song.getCurrentKey() != null ? "->" + song.getCurrentKey(): "");
      btnCurrentKey.setMaxWidth(WIDTH_CURRENTKEY);
      btnCurrentKey.setMinWidth(WIDTH_CURRENTKEY);

      final int WIDTH_ADDITIONALS_MP3 = 50;
      Button btnAdditionalMp3 = new Button();
      btnAdditionalMp3.setText(song.getCurrentKey() != null ? "->" + song.getCurrentKey(): "");
      btnAdditionalMp3.setMaxWidth(WIDTH_ADDITIONALS_MP3);
      btnAdditionalMp3.setMinWidth(WIDTH_ADDITIONALS_MP3);
      if (song.getAdditional(AdditionalType.AUDIO) != null)
        btnAdditionalMp3.setGraphic(Consts.createIcon("fas-file-audio", Consts.ICON_SIZE_TOOLBAR));

      TreeItem treeItemSong = new TreeItem(scopeItem, new HBox(5, scopeItem.getIcon(), lblId, lblName, btnOriginalKey, btnCurrentKey, btnAdditionalMp3));
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
