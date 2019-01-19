package org.adonai.ui.mainpage;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
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
import org.adonai.model.*;
import org.adonai.services.AddSongService;
import org.adonai.services.RemoveSongService;
import org.adonai.services.SessionService;
import org.adonai.ui.Consts;
import org.adonai.ui.SongCellFactory;
import org.adonai.ui.editor.SongEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class MainPageController {

  @FXML
  private ToolBar tbaActions;

  @FXML
  private BorderPane border;

  @FXML
  private ToolBar tbLeft;

  @FXML
  private ToolBar tbRight;


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

  private MainPageContent currentContent;

  @FXML
  private Label lblCurrentEntity;

  private ConfigurationService configurationService = new ConfigurationService();

  private SessionService sessionService = new SessionService();

  private Configuration configuration;

  private int iconSizeToolbar = Consts.ICON_SIZE_SMALL;

  private static final Logger LOGGER = Logger.getLogger(MainPageController.class.getName());

  private Session currentSession = null;

  private Song currentSong = null;


  public void initialize() {
    lviSongs.setCellFactory(new SongCellFactory());
    lviSession.setCellFactory(new SongCellFactory());
    lviSessions.setPlaceholder(new Label("No sessions available, press + to add ones"));
    lviSession.setPlaceholder(new Label("No songs in session available, press + to add ones"));

    panSongDetails.setBackground(Background.EMPTY);

    configuration = configurationService.get();
    selectSongbook();

    refreshListViews(null);

    lviSongs.toFront();

    tbLeft.minWidthProperty().bind(Bindings.max(border.heightProperty(), tbLeft.prefWidthProperty()));
    tbRight.minWidthProperty().bind(Bindings.max(border.heightProperty(), tbRight.prefWidthProperty()));

    //Button Plus
    Button btnAdd = new Button();
    btnAdd.setGraphic(Consts.createImageView("plus", iconSizeToolbar));
    btnAdd.setId("btnPlus");
    tbaActions.getItems().add(btnAdd);
    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        //In Song details reimport content of current song
        if (currentContent.equals(MainPageContent.SONG)) {
          AddSongAction addSongHandler = new AddSongAction();
          addSongHandler.add(configuration, getCurrentSongBook(), new EventHandler<WindowEvent>() {
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
          addSongHandler.add(configuration, getCurrentSongBook(), new EventHandler<WindowEvent>() {
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
          Bounds controlBounds = tbaActions.localToScreen(tbaActions.getLayoutBounds());

          Double x = controlBounds.getMinX() + 10;
          Double y = controlBounds.getMinY() - 10 - SelectAction.SEARCHDIALOG_HEIGHT;
          selectSong.open(allSongs, x, y,  new SongCellFactory(), new EventHandler<WindowEvent>() {
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
    btnRemove.setGraphic(Consts.createImageView("minus", iconSizeToolbar));
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
          removeSongService.removeSong(lviSession.getSelectionModel().getSelectedItem(), getCurrentSongBook());
          refreshListViews(null);
        }
      }
    });
    tbaActions.getItems().add(new Separator());
    Button btnMp3 = new Button();
    btnMp3.setGraphic(Consts.createImageView(AdditionalType.AUDIO.name().toLowerCase(), iconSizeToolbar));
    tbaActions.getItems().add(btnMp3);
    btnMp3.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ConnectSongWithMp3Action connectSongWithMp3Action = new ConnectSongWithMp3Action();
        connectSongWithMp3Action.connect(getSelectedSong());
      }
    });

    tbaActions.getItems().add(new Separator());

    //Button Export with chords
    Button btnExportWithChords = new Button ("Export with");
    btnExportWithChords.setGraphic(Consts.createImageView("export", iconSizeToolbar));
    tbaActions.getItems().add(btnExportWithChords);
    btnExportWithChords.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ExportAction exportAction = new ExportAction();
        exportAction.export(configuration, getCurrentSongs(), getExportName(), true);

      }
    });

    //Button Export with chords
    Button btnExportWithoutChords = new Button ("Export without");
    btnExportWithoutChords.setGraphic(Consts.createImageView("export", iconSizeToolbar));
    tbaActions.getItems().add(btnExportWithoutChords);
    btnExportWithoutChords.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ExportAction exportAction = new ExportAction();
        exportAction.export(configuration, getCurrentSongs(), getExportName(), false);
      }
    });

    //Button Save
    Button btnSave = new Button ();
    btnSave.setGraphic(Consts.createImageView("save", iconSizeToolbar));
    tbaActions.getItems().add(btnSave);
    btnSave.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        configurationService.set(configuration);
      }
    });


    //Button Configurations
    Button btnConfigurations = new Button();
    btnConfigurations.setGraphic(Consts.createImageView("settings", iconSizeToolbar));
    tbaActions.getItems().add(btnConfigurations);
    btnConfigurations.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        ConfigurationAction configurationAction = new ConfigurationAction();
        configurationAction.openConfigurations();

      }
    });

    tbaActions.getItems().add(new Separator());


    //Button Exit
    Button btnExit = new Button();
    btnExit.setGraphic(Consts.createImageView("exit", iconSizeToolbar));
    tbaActions.getItems().add(btnExit);
    btnExit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
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
              searchAction.open();
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
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
          selectSong(lviSession.getSelectionModel().getSelectedItem());
        }
      }
    });

    lviSongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
          selectSong(lviSongs.getSelectionModel().getSelectedItem());
        }
      }
    });

    refreshButtonState();

  }

  private void refreshButtonState () {
    boolean sessionsAvailable = configuration.getSessions().size() > 0;
    togSession.setVisible(sessionsAvailable);
  }

  private void selectSessions () {
    LOGGER.info("select sessions");
    currentSong = null;
    currentContent = MainPageContent.SESSIONS;
    lblCurrentEntity.setText("SESSIONS" );
    spDetails.getChildren().clear();
    spDetails.getChildren().add(lviSessions);
    lviSessions.requestFocus();
    refreshListViews(null);
    lviSessions.getSelectionModel().selectFirst();
    togSessions.setSelected(true);
  }

  private void selectSongbook () {
    LOGGER.info("select songbook");
    currentSong = null;
    currentContent = MainPageContent.SONGBOOK;
    lblCurrentEntity.setText("SONGBOOK");
    spDetails.getChildren().clear();
    spDetails.getChildren().add(lviSongs);
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

    spDetails.getChildren().clear();
    spDetails.getChildren().add(lviSession);
    lviSession.requestFocus();
    lviSession.getSelectionModel().selectFirst();
    lblCurrentEntity.setText("SESSION '" + currentSession.getName() + "'");
    refreshListViews(null);
    togSession.setSelected(true);

  }

  private void selectSong (Song song) {
    LOGGER.info("Select song " + song);
    currentSong = song;
    currentContent = MainPageContent.SONG;
    SongEditor songEditor = new SongEditor(song);
    Parent songEditorPanel = songEditor.getPanel();
    VBox.setVgrow(songEditorPanel, Priority.ALWAYS);
    panSongDetails.getChildren().clear();
    panSongDetails.getChildren().add(songEditorPanel);
    panSongDetails.toFront();
    panSongDetails.requestFocus();
    lblCurrentEntity.setText("SONG '" + currentSong.getName() + "'");

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
    lviSongs.setItems(FXCollections.observableArrayList(getCurrentSongBook().getSongs()));
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
