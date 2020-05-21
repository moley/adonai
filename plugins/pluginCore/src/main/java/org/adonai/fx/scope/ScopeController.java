package org.adonai.fx.scope;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.WindowEvent;
import org.adonai.ServiceRegistry;
import org.adonai.actions.AddSongAction;
import org.adonai.actions.SelectAction;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.main.ScopeItem;
import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.services.AddSongService;
import org.adonai.services.RemoveSongService;
import org.adonai.services.SessionService;
import org.adonai.ui.SongCellFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScopeController extends AbstractController {

  private static final Logger log = LoggerFactory.getLogger(ScopeController.class);
  @FXML private TreeView<ScopeItem> treScope;
  @FXML private Button btnAdd;
  @FXML private Button btnMoveUp;
  @FXML private Button btnRemove;
  @FXML private Button btnMoveDown;

  private ScopeTreeProvider scopeTreeProvider = new ScopeTreeProvider();

  private SessionService sessionService = new SessionService();


  @FXML
  public void initialize () {
    btnAdd.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAdd.setOnAction(action -> add());

    btnMoveUp.setGraphic(Consts.createIcon("fas-angle-up", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveUp.setOnAction(action-> moveUp());

    btnRemove.setGraphic(Consts.createIcon("fas-trash", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setOnAction(action -> remove());

    btnMoveDown.setGraphic(Consts.createIcon("fas-angle-down", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveDown.setOnAction(action -> moveDown());

    treScope.setShowRoot(false);
    treScope.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ScopeItem>>() {
      @Override public void changed(ObservableValue<? extends TreeItem<ScopeItem>> observable,
          TreeItem<ScopeItem> oldValue, TreeItem<ScopeItem> newValue) {
        if (newValue != null) {
          ScopeItem scopeItem = newValue.getValue();
          boolean songInSessionSelected = scopeItem.getSong() != null && scopeItem.getParentItem().getSession() != null;
          btnMoveDown.setVisible(songInSessionSelected);
          btnMoveUp.setVisible(songInSessionSelected);
        }
      }
    });

  }

  private void add() {

    TreeItem<ScopeItem> treeItem = treScope.getSelectionModel().getSelectedItem();
    ScopeItem scopeItem = treeItem.getValue();

    //Select song in session or session itself
    Session session = getSession(scopeItem);
    if (session != null) {
      log.info("addBefore in session " + session.getName());
      SelectAction<Song> selectSong = new SelectAction<Song>(getApplicationEnvironment());
      List<Song> allSongs = getApplicationEnvironment().getCurrentSongBook().getSongs();
      selectSong.open(allSongs, 0.0, 0.0, new SongCellFactory(), new EventHandler<WindowEvent>() {
        @Override public void handle(WindowEvent event) {
          Song selectedSong = selectSong.getSelectedItem();
          log.info("handle window closed in selectsong on " + selectedSong);
          if (selectedSong != null) {
            log.info("Add song " + selectedSong.getId() + " to session " + session.getName());
            ServiceRegistry serviceRegistry = getApplicationEnvironment().getServices();
            serviceRegistry.getSessionService().addSong(session, selectedSong);
            loadData();
          }
        }
      });
    }

    SongBook songBook = getSongBook(scopeItem);
    if (songBook != null) {
      log.info("add() in songbook");
      SongBook currentSongBook = getApplicationEnvironment().getCurrentSongBook();
      Configuration currentConfiguration = getApplicationEnvironment().getCurrentConfiguration();
      AddSongAction addSongHandler = new AddSongAction();
      addSongHandler.add(getApplicationEnvironment(), 0.0, 0.0, currentConfiguration, currentSongBook, new EventHandler<WindowEvent>() {
        @Override public void handle(WindowEvent event) {

          Song song = addSongHandler.getNewSong();
          log.info("New song " + song + " created");
          if (song != null) {
            AddSongService addSongService = new AddSongService();   //Add new song to songbook
            addSongService.addSong(song, currentSongBook);
            getApplicationEnvironment().setCurrentSong(song);
            loadData();
          }
        }
      });

    }
  }

  private SongBook getSongBook (final ScopeItem scopeItem) {
    if (scopeItem.getSongBook() != null)
      return scopeItem.getSongBook();
    else if (scopeItem.getParentItem() != null && scopeItem.getParentItem().getSongBook() != null)
      return scopeItem.getParentItem().getSongBook();
    else
      return null;
  }

  private Song getSong (final ScopeItem scopeItem) {
    return scopeItem.getSong();
  }

  private Session getSession (final ScopeItem scopeItem) {
    if (scopeItem.getSession() != null)
      return scopeItem.getSession();
    else if (scopeItem.getParentItem() != null && scopeItem.getParentItem().getSession() != null)
      return scopeItem.getParentItem().getSession();
    else
      return null;
  }

  private void moveUp() {
    TreeItem treeItem = treScope.getSelectionModel().getSelectedItem();
  }

  private void remove() {
    TreeItem<ScopeItem> treeItem = treScope.getSelectionModel().getSelectedItem();
    ScopeItem scopeItem = treeItem.getValue();

    //A session is selected
    if (scopeItem.getSession() != null) {

      Configuration configuration = getApplicationEnvironment().getCurrentConfiguration();
      Session session = getSession(scopeItem);
      sessionService.removeSession(configuration, session);
      loadData();

    } else {  // A song is selected
      Song song = getSong(scopeItem);
      Session session = getSession(scopeItem);
      if (session != null) {
        sessionService.removeSong(session, song);
        loadData();
      }
      SongBook songBook = getSongBook(scopeItem);
      if (songBook != null) {
        RemoveSongService removeSongService = new RemoveSongService();
        removeSongService.removeSong(song, songBook);
        loadData();
      }
    }
  }

  private void moveDown() {
    TreeItem treeItem = treScope.getSelectionModel().getSelectedItem();
  }

  private void addAfter() {
    TreeItem treeItem = treScope.getSelectionModel().getSelectedItem();
  }

  public void loadData() {
    treScope.setRoot(scopeTreeProvider.getTree(getApplicationEnvironment()));
  }
}
