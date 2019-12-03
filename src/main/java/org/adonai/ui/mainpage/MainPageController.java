package org.adonai.ui.mainpage;

import java.io.File;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.WindowEvent;
import org.adonai.actions.*;
import org.adonai.actions.add.AddSongAction;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.model.*;
import org.adonai.online.DropboxAdapter;
import org.adonai.player.Mp3Player;
import org.adonai.services.AddSongService;
import org.adonai.services.RemoveSongService;
import org.adonai.services.RenumberService;
import org.adonai.services.SessionService;
import org.adonai.ui.Consts;
import org.adonai.ui.SongCellFactory;
import org.adonai.ui.UiUtils;
import org.adonai.ui.editor2.SongEditor;

import java.util.*;
import java.util.logging.Logger;

public class MainPageController {

  @FXML
  private ToolBar tbaActions;

  @FXML
  private BorderPane border;

  @FXML
  private ToolBar tbLeft;


  @FXML
  private StackPane spDetails;



  @FXML
  private ToggleButton togSongbooks;

  @FXML
  private ToggleButton togSessions;

  @FXML
  private ToggleButton togSession;


  @FXML
  private ListView<Song> lviSession;

  @FXML
  private ListView<Song> lviSongs;

  @FXML
  private ListView<Session> lviSessions;

  @FXML
  private VBox panSongDetails;

  @FXML
  private VBox panSessionDetails;

  @FXML
  private VBox panSessions;

  @FXML
  private VBox panSongbook;

  private MainPageContent currentContent;

  @FXML
  private Label lblCurrentEntity;

  @FXML
  private Label lblCurrentType;

  private FilteredList<Song> filteredSongList;

  private ConfigurationService configurationService = new ConfigurationService();

  private SessionService sessionService = new SessionService();

  private Configuration configuration;

  private int iconSizeToolbar = Consts.ICON_SIZE_VERY_SMALL;

  private static final Logger LOGGER = Logger.getLogger(MainPageController.class.getName());

  private Session currentSession = null;

  private Song currentSong = null;

  @FXML
  private TextField txtSessionName;

  private Mp3Player mp3Player = new Mp3Player();


  public void initialize() {
    lviSongs.setCellFactory(new SongCellFactory());
    lviSession.setCellFactory(new SongCellFactory());
    lviSessions.setPlaceholder(new Label("No sessions available, press + to add ones"));
    lviSession.setPlaceholder(new Label("No songs in session available, press + to add ones"));
    lblCurrentEntity.setId("currentEntity");
    lblCurrentType.setId("currentType");



    panSongDetails.setBackground(Background.EMPTY);

    panSessionDetails.setBackground(Background.EMPTY);

    txtSessionName.textProperty().addListener(new ChangeListener<String>() {
      @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        getCurrentSession().setName(newValue);
      }
    });

    configuration = configurationService.get();
    selectSongbook();

    refreshListViews(null);

    lviSongs.toFront();

    tbLeft.minWidthProperty().bind(Bindings.max(border.heightProperty(), tbLeft.prefWidthProperty()));

    //Button Plus
    Button btnAdd = new Button();
    btnAdd.setTooltip(new Tooltip("Add new item"));
    btnAdd.setGraphic(Consts.createIcon("fa-plus", iconSizeToolbar));
    btnAdd.setId("btnPlus");
    tbaActions.getItems().add(btnAdd);
    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        Bounds controlBounds = UiUtils.getBounds(btnAdd);
        Double x = controlBounds.getMinX() + 10;
        Double y = controlBounds.getMinY() - 20 - AddSongAction.ADD_SONG_DIALOG_HEIGHT;

