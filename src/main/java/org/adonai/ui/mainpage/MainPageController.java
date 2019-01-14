package org.adonai.ui.mainpage;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.adonai.actions.ConfigurationAction;
import org.adonai.actions.ExportAction;
import org.adonai.actions.SearchAction;
import org.adonai.actions.add.AddSongAction;
import org.adonai.actions.add.AddSongToSessionAction;
import org.adonai.model.*;
import org.adonai.services.AddSongService;
import org.adonai.services.SessionService;
import org.adonai.ui.Consts;
import org.adonai.ui.SongCellFactory;
import org.adonai.ui.editor.SongEditor;

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

    panSongDetails.setBackground(Background.EMPTY);

    configuration = configurationService.get();

    refreshListViews(null);

    lviSessions.setItems(FXCollections.observableArrayList(configuration.getSessions()));

    lviSongs.toFront();

    tbLeft.minWidthProperty().bind(Bindings.max(border.heightProperty(), tbLeft.prefWidthProperty()));
    tbRight.minWidthProperty().bind(Bindings.max(border.heightProperty(), tbRight.prefWidthProperty()));

    //Button Plus
    Button btnAdd = new Button();
    btnAdd.setGraphic(Consts.createImageView("plus", iconSizeToolbar));
    tbaActions.getItems().add(btnAdd);
    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        //Create or import a new song
        if (currentContent.equals(MainPageContent.SONGBOOK) || currentContent.equals(MainPageContent.SONG)) {
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
        } else if (currentContent.equals(MainPageContent.SESSION)) {
          AddSongToSessionAction addSongToSessionAction = new AddSongToSessionAction();
          List<Song> allSongs = getCurrentSongBook().getSongs();
          addSongToSessionAction.open(allSongs, togSession, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
              AddSongAction addSongHandler = new AddSongAction();
              addSongHandler.add(configuration, getCurrentSongBook(), new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                  addSongToSessionAction.getSelectMask().getStage().close();

                  Song song = addSongHandler.getNewSong();
                  LOGGER.info("New song " + song + " created");
                  if (song != null) {
                    SongBook songBook = getCurrentSongBook();
                    AddSongService addSongService = new AddSongService(); //Add new song to songbook
                    addSongService.addSong(song, songBook);
                    SessionService sessionService = new SessionService(); //Add new song to session
                    sessionService.addSong(currentSession, song);
                    refreshListViews(song);                               //Refresh list data and select the new song in editor
                    selectSong(song);
                  }
                }
              });

            }
          });

        } else if (currentContent.equals(MainPageContent.SESSIONS)) {

        }
      }
    });

    //Button Minus
    Button btnRemove = new Button();
    btnRemove.setGraphic(Consts.createImageView("minus", iconSizeToolbar));
    tbaActions.getItems().add(btnRemove);
    btnRemove.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //TODO
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
    selectSongbook();

  }

  private void refreshButtonState () {
    boolean sessionsAvailable = configuration.getSessions().size() > 0;
    togSession.setVisible(sessionsAvailable);
  }

  private void selectSessions () {
    currentSong = null;
    lblCurrentEntity.setText("SESSIONS" );
    lviSessions.setItems(FXCollections.observableArrayList(configuration.getSessions()));
    lviSessions.toFront();
    lviSessions.requestFocus();
    currentContent = MainPageContent.SESSIONS;
  }

  private void selectSongbook () {
    currentSong = null;
    currentContent = MainPageContent.SONGBOOK;
    lblCurrentEntity.setText("SONGBOOK");
    lviSongs.setItems(FXCollections.observableArrayList(getCurrentSongs()));
    lviSongs.toFront();
    lviSongs.requestFocus();
  }

  private void selectSession (Session session) {
    if (session == null)
      return;

    currentContent = MainPageContent.SESSION;
    currentSong = null;
    currentSession = session;

    lviSession.toFront();
    lviSession.requestFocus();
    lblCurrentEntity.setText("SESSION '" + currentSession.getName() + "'");
    togSession.setSelected(true);
  }

  private void selectSong (Song song) {
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

  private Collection<Song> getCurrentSongs () {
    if (currentContent == MainPageContent.SONGBOOK) {
      return getCurrentSongBook().getSongs();
    }
    else if (currentContent == MainPageContent.SESSION) {
      return sessionService.getSongs(currentSession, getCurrentSongBook());
    }
    else
      return Arrays.asList();
  }

  private String getExportName () {
    if (currentContent == MainPageContent.SONGBOOK) {
      return "songbook";
    }
    else if (currentContent == MainPageContent.SESSION) {
      return currentSession.getName();
    }
    else
      return "";
  }

  private void refreshListViews(Song selectSong) {
    LOGGER.info("Refresh songbook");
    lviSongs.setItems(FXCollections.observableArrayList(getCurrentSongs()));
    lviSession.setItems(FXCollections.observableArrayList(getCurrentSongs()));
    if (selectSong != null) {
      LOGGER.info("Select song " + selectSong );
      lviSongs.getSelectionModel().select(selectSong);
    }
  }


}
