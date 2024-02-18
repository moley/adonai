package org.adonai.fx.scope;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.adonai.ServiceRegistry;
import org.adonai.actions.AddSongAction;
import org.adonai.actions.SearchAction;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.main.ScopeItem;
import org.adonai.fx.renderer.SongCellRenderer;
import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.TenantModel;
import org.adonai.services.AddSongService;
import org.adonai.services.RemoveSongService;
import org.adonai.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScopeController extends AbstractController {

  private final static Logger log = LoggerFactory.getLogger(ScopeController.class);
  @FXML private Label lblName;
  @FXML private TextField txtName;
  @FXML private TreeView<ScopeItem> treScope;
  @FXML private Button btnAdd;
  @FXML private Button btnMoveUp;
  @FXML private Button btnRemove;
  @FXML private Button btnMoveDown;

  @FXML private MenuButton btnCloneToTenant;

  private final ScopeTreeProvider scopeTreeProvider = new ScopeTreeProvider();

  private final SessionService sessionService = new SessionService();

  @FXML public void initialize() {
    lblName.setVisible(false);
    txtName.setVisible(false);
    txtName.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER))
       loadData(getSelectedScopeItem());
    });
    btnAdd.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAdd.setTooltip(new Tooltip("Add new song"));
    btnAdd.setOnAction(action -> add());

    btnMoveUp.setGraphic(Consts.createIcon("fas-angle-up", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveUp.setTooltip(new Tooltip("Move song up"));
    btnMoveUp.setOnAction(action -> moveUp());

    btnRemove.setGraphic(Consts.createIcon("fas-trash", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setOnAction(action -> remove());
    btnRemove.setTooltip(new Tooltip("Remove selected song"));

    btnMoveDown.setGraphic(Consts.createIcon("fas-angle-down", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveDown.setOnAction(action -> moveDown());
    btnMoveDown.setTooltip(new Tooltip("Move song down"));

    btnCloneToTenant.setGraphic(Consts.createIcon("fas-clone", Consts.ICON_SIZE_VERY_SMALL));
    btnCloneToTenant.setOnAction(action -> cloneToTenant ());
    btnCloneToTenant.setTooltip(new Tooltip("Clone song to tenant"));



    treScope.setShowRoot(true);

    treScope.setOnKeyPressed(event -> {
      log.info("handle onKeyPressed " + getSelectedScopeItem());

      if (event.getText().equals("+")) {
        event.consume();
        add();
      }
      else if (event.getText().equals("-")) {
        event.consume();
        remove();
      }
      else if (event.getCode().equals(KeyCode.UP) && event.isShiftDown()) {
        log.info("onKeyTyped down");
        event.consume();
        moveUp();
      }
      else if (event.getCode().equals(KeyCode.DOWN) && event.isShiftDown()) {
        log.info("onKeyTyped up");
        event.consume();
        moveDown();
      }
      else if (event.getCode().equals(KeyCode.ENTER)) {
        stepToSong();
      }


    });

    treScope.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {


      ScopeItem selectedScopeItem = getSelectedScopeItem();
      log.info("selectionChanged property: " + selectedScopeItem.toString());

      //btnAdd.setDisable(selectedScopeItem.getSongBook() == null && selectedScopeItem.getR );

      if (oldValue != null && oldValue.getValue() != null) {
        ScopeItem scopeItem = oldValue.getValue();
        if (scopeItem.getSession() != null) {
          Session currentSession = scopeItem.getSession();
          txtName.textProperty().unbindBidirectional(currentSession.getNameProperty());
        }
      }

      if (newValue != null && newValue.getValue() != null) {
        ScopeItem scopeItem = newValue.getValue();
        boolean songInSessionSelected = scopeItem != null && scopeItem.getSong() != null && scopeItem.getParentItem().getSession() != null;
        boolean sessionDetailsShown = scopeItem != null && scopeItem.getSession() != null;

        if (scopeItem.getSong() != null) {
          getApplicationEnvironment().currentSongProperty().set(scopeItem.getSong());
        }

        btnMoveDown.setVisible(songInSessionSelected);
        btnMoveUp.setVisible(songInSessionSelected);

        lblName.setVisible(sessionDetailsShown);
        txtName.setVisible(sessionDetailsShown);

        if (scopeItem.getSession() != null) {
          Session currentSession = scopeItem.getSession();
          txtName.textProperty().bindBidirectional(currentSession.getNameProperty());
        }



      }
    });

    treScope.setOnMouseClicked(event -> {
      if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
        stepToSong();
      }
    });

  }

  private void cloneToTenant() {
  }

  private void stepToSong () {
    ScopeItem selected = treScope.getSelectionModel().getSelectedItem().getValue();
    log.info("Step to " + selected.getId() + "-" + selected.getName());

    getApplicationEnvironment().setCurrentSession(selected.getSession());
    getApplicationEnvironment().setCurrentSong(selected.getSong());
    getMainController().reloadViewer();
  }

  private void add() {

    ScopeItem scopeItem = getSelectedScopeItem();
    if (scopeItem.isRoot()) { //Root
      Session newSession = sessionService.newSession(getApplicationEnvironment().getCurrentConfiguration());
      loadData(new ScopeItem(newSession));
    } else {

      //Select song in session or session itself
      Session session = getSession(scopeItem);
      if (session != null) {
        log.info("addBefore in session " + session.getName());
        SearchAction<Song> selectSong = new SearchAction<>();
        List<Song> allSongs = getApplicationEnvironment().getCurrentSongBook().getSongs();
        FilteredList<Song> filteredList = new FilteredList<>(FXCollections.observableArrayList(allSongs));
        selectSong.open(getApplicationEnvironment(), renderer -> new SongCellRenderer(), event -> {
          Song selectedSong = selectSong.getSelectedItem();
          log.info("handle window closed in selectsong on " + selectedSong);
          if (selectedSong != null) {
            log.info("Add song " + selectedSong.getId() + " to session " + session.getName());
            ServiceRegistry serviceRegistry = getApplicationEnvironment().getServices();
            serviceRegistry.getSessionService().addSong(session, selectedSong);
            loadData(new ScopeItem(new ScopeItem(session), selectedSong));
          }
        }, filteredList, "", 10.0, 10.0);
      }

      SongBook songBook = getSongBook(scopeItem);
      if (songBook != null) {
        log.info("add() in songbook");
        SongBook currentSongBook = getApplicationEnvironment().getCurrentSongBook();
        Configuration currentConfiguration = getApplicationEnvironment().getCurrentConfiguration();
        AddSongAction addSongHandler = new AddSongAction();
        addSongHandler.add(getApplicationEnvironment(), currentConfiguration, currentSongBook, event -> {

          Song song = addSongHandler.getNewSong();
          log.info("New song " + song + " created");
          if (song != null) {
            AddSongService addSongService = new AddSongService();   //Add new song to songbook
            addSongService.addSong(song, currentSongBook);
            getApplicationEnvironment().setCurrentSong(song);
            loadData(new ScopeItem(new ScopeItem(songBook), song));
          }
        });

      }
    }

  }

  private SongBook getSongBook(final ScopeItem scopeItem) {
    if (scopeItem.getSongBook() != null)
      return scopeItem.getSongBook();
    else if (scopeItem.getParentItem() != null && scopeItem.getParentItem().getSongBook() != null)
      return scopeItem.getParentItem().getSongBook();
    else
      return null;
  }

  private Song getSong(final ScopeItem scopeItem) {
    return scopeItem.getSong();
  }

  private Session getSession(final ScopeItem scopeItem) {
    if (scopeItem == null)
      throw new IllegalArgumentException("Parameter scopeItem must not be null");
    if (scopeItem.getSession() != null)
      return scopeItem.getSession();
    else if (scopeItem.getParentItem() != null && scopeItem.getParentItem().getSession() != null)
      return scopeItem.getParentItem().getSession();
    else
      return null;
  }

  private void moveUp() {
    ScopeItem scopeItem = getSelectedScopeItem();
    Session session = getSession(scopeItem);
    if (session != null) {
      sessionService.moveUp(session, scopeItem.getSong());
      loadData(scopeItem);
    } else
      throw new IllegalStateException("MoveDown can only be called with a sessioned scope item");
  }

  private void moveDown() {
    ScopeItem scopeItem = getSelectedScopeItem();
    Session session = getSession(scopeItem);
    if (session != null) {
      sessionService.moveDown(session, scopeItem.getSong());
      loadData(scopeItem);
    } else
      throw new IllegalStateException("MoveDown can only be called with a sessioned scope item");
  }

  private ScopeItem getSelectedScopeItem() {
    TreeItem<ScopeItem> treeItem = treScope.getSelectionModel().getSelectedItem();
    return treeItem != null ? treeItem.getValue() : null;
  }

  private void remove() {
    ScopeItem scopeItem = getSelectedScopeItem();
    if (scopeItem == null)
      return;

    //A session is selected
    if (scopeItem.getSession() != null) {
      Configuration configuration = getApplicationEnvironment().getCurrentConfiguration();
      Session session = getSession(scopeItem);
      sessionService.removeSession(configuration, session);
      loadData(new ScopeItem(session));

    } else {  // A song is selected
      Song song = getSong(scopeItem);
      Session session = getSession(scopeItem);
      if (session != null) {
        sessionService.removeSong(session, song);
        loadData(new ScopeItem(session));
      }
      SongBook songBook = getSongBook(scopeItem);
      if (songBook != null) {
        RemoveSongService removeSongService = new RemoveSongService();
        removeSongService.removeSong(song, songBook);
        loadData(new ScopeItem(songBook));
      }
    }
  }

  public void loadData(ScopeItem selectedScopeItem) {
    String selectedId = (selectedScopeItem != null ? selectedScopeItem.getId() : null);

    treScope.setShowRoot(true);
    treScope.setRoot(scopeTreeProvider.getTree(getApplicationEnvironment()));
    treScope.getRoot().setExpanded(true);

    btnCloneToTenant.getItems().clear();
    for (String next: getApplicationEnvironment().getOtherTenants()) {
      MenuItem menuItemTenant = new MenuItem(next);
      menuItemTenant.setOnAction(new EventHandler<>() {
        @Override public void handle(ActionEvent event) {
          if (getSelectedScopeItem() != null && getSelectedScopeItem().getSong() != null) {
            String tenant = ((MenuItem) event.getSource()).getText();
            System.out.println("Clone " + getSelectedScopeItem().getSong().getName() + " to " + tenant);

            AddSongService addSongService = new AddSongService();

            TenantModel tenantModel = getApplicationEnvironment().getModel().getTenantModel(tenant);
            Configuration configurationOtherTenant = tenantModel.get();

            SongBook songBookOtherTenant = configurationOtherTenant.getSongBooks().get(0);
            addSongService.addSong(getSelectedScopeItem().getSong(), songBookOtherTenant);
            tenantModel.save();
          }


        }
      });
      btnCloneToTenant.getItems().add(menuItemTenant);
    }

    if (selectedId != null) {
      TreeItem<ScopeItem> root = treScope.getRoot();

      for (TreeItem<ScopeItem> firstLevel : root.getChildren()) {
        if (firstLevel.getValue().getId().equals(selectedId)) {
          treScope.getSelectionModel().select(firstLevel);
          break;
        }
        for (TreeItem<ScopeItem> secondLevel : firstLevel.getChildren()) {
          if (secondLevel.getValue().getId().equals(selectedId)) {
            firstLevel.setExpanded(true);
            treScope.getSelectionModel().select(secondLevel);
            break;
          }
        }
      }
    }
    treScope.requestFocus();


  }
}
