package org.adonai.fx.main;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.adonai.ApplicationEnvironment;
import org.adonai.SizeInfo;
import org.adonai.api.MainAction;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.presentation.PresentationDocumentBuilder;
import org.adonai.export.presentation.PresentationExporter;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.editor.SongEditor;
import org.adonai.fx.renderer.ScopeItemCellRenderer;
import org.adonai.fx.renderer.ScopeItemStringConverter;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.player.Mp3Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController extends AbstractController {

  private static final Logger log = LoggerFactory.getLogger(MainController.class);

  public MenuButton btnMainActions;
  public Button btnPlayerBeginning;
  public Button btnPlayerBackward;
  public Button btnPlayerPlay;
  public Button btnPlayerPause;
  public Button btnPlayerForward;
  public Button btnPlayerEnd;
  public Label lblSongInfo;
  public HBox panHeader;
  public BorderPane border;
  public ComboBox<ScopeItem> cboScope;

  private ScreenManager screenManager = new ScreenManager();

  private Mp3Player mp3Player = new Mp3Player();


  private ScopeItemProvider scopeItemProvider = new ScopeItemProvider();

  public void initialize() {
    btnMainActions.setGraphic(Consts.createIcon("fas-bars", Consts.ICON_SIZE_TOOLBAR));
    cboScope.setCellFactory(cellFactory -> new ScopeItemCellRenderer());
    cboScope.setConverter(new ScopeItemStringConverter());
    cboScope.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ScopeItem>() {
      @Override public void changed(ObservableValue<? extends ScopeItem> observable, ScopeItem oldValue, ScopeItem newValue) {
        getApplicationEnvironment().setCurrentScopeItem(newValue);
        reloadEditor();
      }
    });

    btnPlayerBeginning.setGraphic(Consts.createIcon("fas-fast-backward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerBeginning.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
        mp3Player.beginning();
      }
    });
    btnPlayerBackward.setGraphic(Consts.createIcon("fas-step-backward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerBackward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
        mp3Player.backward();
      }
    });
    btnPlayerPause.setGraphic(Consts.createIcon("fas-pause", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerPause.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
        mp3Player.pause();
      }
    });
    btnPlayerPlay.setGraphic(Consts.createIcon("fas-play", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerPlay.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
        mp3Player.play();
      }
    });
    btnPlayerForward.setGraphic(Consts.createIcon("fas-step-forward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerForward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
        mp3Player.forward();
      }
    });
    btnPlayerEnd.setGraphic(Consts.createIcon("fas-fast-forward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerEnd.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
        mp3Player.end();
      }
    });

    /**border.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
        log.info("Event " + event.getCode());
        if (event.getCode().equals(KeyCode.RIGHT)) {

          //TODO scope
          SongBook songBook = getApplicationEnvironment().getCurrentSongBook();
          int indexSong = songBook.getSongs().indexOf(getApplicationEnvironment().getCurrentSong());
          int newIndexSong = indexSong + 1;
          getApplicationEnvironment().setCurrentSong(songBook.getSongs().get(newIndexSong));
          log.info("Step to next song: " + getApplicationEnvironment().getCurrentSong().getId());
          reloadEditor();
        } else if (event.getCode().equals(KeyCode.LEFT)) {
          //TODO scope
          SongBook songBook = getApplicationEnvironment().getCurrentSongBook();
          int indexSong = songBook.getSongs().indexOf(getApplicationEnvironment().getCurrentSong());
          int newIndexSong = indexSong - 1;
          getApplicationEnvironment().setCurrentSong(songBook.getSongs().get(newIndexSong));
          log.info("Step to next song: " + getApplicationEnvironment().getCurrentSong().getId());
          reloadEditor();
        }
      }
    });**/
  }


  private void reloadScopeCombobox() {
    cboScope.setItems(FXCollections.observableArrayList(scopeItemProvider.getScopeItems(getApplicationEnvironment().getCurrentConfiguration())));
    if (cboScope.getSelectionModel().isEmpty())
      cboScope.getSelectionModel().selectFirst();
  }

  private void reloadActionMenu () {
    log.info("reloadActionMenu called");
    btnMainActions.getItems().clear();

    for (String nextTenant: getApplicationEnvironment().getTenants()) {
      MenuItem menuItem = new MenuItem();
      menuItem.setUserData(nextTenant);
      menuItem.setText("Use tenant " + nextTenant);
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
          log.info("Selected menuitem " + event.getSource());
          getApplicationEnvironment().getAdonaiProperties().setCurrentTenant(nextTenant);
          reloadScopeCombobox();
          reloadEditor();
        }
      });
      btnMainActions.getItems().add(menuItem);
    }

    btnMainActions.getItems().add(new SeparatorMenuItem());

    for (MainAction nextMainAction: getApplicationEnvironment().getExtensions(MainAction.class)) {
      MenuItem menuItem = new MenuItem();
      menuItem.setText(nextMainAction.getDisplayName());
      menuItem.setGraphic(Consts.createIcon(nextMainAction.getIconname(), Consts.ICON_SIZE_TOOLBAR));
      menuItem.setOnAction(nextMainAction.getEventHandler(getApplicationEnvironment()));
      btnMainActions.getItems().add(menuItem);
    }


  }

  private void reloadEditor() {
    log.info("reloadEditor called with tenant " + getApplicationEnvironment().getCurrentTenant());

    SizeInfo sizeInfo = new SizeInfo(border.getWidth(), border.getHeight() - 200);
    PresentationExporter exporter = new PresentationExporter(sizeInfo);

    Configuration configuration = getApplicationEnvironment().getCurrentConfiguration();
    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(PresentationDocumentBuilder.class);

    List<Song> songsOfCurrentScope = getApplicationEnvironment().getSongsOfCurrentScope();
    log.info("recalculate " + songsOfCurrentScope.size() + " songs");

    exporter.export(songsOfCurrentScope, null, exportConfiguration);
    SongEditor root = new SongEditor(exporter.getPanes(), getApplicationEnvironment());
    border.setCenter(root);
    root.show();
  }


  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);

    reloadScopeCombobox();
    reloadEditor();
    reloadActionMenu();




  }
}