        //In Song details reimport content of current song
        if (currentContent.equals(MainPageContent.SONG)) {
          AddSongAction addSongHandler = new AddSongAction();
          addSongHandler.add(x, y, getCurrentSongBook(), new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

              Song song = addSongHandler.getNewSong();
              LOGGER.info("New song " + song + " created");
              if (song != null) {
                currentSong.getSongParts().clear();
                currentSong.getSongParts().addAll(song.getSongParts());
                refreshListViews(song);                                 //Refresh list data and select the new song in editor
                selectSong(song);
              }
            }
          });
        }
        else //In Songbook add new song
        if (currentContent.equals(MainPageContent.SONGBOOK)) {
          AddSongAction addSongHandler = new AddSongAction();
          addSongHandler.add(x, y, getCurrentSongBook(), new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

              Song song = addSongHandler.getNewSong();
              LOGGER.info("New song " + song + " created");
              if (song != null) {
                SongBook songBook = getCurrentSongBook();
                AddSongService addSongService = new AddSongService();   //Add new song to songbook
                addSongService.addSong(song, songBook);
                refreshListViews(song);                                 //Refresh list data and select the new song in editor
                selectSong(song);
              }
            }
          });
        } else if (currentContent.equals(MainPageContent.SESSION)) { // in session add new song and add to session
          SelectAction<Song> selectSong = new SelectAction<Song>();
          List<Song> allSongs = getCurrentSongBook().getSongs();

          Double xSession = controlBounds.getMinX() + 10;
          Double ySession = controlBounds.getMinY() - 20 - SelectAction.SEARCHDIALOG_HEIGHT;

          selectSong.open(allSongs, xSession, ySession,  new SongCellFactory(), new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
              Song selectedSong = selectSong.getSelectedItem();
              if (selectedSong != null) {
                LOGGER.info("Add song " + selectedSong.getId() + " to session " + getCurrentSession().getName());
                sessionService.addSong(getCurrentSession(), selectedSong);
                refreshListViews(selectedSong);
              }
            }
          });

        } else if (currentContent.equals(MainPageContent.SESSIONS)) {
          SessionService sessionService = new SessionService();
          sessionService.newSession(configuration);
          refreshListViews(null);
        }
      }
    });

    //Button Minus
    Button btnRemove = new Button();
    btnRemove.setId("btnMinus");
    btnRemove.setTooltip(new Tooltip("Remove selected item"));
    btnRemove.setGraphic(Consts.createIcon("fa-minus", iconSizeToolbar));
    tbaActions.getItems().add(btnRemove);
    btnRemove.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        LOGGER.info("Button minus was pressed");
        if (currentContent.equals(MainPageContent.SESSIONS)) {
          LOGGER.info("Remove session " + getCurrentSession().getName());
          configuration.getSessions().remove(getCurrentSession());
          refreshListViews(null);
        } else if (currentContent.equals(MainPageContent.SESSION)) {
          int selectedIndex = lviSession.getSelectionModel().getSelectedIndex();
          Session session = getCurrentSession();
          session.getSongs().remove(selectedIndex);
          int previous = Math.max(selectedIndex - 1, 0);
          lviSession.getSelectionModel().select(previous);
          refreshListViews(null);
        } else if (currentContent.equals(MainPageContent.SONGBOOK)) {
          RemoveSongService removeSongService = new RemoveSongService();
          removeSongService.removeSong(lviSongs.getSelectionModel().getSelectedItem(), getCurrentSongBook());
          refreshListViews(null);
        }
      }
    });
    tbaActions.getItems().add(new Separator());
    Button btnMp3 = new Button();
    btnMp3.setTooltip(new Tooltip("Attach mp3 file to the song"));
    btnMp3.setId("btnMp3");
    btnMp3.setText("Mp3");
    btnMp3.setGraphic(Consts.createIcon(AdditionalType.AUDIO.getIconName(), iconSizeToolbar));
    tbaActions.getItems().add(btnMp3);
    btnMp3.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        if (! currentContent.equals(MainPageContent.SESSIONS)) {
          ConnectSongWithMp3Action connectSongWithMp3Action = new ConnectSongWithMp3Action();
          Bounds controlBounds = UiUtils.getBounds(btnMp3);
          Double x = controlBounds.getMinX() + 10;
          Double y = controlBounds.getMinY() - 20 - ConnectSongWithMp3Action.CONNECTSONGDIALOG_HEIGHT;
          connectSongWithMp3Action.connect(x, y, getSelectedSong());
        }
      }
    });

    tbaActions.getItems().add(new Separator());

    //Button Export origin
    /**Button btnExportWithChords = new Button ("Export origin");
     btnExportWithChords.setId("btnExportWithChords");
     btnExportWithChords.setGraphic(Consts.createImageView("export", iconSizeToolbar));
     tbaActions.getItems().add(btnExportWithChords);
     btnExportWithChords.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
    ExportAction exportAction = new ExportAction();
    exportAction.export(configuration, getCurrentSongs(), getExportName(), true, false);

    }
    });**/

    //Button Export transposed
    Button btnExport = new Button ("Export");
    btnExport.setTooltip(new Tooltip("Export to pdf"));
    btnExport.setId("btnExport");
    btnExport.setGraphic(Consts.createIcon("fa-desktop", iconSizeToolbar));
    tbaActions.getItems().add(btnExport);
    btnExport.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ExportAction exportAction = new ExportAction();
        exportAction.export(configuration, getCurrentSongs(), getExportName());
      }
    });



    Button btnUserAdmin = new Button("Users");
    btnUserAdmin.setTooltip(new Tooltip("Administrate all users"));
    btnUserAdmin.setId("btnUserAdmin");
    btnUserAdmin.setGraphic(Consts.createIcon("fa-user", iconSizeToolbar));
    tbaActions.getItems().add(btnUserAdmin);
    btnUserAdmin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Bounds controlBounds = UiUtils.getBounds(btnUserAdmin);
        Double x = controlBounds.getMinX() + 10;
        Double y = controlBounds.getMinY() - 20 - UsersAdminAction.HEIGHT;
        UsersAdminAction usersAdminAction = new UsersAdminAction();
        usersAdminAction.open(configuration, x, y);
      }
    });

    tbaActions.getItems().add(new Separator());


    Button btnPlayerBackward = new Button();
    btnPlayerBackward.setTooltip(new Tooltip("Step to the beginning of the current song"));
    btnPlayerBackward.setGraphic(Consts.createIcon("fa-backward", Consts.ICON_SIZE_VERY_SMALL));
    btnPlayerBackward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.beginning();
      }
    });
    tbaActions.getItems().add(btnPlayerBackward);

    Button btnPlayerStepBackward = new Button();
    btnPlayerStepBackward.setTooltip(new Tooltip("Step some seconds backward in the current song"));
    btnPlayerStepBackward.setGraphic(Consts.createIcon("fa-step-backward", Consts.ICON_SIZE_VERY_SMALL));
    btnPlayerStepBackward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.backward();
      }
    });
    tbaActions.getItems().add(btnPlayerStepBackward);



    Button btnPlayerPause = new Button();
    btnPlayerPause.setTooltip(new Tooltip("Pause playing the current song"));
    btnPlayerPause.setGraphic(Consts.createIcon("fa-pause", Consts.ICON_SIZE_VERY_SMALL));
    btnPlayerPause.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.pause();
      }
    });
    tbaActions.getItems().add(btnPlayerPause);

    Button btnPlayerPlay = new Button();
    btnPlayerPlay.setTooltip(new Tooltip("Play the current song"));
    btnPlayerPlay.setGraphic(Consts.createIcon("fa-play", Consts.ICON_SIZE_VERY_SMALL));
    btnPlayerPlay.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.play();
      }
    });
    tbaActions.getItems().add(btnPlayerPlay);

    Button btnPlayerStepForeward = new Button();
    btnPlayerStepForeward.setTooltip(new Tooltip("Step some seconds foreward in the current song"));
    btnPlayerStepForeward.setGraphic(Consts.createIcon("fa-step-forward", Consts.ICON_SIZE_VERY_SMALL));
    btnPlayerStepForeward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.forward();
      }
    });
    tbaActions.getItems().add(btnPlayerStepForeward);

    Button btnPlayerForeward = new Button();
    btnPlayerForeward.setTooltip(new Tooltip("Step to the end of the current song"));
    btnPlayerForeward.setGraphic(Consts.createIcon("fa-forward", Consts.ICON_SIZE_VERY_SMALL));
    btnPlayerForeward.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        mp3Player.end();
      }
    });
    tbaActions.getItems().add(btnPlayerForeward);


    tbaActions.getItems().add(new Separator());

    //Button Save
    Button btnSave = new Button ();
    btnSave.setTooltip(new Tooltip("Save all data"));
    btnSave.setGraphic(Consts.createIcon("fa-save", iconSizeToolbar));

    tbaActions.getItems().add(btnSave);
    btnSave.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        mp3Player.setFile(getMp3FileOfCurrentSong());
        configurationService.set(configuration);
      }
    });

    //Button Backup
    Button btnToCloud = new Button ();
    btnToCloud.setTooltip(new Tooltip("Upload data to dropbox"));
    btnToCloud.setGraphic(Consts.createIcon("fa-cloud-upload", iconSizeToolbar));
    tbaActions.getItems().add(btnToCloud);
    btnToCloud.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DropboxAdapter dropboxAdapter = new DropboxAdapter();
        dropboxAdapter.upload(configurationService.getConfigFile());
      }
    });

    //Button Recover
    Button btnFromCloud = new Button ();
    btnFromCloud.setTooltip(new Tooltip("Download data from dropbox"));
    btnFromCloud.setGraphic(Consts.createIcon("fa-cloud-download", iconSizeToolbar));
    tbaActions.getItems().add(btnFromCloud);
    btnFromCloud.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        DropboxAdapter dropboxAdapter = new DropboxAdapter();
        dropboxAdapter.download(configurationService.getConfigFile().getParentFile());
      }
    });

    Button btnRenumber = new Button ();
    btnRenumber.setTooltip(new Tooltip("Reindex the id's of all songs (1,2,3,4...)"));
    btnRenumber.setGraphic(Consts.createIcon("fa-sort-numeric-asc", iconSizeToolbar));
    tbaActions.getItems().add(btnRenumber);
    btnRenumber.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SongBook songBook = getCurrentSongBook();
        RenumberService renumberService = new RenumberService();
        renumberService.renumber(songBook);
      }
    });


    //Button Configurations
    Button btnConfigurations = new Button();
    btnConfigurations.setTooltip(new Tooltip("Configure adonai"));

    btnConfigurations.setGraphic(Consts.createIcon("fa-wrench", iconSizeToolbar));
    tbaActions.getItems().add(btnConfigurations);
    btnConfigurations.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Bounds controlBounds = UiUtils.getBounds(btnConfigurations);
        Double x = controlBounds.getMinX() - (ConfigurationAction.CONFIGDIALOG_WIDTH / 2);
        Double y = controlBounds.getMinY() - 20 - ConfigurationAction.CONFIGDIALOG_HEIGHT;

        ConfigurationAction configurationAction = new ConfigurationAction();
        configurationAction.openConfigurations(x, y);

      }
    });

    tbaActions.getItems().add(new Separator());


    //Button Exit
    Button btnExit = new Button();
    btnExit.setTooltip(new Tooltip("Exit adonai"));

    btnExit.setGraphic(Consts.createIcon("fa-power-off", iconSizeToolbar));
    tbaActions.getItems().add(btnExit);
    btnExit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        if (configurationService.hasChanged()) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Exit adonai");
          alert.setHeaderText("You have unsaved changes!");
          alert.setContentText("Do you want to save your changes?");

          Optional<ButtonType> result = alert.showAndWait();
          if (result.get() == ButtonType.OK){
            configurationService.set(configuration);
          }
        }

        System.exit(0);
      }
    });

    //Views
    lviSongs.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
            LOGGER.info("KeyHandler " + event.getCode().toString());

            if (event.getCode().equals(KeyCode.SPACE)) {
              SearchAction searchAction = new SearchAction();
              Point2D point2D = lviSongs.localToScreen(lviSongs.getWidth() - 305, lviSongs.getHeight() - 55);
              searchAction.open(filteredSongList, "", point2D.getX(), point2D.getY());
            }
          }
    });

    togSongbooks.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        selectSongbook();
      }
    });

    togSessions.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        selectSessions();
      }
    });

    togSession.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        Session sessionToSelect = currentSession;
        if (sessionToSelect == null && ! configuration.getSessions().isEmpty())
          sessionToSelect = configuration.getSessions().get(0);

        selectSession(sessionToSelect);
      }
    });

    lviSessions.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
          selectSession(lviSessions.getSelectionModel().getSelectedItem());
        }
      }
    });

    lviSession.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        LOGGER.info("mouseClicked on lviSession " + event.getClickCount());
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
          selectSong(lviSession.getSelectionModel().getSelectedItem());
        }
      }
    });

    lviSongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        LOGGER.info("mouseClicked on lviSongs " + event.getClickCount());
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
          selectSong(lviSongs.getSelectionModel().getSelectedItem());
        }
      }
    });

    refreshButtonState();

  }

  private File getMp3FileOfCurrentSong () {
    if (currentSong == null) {
      LOGGER.info("getMp3FileOfCurrentSong returns null (currentSong=null)");
      return null;
    }
    if  (currentSong.getAdditionals() == null) {
      LOGGER.info("getMp3FileOfCurrentSong returns null (getAdditionals=null)");
      return null;
    }
    AdditionalsImporter importer = new AdditionalsImporter();

    for (Additional nextAdditional: currentSong.getAdditionals()) {
      if (nextAdditional.getAdditionalType().equals(AdditionalType.AUDIO) && nextAdditional.getLink() != null) {
        File additionalFile = importer.getAdditionalFile(currentSong, nextAdditional);
        LOGGER.info("getMp3FileOfCurrentSong returns " + additionalFile);
        return additionalFile;
      }
    }

    LOGGER.info("getMp3FileOfCurrentSong returns null (not found) ");

    return null;
  }

  private void refreshButtonState () {
    boolean sessionsAvailable = configuration.getSessions().size() > 0;
    togSession.setVisible(sessionsAvailable);
  }

  private void selectSessions () {
    LOGGER.info("select sessions");
    currentSong = null;
    currentContent = MainPageContent.SESSIONS;
    lblCurrentType.setText("sessions" );
    lblCurrentEntity.setText("");
    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSessions);
    lviSessions.requestFocus();
    refreshListViews(null);
    lviSessions.getSelectionModel().selectFirst();
    togSessions.setSelected(true);
  }

  private void selectSongbook () {
    LOGGER.info("select songbook");
    currentSong = null;
    currentContent = MainPageContent.SONGBOOK;
    lblCurrentType.setText("songbook");
    lblCurrentEntity.setText("");
    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSongbook);
    lviSongs.requestFocus();
    refreshListViews(null);
    lviSongs.getSelectionModel().selectFirst();
    togSongbooks.setSelected(true);
  }

  private void selectSession (Session session) {
    LOGGER.info("select session " + session);
    if (session == null)
      return;

    currentContent = MainPageContent.SESSION;
    currentSong = null;
    currentSession = session;

    txtSessionName.setText(session.getName());

    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSessionDetails);
    lviSession.requestFocus();
    lblCurrentType.setText("session");
    lblCurrentEntity.setText(currentSession.getName().toUpperCase());
    refreshListViews(null);
    lviSession.getSelectionModel().selectFirst();
    togSession.setSelected(true);

  }

  private void selectSong (Song song) {
    LOGGER.info("Select song " + song);
    currentSong = song;
    currentContent = MainPageContent.SONG;
    SongEditor songEditor = new SongEditor(configuration, song);
    Parent songEditorPanel = songEditor.getPanel();
    songEditorPanel.setVisible(true);

    VBox.setVgrow(songEditorPanel, Priority.ALWAYS);
    panSongDetails.getChildren().clear();
    panSongDetails.getChildren().add(songEditorPanel);
    panSongDetails.toFront();
    panSongDetails.requestFocus();
    spDetails.getChildren().clear();
    spDetails.getChildren().add(panSongDetails);

    lblCurrentType.setText("song");
    lblCurrentEntity.setText(currentSong.getId() + " - " + currentSong.getName().toUpperCase());


    LOGGER.info("panSongDetails: " + panSongDetails.getWidth() + "-" + panSongDetails.getHeight());
    LOGGER.info("lviSongs: " + lviSongs.getWidth() + "-" + lviSongs.getHeight());

  }

  private SongBook getCurrentSongBook () {
    return configuration.getSongBooks().get(0);
  }

  private Session getCurrentSession () {
    if (currentContent == MainPageContent.SESSIONS) {
      return lviSessions.getSelectionModel().getSelectedItem();
    } else if (currentContent == MainPageContent.SESSION) {
      return currentSession;
    }
    else throw new IllegalStateException("Invalid page content " + currentContent);
  }

  private Collection<Song> getCurrentSongs () {
    if (currentContent == MainPageContent.SONGBOOK) {
      return getCurrentSongBook().getSongs();
    }
    else if (currentContent == MainPageContent.SESSION) {
      return sessionService.getSongs(currentSession, getCurrentSongBook());
    }
    else if (currentContent == MainPageContent.SONG) {
      return Arrays.asList(currentSong);
    }
    else throw new IllegalStateException("Invalid page content " + currentContent);
  }

  private Song getSelectedSong () {
    if (currentContent == MainPageContent.SONGBOOK) {
      return lviSongs.getSelectionModel().getSelectedItem();
    }
    else if (currentContent == MainPageContent.SESSION) {
      return lviSession.getSelectionModel().getSelectedItem();
    }
    else if (currentContent == MainPageContent.SONG) {
      return currentSong;
    }
    else throw new IllegalStateException("Invalid page content " + currentContent);
  }

  private String getExportName () {
    if (currentContent == MainPageContent.SONGBOOK) {
      return "songbook";
    }
    else if (currentContent == MainPageContent.SESSION) {
      return currentSession.getName();
    }
    else if (currentContent == MainPageContent.SONG) {
      return currentSong.getName();
    }
    else
      return "";
  }

  private void refreshListViews(Song selectSong) {
    LOGGER.info("Refresh views");
    filteredSongList = new FilteredList<Song>(FXCollections.observableArrayList(getCurrentSongBook().getSongs()), s->true);
    lviSongs.setItems(filteredSongList);
    List<Song> sessionSongs = currentSession != null ? sessionService.getSongs(currentSession, getCurrentSongBook()): new ArrayList<>();
    lviSession.setItems(FXCollections.observableArrayList(sessionSongs));
    lviSessions.setItems(FXCollections.observableArrayList(configuration.getSessions()));

    if (selectSong != null) {
      LOGGER.info("Select song " + selectSong );
      lviSongs.getSelectionModel().select(selectSong);
      lviSession.getSelectionModel().select(selectSong);
    }
  }

  public MainPageContent getCurrentContent() {
    return currentContent;
  }



}
