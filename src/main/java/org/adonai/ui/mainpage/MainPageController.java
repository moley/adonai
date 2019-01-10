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
import org.adonai.actions.SearchAction;
import org.adonai.actions.add.AddSongAction;
import org.adonai.model.*;
import org.adonai.services.SessionService;
import org.adonai.ui.Consts;
import org.adonai.ui.SongCellFactory;
import org.adonai.ui.editor.SongEditor;

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

    lviSongs.setItems(FXCollections.observableArrayList(configuration.getSongBooks().get(0).getSongs()));
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
        if (currentContent.equals(MainPageContent.SESSION) || currentContent.equals(MainPageContent.SONGBOOK)) {
          AddSongAction addSongHandler = new AddSongAction();
          addSongHandler.add(configuration, getCurrentSongBook());
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

  private void selectSessions () {
    currentSong = null;
    lblCurrentEntity.setText("SESSIONS" );
    lviSessions.toFront();
    lviSessions.requestFocus();
    currentContent = MainPageContent.SESSIONS;
  }

  private void selectSongbook () {
    currentSong = null;
    lblCurrentEntity.setText("SONGBOOK");
    lviSongs.toFront();
    lviSongs.requestFocus();
    currentContent = MainPageContent.SONGBOOK;
  }

  private void refreshButtonState () {
    boolean sessionsAvailable = configuration.getSessions().size() > 0;
    togSession.setVisible(sessionsAvailable);
  }

  private void selectSession (Session session) {
    if (session == null)
      return;

    currentContent = MainPageContent.SESSION;
    currentSong = null;
    currentSession = session;

    lviSession.setItems(FXCollections.observableArrayList(sessionService.getSongs(session, getCurrentSongBook())));

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


}
