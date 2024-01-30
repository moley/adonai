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

  public TreeItem<ScopeItem> getTree (final ApplicationEnvironment applicationEnvironment) {
    TreeItem <ScopeItem> rootItem = new TreeItem<>();
    TenantModel currentTenantModel = applicationEnvironment.getModel().getCurrentTenantModel();
    ScopeItem scopeItemRoot = new ScopeItem(currentTenantModel);
    rootItem.setValue(scopeItemRoot);

    //SongBook
    SongBook currentSongBook = applicationEnvironment.getCurrentSongBook();
    ScopeItem scopeItemSongBook = applicationEnvironment.getScopeItem(currentSongBook);
    TreeItem <ScopeItem>treeItemSongBook = new TreeItem<>(scopeItemSongBook, scopeItemSongBook.getIcon());
    for (Song song : scopeItemSongBook.getSongBook().getSongs()) {
      TreeItem <ScopeItem>treeItemSong = createSongTreeItem(scopeItemSongBook, song);
      treeItemSongBook.getChildren().add(treeItemSong);
    }
    rootItem.getChildren().add(treeItemSongBook);

    //Sessions
    for (Session nextSession: applicationEnvironment.getCurrentConfiguration().getSessions()) {
      ScopeItem scopeItemSession = applicationEnvironment.getScopeItem(nextSession);
      TreeItem <ScopeItem>treeItemSession = new TreeItem<>(scopeItemSession, scopeItemSession.getIcon());
      for (Integer songId : nextSession.getSongs()) {
        Song song = currentSongBook.findSong(songId);
        TreeItem <ScopeItem>treeItemSong = createSongTreeItem(scopeItemSession, song);

        treeItemSession.getChildren().add(treeItemSong);
      }
      rootItem.getChildren().add(treeItemSession);
    }

    return rootItem;

  }

  private TreeItem <ScopeItem> createSongTreeItem (final ScopeItem parent, final Song song) {
    ScopeItem scopeItem = new ScopeItem(parent, song);

    Label lblId = new Label(song.getId().toString());
    final int WIDTH_ID = 50;
    lblId.setMaxWidth(WIDTH_ID);
    lblId.setMinWidth(WIDTH_ID);

    Label lblName = new Label(song.getName());
    final int WIDTH_NAME = 400;
    lblName.setMaxWidth(WIDTH_NAME);
    lblName.setMinWidth(WIDTH_NAME);

    final int WIDTH_LEAD = 150;
    Button btnLeadVoice = new Button();
    btnLeadVoice.setText((song.getLeadVoice() != null ? song.getLeadVoice().getUsername(): ""));
    btnLeadVoice.setMaxWidth(WIDTH_LEAD);
    btnLeadVoice.setMinWidth(WIDTH_LEAD);

    final int WIDTH_CURRENTKEY =75;
    Button btnCurrentKey = new Button();
    btnCurrentKey.setText(song.getCurrentKey() != null ? song.getCurrentKey(): "");
    btnCurrentKey.setMaxWidth(WIDTH_CURRENTKEY);
    btnCurrentKey.setMinWidth(WIDTH_CURRENTKEY);

    final int WIDTH_ADDITIONALS_MP3 = 50;
    Button btnAdditionalMp3 = new Button();
    btnAdditionalMp3.setMaxWidth(WIDTH_ADDITIONALS_MP3);
    btnAdditionalMp3.setMinWidth(WIDTH_ADDITIONALS_MP3);
    if (song.getAdditional(AdditionalType.AUDIO) != null)
      btnAdditionalMp3.setGraphic(Consts.createIcon("fas-file-audio", Consts.ICON_SIZE_TOOLBAR));
    else {
      btnAdditionalMp3.setText("");
      btnAdditionalMp3.setDisable(true);
    }

    Label lblStatus = new Label();
    lblStatus.setText((song.getStatus() != null ? song.getStatus().name():""));

    return new TreeItem<>(scopeItem, new HBox(5, scopeItem.getIcon(), lblId, lblName, btnLeadVoice, btnCurrentKey, btnAdditionalMp3, lblStatus));

  }
}
