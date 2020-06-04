package org.adonai.fx.main;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
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
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.editor.SongEditor;
import org.adonai.fx.renderer.ScopeItemCellRenderer;
import org.adonai.fx.renderer.ScopeItemStringConverter;
import org.adonai.fx.scope.ScopeController;
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
  public HBox panHeader;
  public BorderPane main;
  public ComboBox<ScopeItem> cboScope;
  public Button btnLeadVoice;
  public Button btnTransposedKey;
  public Button btnOriginalKey;
  public Button btnWorkspace;

  private Mp3Player mp3Player = new Mp3Player();

  private MaskLoader<ScopeController> maskLoaderScope = new MaskLoader<ScopeController>();
  private Mask<ScopeController> maskScope;



  public void initialize() {
    btnMainActions.setGraphic(Consts.createIcon("fas-bars", Consts.ICON_SIZE_TOOLBAR));
    btnWorkspace.setGraphic(Consts.createIcon("fas-code-branch", Consts.ICON_SIZE_TOOLBAR));
    btnWorkspace.setTooltip(new Tooltip("Workspace"));
    btnWorkspace.setOnAction(event -> reloadScope());

    btnTransposedKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        getApplicationEnvironment().setShowOriginalKey(false);
        btnTransposedKey.setStyle("-fx-background-color:yellow");
        btnOriginalKey.setStyle("");
        reloadEditor();
      }
    });
    btnOriginalKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        getApplicationEnvironment().setShowOriginalKey(true);
        btnOriginalKey.setStyle("-fx-background-color:yellow");
        btnTransposedKey.setStyle("");
        reloadEditor();
      }
    });

    cboScope.setCellFactory(cellFactory -> new ScopeItemCellRenderer());
    cboScope.setConverter(new ScopeItemStringConverter());

    cboScope.setOnMouseClicked(evenHandler -> {
        ScopeItem scopeItem = cboScope.getSelectionModel().getSelectedItem();
        getApplicationEnvironment().setCurrentSession(scopeItem.getSession());
        reloadEditor();
      });

    cboScope.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ScopeItem>() {
      @Override public void changed(ObservableValue<? extends ScopeItem> observable, ScopeItem oldValue, ScopeItem newValue) {
        getApplicationEnvironment().setCurrentSession(newValue.getSession());
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
  }


  private void reloadScopeCombobox() {
    cboScope.setItems(FXCollections.observableArrayList(getApplicationEnvironment().getAllScopeItems()));
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
      if (nextMainAction.getIconname() != null)
        menuItem.setGraphic(Consts.createIcon(nextMainAction.getIconname(), Consts.ICON_SIZE_TOOLBAR));
      menuItem.setOnAction(nextMainAction.getEventHandler(getApplicationEnvironment()));
      btnMainActions.getItems().add(menuItem);
    }


  }

  private void reloadScope () {
    if (maskScope == null)
      maskScope = maskLoaderScope.load("scope");
    ScopeController scopeController = maskScope.getController();
    scopeController.setApplicationEnvironment(getApplicationEnvironment());
    scopeController.loadData(null);
    main.setCenter(maskScope.getRoot());
  }

  private void reloadEditor() {
    log.info("reloadEditor called with tenant " + getApplicationEnvironment().getCurrentTenant());

    SizeInfo sizeInfo = new SizeInfo(main.getWidth(), main.getHeight() - 200);
    PresentationExporter exporter = new PresentationExporter(getApplicationEnvironment(), sizeInfo, new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        log.info("onSongContentChangedHandler triggered");
        reloadEditor();
      }
    });

    Configuration configuration = getApplicationEnvironment().getCurrentConfiguration();
    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(PresentationDocumentBuilder.class);

    List<Song> songsOfCurrentScope = getApplicationEnvironment().getSongsOfCurrentScope();
    log.info("recalculate " + songsOfCurrentScope.size() + " songs");

    exportConfiguration.setOriginalKey(getApplicationEnvironment().isShowOriginalKey());
    exportConfiguration.setInterPartDistance(Double.valueOf(40));
    exportConfiguration.setWithId(true);
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithKeys(false);
    exportConfiguration.setWithLead(false);
    exportConfiguration.setWithRemarks(true); //TODO from configuration

    exporter.export(songsOfCurrentScope, null, exportConfiguration);
    SongEditor songeditorRoot = new SongEditor(getApplicationEnvironment(), exporter.getPanes());
    songeditorRoot.currentSongProperty().addListener(new ChangeListener<Song>() {
      @Override public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
        getApplicationEnvironment().setCurrentSong(newValue);
        btnLeadVoice.setText(newValue.getLeadVoice() != null ? newValue.getLeadVoice().getUsername(): "");
        btnOriginalKey.setText(newValue.getOriginalKey() != null ? newValue.getOriginalKey(): "");
        btnTransposedKey.setText(newValue.getCurrentKey() != null ? ("-> " + newValue.getCurrentKey()): "");
      }
    });
    main.setCenter(songeditorRoot);
    songeditorRoot.show();
  }


  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);

    reloadScopeCombobox();
    reloadScope();
    reloadActionMenu();




  }
}
