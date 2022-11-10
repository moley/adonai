package org.adonai.fx.main;

import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.adonai.ApplicationEnvironment;
import org.adonai.SizeInfo;
import org.adonai.actions.HelpAction;
import org.adonai.actions.SearchAction;
import org.adonai.api.MainAction;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.presentation.PresentationDocumentBuilder;
import org.adonai.export.presentation.PresentationExporter;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.SongViewer;
import org.adonai.fx.editcontent.SongEditor;
import org.adonai.fx.renderer.SongCellRenderer;
import org.adonai.fx.scope.ScopeController;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.player.Mp3Player;
import org.adonai.presentation.Metronome;
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
  public Button btnLeadVoice;
  public Button btnTransposedKey;
  public Button btnOriginalKey;
  //public Button btnHelp;
  public Button btnAdmin;
  public Button btnViewer;
  public Button btnEditor;
  public Pane panMetronome;
  public Button btnSpeed;

  private final Mp3Player mp3Player = new Mp3Player();

  private final Metronome metronome = new Metronome();

  private final MaskLoader<ScopeController> maskLoaderScope = new MaskLoader<>();
  private Mask<ScopeController> maskScope;

  private final MaskLoader<SongEditor> maskLoaderContent = new MaskLoader<>();
  private Mask<SongEditor> songEditorMask;

  public void initialize() {
    btnMainActions.setGraphic(Consts.createIcon("fas-bars", Consts.ICON_SIZE_TOOLBAR));
    btnAdmin.setGraphic(Consts.createIcon("fas-code-branch", Consts.ICON_SIZE_TOOLBAR));
    btnAdmin.setTooltip(new Tooltip("Data"));
    btnAdmin.setOnAction(event -> reloadScope());
    //btnHelp.setTooltip(new Tooltip("Help screen"));
    //btnHelp.setOnAction(event -> openHelp ());
    //btnHelp.setGraphic(Consts.createIcon("fas-info", Consts.ICON_SIZE_TOOLBAR));



    btnViewer.setGraphic(Consts.createIcon("fas-binoculars", Consts.ICON_SIZE_TOOLBAR));
    btnViewer.setTooltip(new Tooltip("Viewer"));
    btnViewer.setOnAction(event -> reloadViewer());

    btnEditor.setGraphic(Consts.createIcon("fas-edit", Consts.ICON_SIZE_TOOLBAR));
    btnEditor.setTooltip(new Tooltip("Editor"));
    btnEditor.setOnAction(event -> reloadEditor());

    main.setOnKeyTyped(event -> {
      if (event.getCode().equals(KeyCode.E)) {
        Platform.runLater(this::reloadEditor);

      }
    });

    main.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.F1)) {
        openHelp();
      }
      if (event.getCode().equals(KeyCode.M)) {
        metronome.setBpm(getApplicationEnvironment().getCurrentSong().getSpeed());
        metronome.setVisible(! metronome.isVisible());

      }
      else if (event.getCode().equals(KeyCode.E)) {
        reloadEditor ();
      }
      else if (event.getCode().equals(KeyCode.F)) {
        ApplicationEnvironment applicationEnvironment = getApplicationEnvironment();
        SearchAction<Song> searchAction = new SearchAction<>();
        FilteredList<Song> filteredList = new FilteredList<>(FXCollections.observableArrayList(applicationEnvironment.getSongsOfCurrentScope()));
        filteredList.setPredicate(song -> true);
        searchAction.open(applicationEnvironment, cellrendere -> new SongCellRenderer(), event1 -> {
          Song selectedSong = searchAction.getSelectedItem();
          if (selectedSong != null) {
            log.info("Search action selects song " + selectedSong.getId());
            applicationEnvironment.setCurrentSession(null);
            applicationEnvironment.setCurrentSong(selectedSong);
            reloadViewer();
          }
        }, filteredList, "", 50, 100);
      }

    });

    panMetronome.getChildren().add(metronome.getControl());

    btnTransposedKey.setOnAction(event -> {
      getApplicationEnvironment().setShowOriginalKey(false);
      btnTransposedKey.setStyle("-fx-background-color:yellow");
      btnOriginalKey.setStyle("");
      reloadViewer();
    });
    btnOriginalKey.setOnAction(event -> {
      getApplicationEnvironment().setShowOriginalKey(true);
      btnOriginalKey.setStyle("-fx-background-color:yellow");
      btnTransposedKey.setStyle("");
      reloadViewer();
    });

    btnPlayerBeginning.setGraphic(Consts.createIcon("fas-fast-backward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerBeginning.setOnAction(event -> {
      mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
      mp3Player.beginning();
    });
    btnPlayerBackward.setGraphic(Consts.createIcon("fas-step-backward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerBackward.setOnAction(event -> {
      mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
      mp3Player.backward();
    });
    btnPlayerPause.setGraphic(Consts.createIcon("fas-pause", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerPause.setOnAction(event -> {
      mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
      mp3Player.pause();
    });
    btnPlayerPlay.setGraphic(Consts.createIcon("fas-play", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerPlay.setOnAction(event -> {
      mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
      mp3Player.play();
    });
    btnPlayerForward.setGraphic(Consts.createIcon("fas-step-forward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerForward.setOnAction(event -> {
      mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
      mp3Player.forward();
    });
    btnPlayerEnd.setGraphic(Consts.createIcon("fas-fast-forward", Consts.ICON_SIZE_TOOLBAR));
    btnPlayerEnd.setOnAction(event -> {
      mp3Player.setFile(getApplicationEnvironment().getMp3FileOfCurrentSong());
      mp3Player.end();
    });

  }

  private void openHelp() {
    HelpAction helpAction = new HelpAction();
    helpAction.show(this);
  }

    private void reloadActionMenu () {
    log.info("reloadActionMenu called");
    btnMainActions.getItems().clear();

    for (MainAction nextMainAction: getApplicationEnvironment().getExtensions(MainAction.class)) {
      if (nextMainAction.isVisible()) {
        MenuItem menuItem = new MenuItem();
        menuItem.setText(nextMainAction.getDisplayName());
        if (nextMainAction.getIconname() != null)
          menuItem.setGraphic(Consts.createIcon(nextMainAction.getIconname(), Consts.ICON_SIZE_TOOLBAR));
        menuItem.setOnAction(nextMainAction.getEventHandler(getApplicationEnvironment()));
        btnMainActions.getItems().add(menuItem);
      }
    }


  }

  private void reloadScope () {
    if (maskScope == null)
      maskScope = maskLoaderScope.loadWithStage("scope");
    ScopeController scopeController = maskScope.getController();
    scopeController.setApplicationEnvironment(getApplicationEnvironment());
    scopeController.loadData(null);
    scopeController.setMainController(this);
    main.setCenter(maskScope.getRoot());
    getApplicationEnvironment().setCursorSelectsSong(false);
    main.requestLayout();

  }

  private void reloadEditor () {
    if (songEditorMask == null)
      songEditorMask = maskLoaderContent.loadWithStage("songeditor");
    SongEditor songEditor = songEditorMask.getController();
    songEditor.setApplicationEnvironment(getApplicationEnvironment());
    if (getApplicationEnvironment().getCurrentSong() == null && getApplicationEnvironment().getCurrentSongBook().getSongs().size() > 0)
      getApplicationEnvironment().setCurrentSong(getApplicationEnvironment().getCurrentSongBook().getSongs().get(0));
    songEditor.setSong(getApplicationEnvironment().getCurrentSong());
    songEditor.setMainController(this);
    main.setCenter(songEditorMask.getRoot());
    getApplicationEnvironment().setCursorSelectsSong(false);
    main.requestLayout();
  }

  public Node getCenter () {
    return main.getCenter();
  }


  public void reloadViewer() {
    log.info("reloadEditor called with tenant " + getApplicationEnvironment().getModel().getCurrentTenant());

    SizeInfo sizeInfo = new SizeInfo(main.getWidth(), main.getHeight() - 200);
    PresentationExporter exporter = new PresentationExporter(getApplicationEnvironment(), sizeInfo, event -> {
      log.info("onSongContentChangedHandler triggered");
      reloadViewer();
    });

    getApplicationEnvironment().setCursorSelectsSong(true);

    Configuration configuration = getApplicationEnvironment().getCurrentConfiguration();
    ExportConfiguration exportConfiguration = configuration.findDefaultExportConfiguration(PresentationDocumentBuilder.class);

    List<Song> songsOfCurrentScope = getApplicationEnvironment().getSongsOfCurrentScope();
    log.info("recalculate " + songsOfCurrentScope.size() + " songs");

    exportConfiguration.setOriginalKey(getApplicationEnvironment().isShowOriginalKey());
    exportConfiguration.setInterPartDistance(40.0);
    exportConfiguration.setRemarksStructureDistance(5.0); //TODO why it is not set from default
    exportConfiguration.setWithId(true);
    exportConfiguration.setWithTitle(true);
    exportConfiguration.setWithKeys(false);
    exportConfiguration.setWithLead(false);
    exportConfiguration.setWithRemarks(true); //TODO from configuration

    exporter.export(songsOfCurrentScope, null, exportConfiguration);



    SongViewer songViewer = new SongViewer(getApplicationEnvironment(), exporter.getPanes());
    main.setCenter(songViewer);
    songViewer.show();

  }


  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);

    applicationEnvironment.currentSongProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        log.info("Current song property changed to " + newValue.getId());
        btnLeadVoice.setText(newValue.getLeadVoice() != null ? newValue.getLeadVoice().getUsername() : "");
        btnOriginalKey.setText(newValue.getOriginalKey() != null ? newValue.getOriginalKey() : "");
        btnTransposedKey.setText(newValue.getCurrentKey() != null ? ("-> " + newValue.getCurrentKey()) : "");
        btnSpeed.setText(newValue.getSpeedNotNull());
      }
      else {
        btnLeadVoice.setText("");
        btnOriginalKey.setText("");
        btnTransposedKey.setText("");
        btnSpeed.setText("");
      }
    });

    reloadScope();
    reloadActionMenu();
  }
}
