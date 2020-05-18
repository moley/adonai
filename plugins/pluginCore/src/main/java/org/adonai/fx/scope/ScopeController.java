package org.adonai.fx.scope;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.Consts;
import org.adonai.fx.ContentChangeableController;
import org.adonai.fx.main.ScopeItem;
import org.adonai.model.Song;

public class ScopeController extends ContentChangeableController {
  @FXML private ListView lviSongs;
  @FXML private TextField txtSearch;
  @FXML private Button btnAddBefore;
  @FXML private Button btnMoveUp;
  @FXML private Button btnRemove;
  @FXML private Button btnMoveDown;
  @FXML private Button btnAddAfter;

  private ScopeItem scopeItem;

  private ApplicationEnvironment applicationEnvironment;

  @FXML public void initialize() {
    btnAddBefore.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveUp.setGraphic(Consts.createIcon("fas-angle-up", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setGraphic(Consts.createIcon("fas-trash", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveDown.setGraphic(Consts.createIcon("fas-angle-down", Consts.ICON_SIZE_VERY_SMALL));
    btnAddAfter.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));

  }

  @Override protected void save() {

  }

  public void setSongList(List<Song> songList) {
    lviSongs.setItems(FXCollections.observableArrayList(songList));
  }

  @Override public ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }

  public ScopeItem getScopeItem() {
    return scopeItem;
  }

  public void setScopeItem(ScopeItem scopeItem) {
    this.scopeItem = scopeItem;
  }
}
