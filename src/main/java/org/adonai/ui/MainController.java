package org.adonai.ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportException;
import org.adonai.export.pdf.PdfExporter;
import org.adonai.model.*;
import org.adonai.screens.ScreenManager;
import org.adonai.services.AddSongService;
import org.adonai.ui.editor.SongEditor;
import org.adonai.ui.imports.ImportWizard;
import org.adonai.ui.imports.SongImportController;
import org.adonai.ui.settings.SettingsController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Created by OleyMa on 02.09.16.
 */
public class MainController {

  private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

  private ScreenManager screenManager = new ScreenManager();

  @FXML
  ListView<Song> lviAllSongs;

  @FXML
  ListView<Session> lviSessions;

  @FXML
  ListView<Song> lviSessionDetails;

  @FXML
  TextField txtSessionName;

  ConfigurationService configurationService = new ConfigurationService();

  Configuration configuration;


  @FXML
  ToolBar tbaActions;

  @FXML
  TabPane tabView;

  Player playMP3;



  @FXML
  public void initialize() {
    configuration = configurationService.get();
    lviSessions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    lviSessions.setPlaceholder(new Label ("No sessions available, please create one"));



    lviSessionDetails.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    lviAllSongs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    lviAllSongs.setPlaceholder(new Label ("No songs available, please create one"));

    lviAllSongs.setCellFactory(new SongCellFactory());

    TableColumn colBackground = new TableColumn();
    colBackground.setPrefWidth(10);
    colBackground.setCellValueFactory(new PropertyValueFactory<Song, String>("attachedBackground"));
    colBackground.setId("background");
    lviSessionDetails.setCellFactory(new SongCellFactory());
    lviSessionDetails.setPlaceholder(new Label ("No songs available in session, please add one"));
    lviSessions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Session>() {
      @Override
      public void changed(ObservableValue<? extends Session> observable, Session oldValue, Session newValue) {

        if (oldValue != null) {
          txtSessionName.textProperty().unbindBidirectional(oldValue.getNameProperty());
        }

        if (newValue != null) {
          txtSessionName.textProperty().bindBidirectional(newValue.getNameProperty());
        }


      }
    });


    txtSessionName.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
          lviSessionDetails.requestFocus();
          lviSessionDetails.getSelectionModel().selectFirst();
        }
        else
          if (txtSessionName.getCaretPosition() == 0 && event.getCode() == KeyCode.LEFT)
            lviSessions.requestFocus();
      }
    });

    lviAllSongs.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.RIGHT) {
          System.out.println ("Left or right on lviAllSongs");
          tabView.getSelectionModel().select(1);
          lviSessions.requestFocus();
        }
      }
    });

    reload();

    lviSessions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Session>() {
      @Override
      public void changed(ObservableValue<? extends Session> observable, Session oldValue, Session newValue) {
        reload();

      }
    });

    lviSessions.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        System.out.println("KeyListener " + event.getText() + "-" + event.getCode());
        if (event.getCode() == KeyCode.LEFT) {
          tabView.getSelectionModel().select(0);
          lviAllSongs.requestFocus();
        }
        if (event.getCode() == KeyCode.RIGHT) {
          txtSessionName.requestFocus();
        }
        if (event.getText().equals("+"))
          addSession();

        if (event.getText().equals("-"))
          removeSession();


      }
    });

    lviSessionDetails.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          stepToSongEditor(lviSessionDetails.getSelectionModel().getSelectedItem());
        }
      }
    });

    lviSessionDetails.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        System.out.println("KeyListener " + event.getText() + " from " + event.getSource() + "-" + lviSessionDetails);

        if (event.getCode() == KeyCode.UP)
          txtSessionName.requestFocus();
        else
        if (event.getCode() == KeyCode.LEFT)
          lviSessions.requestFocus();
        else if (event.getText().equals("+"))
          addSongToSession();
        else if (event.getText().equals("-"))
          removeSongFromSession();

        else if (event.getCode().equals(KeyCode.ENTER)) {
          Song songToEdit = lviSessionDetails.getSelectionModel().getSelectedItem();
          stepToSongEditor(songToEdit);
        }

      }
    });

    lviAllSongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          stepToSongEditor(lviAllSongs.getSelectionModel().getSelectedItem());
        }
      }
    });

    Button btnSave = new Button("Save");
    btnSave.setGraphic(Consts.createImageView("save"));
    tbaActions.getItems().add(btnSave);
    btnSave.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Saving in " + configurationService.getConfigFile().getAbsolutePath());
        configurationService.save();

      }
    });

    Button btnPlaySong = new Button("Play song");
    btnPlaySong.setGraphic(Consts.createImageView("play"));
    tbaActions.getItems().add(btnPlaySong);
    btnPlaySong.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (playMP3 != null) {
          LOGGER.info("Stop mp3");
          playMP3.close();
          playMP3 = null;
        } else {
          Song song = lviSessionDetails.getSelectionModel().getSelectedItem();


          if (song == null)
            return;

          LOGGER.info("Start mp3 " + song.getAttachedSong());

          FileInputStream fis = null;
          try {
            fis = new FileInputStream(song.getAttachedSong());
            playMP3 = new Player(fis);
            CompletableFuture futureCount = CompletableFuture.supplyAsync(
              () -> {
                startMp3();
                return 10;
              });

          } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
          } catch (JavaLayerException e) {
            throw new IllegalStateException(e);
          }
        }


      }
    });

    Button btnCreatePlayList = new Button("Playlist");
    btnCreatePlayList.setGraphic(Consts.createImageView("playlist"));
    tbaActions.getItems().add(btnCreatePlayList);
    btnCreatePlayList.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        throw new IllegalStateException("Implement selection dialog");
        //PlaylistExport export = new PlaylistExport();
        //export.export(configuration, playlistpath);


      }
    });

    Button btnExportSessionWithChords = new Button("Export session with chords");
    btnExportSessionWithChords.setGraphic(Consts.createImageView("export"));
    tbaActions.getItems().add(btnExportSessionWithChords);
    btnExportSessionWithChords.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //saving the changes to a file
        //creating an FileOutputStream object
        Session session = lviSessions.getSelectionModel().getSelectedItem();

        PdfExporter writer = new PdfExporter();
        Collection<Song> sessionSongs = getSongs(session);

        ExportConfiguration exportConfiguration = writer.getPdfDocumentBuilder().getDefaultConfiguration();
        exportConfiguration.setWithChords(true);

        File exportFile = new File (configuration.getExportPathAsFile(), session.getName() + "_Chords.pdf");
        exportFile.getParentFile().mkdirs();
        try {
          writer.export(sessionSongs, exportFile, exportConfiguration);
        } catch (ExportException e) {
          throw new IllegalStateException(e);
        }
      }
    });

    Button btnExportSessionWithoutChords = new Button("Export session without chords");
    btnExportSessionWithoutChords.setGraphic(Consts.createImageView("export"));
    tbaActions.getItems().add(btnExportSessionWithoutChords);
    btnExportSessionWithoutChords.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //saving the changes to a file
        //creating an FileOutputStream object
        Session session = lviSessions.getSelectionModel().getSelectedItem();

        PdfExporter writer = new PdfExporter();
        Collection<Song> sessionSongs = getSongs(session);

        ExportConfiguration exportConfiguration = writer.getPdfDocumentBuilder().getDefaultConfiguration();
        exportConfiguration.setWithChords(false);

        try {
          File exportFile = new File (configuration.getExportPathAsFile(), session.getName()+ ".pdf");
          exportFile.getParentFile().mkdirs();
          writer.export(sessionSongs, exportFile, exportConfiguration);
        } catch (ExportException e) {
          throw new IllegalStateException(e);
        }

      }
    });

    Button btnExportAll = new Button("Exporter all");
    btnExportAll.setGraphic(Consts.createImageView("export"));

    tbaActions.getItems().add(btnExportAll);
    btnExportAll.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        throw new IllegalStateException("Implement selection dialog");

        /**PptExporter writer = new PptExporter();
        try {
          writer.export(configuration.getSongBooks().get(0).getSongs(), file, new ExportConfiguration());
        } catch (ExportException e) {
          throw new IllegalStateException(e);
        }**/

      }
    });

    Button btnConfigurations = new Button("Configurations");
    btnConfigurations.setGraphic(Consts.createImageView("settings"));
    tbaActions.getItems().add(btnConfigurations);
    btnConfigurations.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/adonai/settings.fxml"));
          Parent root = loader.load();

          SettingsController settingsController = loader.getController();

          Stage stage = new Stage();
          stage.setResizable(true);


          stage.setTitle("Configurations");
          Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);

          scene.getStylesheets().add("/adonai.css");
          stage.setScene(scene);
          screenManager.layoutOnScreen(stage);

          stage.show();

        } catch (IOException e) {
          throw new IllegalStateException(e);
        }

      }
    });

    reload();

    lviSessions.getSelectionModel().selectFirst();
    lviAllSongs.getSelectionModel().selectFirst();


    MenuItem menuItemNewSong = new MenuItem("New song");
    menuItemNewSong.setGraphic(Consts.createImageView("songPlus"));
    menuItemNewSong.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        SongBook songBook = getCurrentSongBook();
        AddSongService addSongService = new AddSongService();
        Song newSong = new Song();
        newSong.setTitle("NEW SONG");
        addSongService.addSong(newSong, songBook);
        reload();
        stepToSongEditor(newSong);
      }
    });

    MenuItem menuItemCopySong = new MenuItem("Copy song");
    menuItemCopySong.setGraphic(Consts.createImageView("copy"));
    menuItemCopySong.setId("menuItemCopySong");
    menuItemCopySong.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        SongBook songBook = getCurrentSongBook();
        Song selectedSong = getSelectedSong();
        AddSongService addSongService = new AddSongService();
        Song newSong = new Song();
        newSong.setTitle("Copy of " + selectedSong.getTitle());
        newSong.getSongParts().addAll(selectedSong.getSongParts());
        addSongService.addSong(newSong, songBook);
        reload();
        stepToSongEditor(newSong);
      }
    });

    MenuItem menuItemImportSong = new MenuItem("Import song");
    menuItemImportSong.setGraphic(Consts.createImageView("import"));
    menuItemImportSong.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        stepToImport();
      }
    });

    ContextMenu contextMenu = new ContextMenu(menuItemNewSong, menuItemImportSong, menuItemCopySong);

    lviAllSongs.setContextMenu(contextMenu);

    MenuItem menuItemNewSession = new MenuItem("New session");
    menuItemNewSession.setId("menuItemNewSession");
    menuItemNewSession.setGraphic(Consts.createImageView("sessionPlus"));
    menuItemNewSession.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        addSession();
      }
    });

    MenuItem menuItemRemoveSession = new MenuItem("Remove session");
    menuItemRemoveSession.setId("menuItemRemoveSession");
    menuItemRemoveSession.setGraphic(Consts.createImageView("sessionMinus"));
    menuItemRemoveSession.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        removeSession();
      }
    });

    ContextMenu contextMenuSesssionsOverview = new ContextMenu(menuItemNewSession, menuItemRemoveSession);

    lviSessions.setContextMenu(contextMenuSesssionsOverview);

    MenuItem menuItemAddSongToSession = new MenuItem("Add song");
    menuItemAddSongToSession.setId("menuItemAddSongToSession");
    menuItemAddSongToSession.setGraphic(Consts.createImageView("songPlus"));
    menuItemAddSongToSession.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        addSongToSession();
      }
    });

    MenuItem menuItemRemoveSongFromSession = new MenuItem("Remove session");
    menuItemRemoveSongFromSession.setId("menuItemRemoveSession");
    menuItemRemoveSongFromSession.setGraphic(Consts.createImageView("songMinus"));
    menuItemRemoveSongFromSession.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        removeSongFromSession();
      }
    });

    ContextMenu contextMenuSessionDetails = new ContextMenu(menuItemAddSongToSession, menuItemRemoveSongFromSession);

    lviSessionDetails.setContextMenu(contextMenuSessionDetails);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        lviAllSongs.requestFocus();
        lviAllSongs.getSelectionModel().selectFirst();

      }
    });

  }

  private SongBook getCurrentSongBook() {
    return configuration.getSongBooks().get(0);
  }

  private Song getSelectedSong () {
    return lviAllSongs.getSelectionModel().getSelectedItem();
  }


  private void reload() {
    System.out.println("reload");

    if (configuration.getSongBooks().isEmpty())
      configuration.getSongBooks().add(new SongBook());

    lviSessions.setItems(FXCollections.observableArrayList(configuration.getSessions()));

    lviAllSongs.setItems(FXCollections.observableArrayList(configuration.getSongBooks().get(0).getSongs()));

    if (lviSessions.getSelectionModel().getSelectedItem() == null)
      lviSessions.getSelectionModel().selectFirst();

    Session selectedSession = lviSessions.getSelectionModel().getSelectedItem();
    Collection<Song> sessionSongs = getSongs(selectedSession);
    System.out.println("Reload selected " + selectedSession.getName() + "-" + sessionSongs.size());
    lviSessionDetails.setItems(FXCollections.observableArrayList(sessionSongs));



  }

  private Collection<Song> getSongs(final Session session) {
    Collection<Song> sessionSongs = new ArrayList<Song>();
    for (Integer nextSong : session.getSongs()) {
      Song foundSong = configuration.getSongBooks().get(0).findSong(nextSong);
      sessionSongs.add(foundSong);
    }
    return sessionSongs;

  }

  private void addSession() {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
    String now = formatter.format(LocalDate.now());
    Session session = new Session();
    session.setName("New session " + now);
    System.out.println("addSession" + session.getName());
    configuration.getSessions().add(session);
    lviSessions.getSelectionModel().select(session);
    reload();
    lviSessionDetails.requestFocus();
  }

  private void removeSession() {
    int selected = lviSessions.getSelectionModel().getSelectedIndex();
    Session session = lviSessions.getSelectionModel().getSelectedItem();

    if (session != null) {
      configuration.getSessions().remove(session);
    }

    reload();

    lviSessions.requestFocus();
    int newSelectedIndex = Integer.max(0, selected - 1);
    LOGGER.info("Selecting session " + newSelectedIndex);
    lviSessions.getSelectionModel().select(newSelectedIndex);

  }

  private void stepToImport() {
    Stage stage = new Stage();

    SongImportController songImportController = new SongImportController();
    songImportController.setSongBook(getCurrentSongBook());
    songImportController.setSongToImport(null);

    ImportWizard importWizard = new ImportWizard(stage, songImportController);
    Scene scene = new Scene(importWizard, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT, false);
    scene.getStylesheets().add("/adonai.css");

    stage.setOnCloseRequest(event -> {
      stage.close();
      Song importedSong = songImportController.getSongToImport();
      if (importedSong != null)
        stepToSongEditor(importedSong);
    });
    stage.setTitle("Import new song");
    stage.setScene(scene);
    ScreenManager screenManager = new ScreenManager();
    screenManager.layoutOnScreen(stage);
    stage.show();

  }

  private void stepToSongEditor(final Song songToEdit) {
    SongEditor songEditor = new SongEditor(songToEdit, false);
    Parent editor = songEditor.getPanel();

    Scene scene = new Scene(editor, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT, false);
    scene.getStylesheets().add("/adonai.css");
    Stage stage = new Stage();
    stage.setOnCloseRequest(event -> {
      songEditor.save();
    });
    stage.setTitle(songToEdit.getId() + " - " + songToEdit.getTitle());
    stage.setScene(scene);
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        if (songEditor.hasChange()) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setHeaderText("Unsaved changes");
          alert.setContentText("You have unsaved changes in your song " + songToEdit.getTitle() + ". Do you want to save them?");
          alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
              System.out.println(songEditor.getSong().toString());
              songEditor.save();
            }
          });
        }
      }
    });
    ScreenManager screenManager = new ScreenManager();
    screenManager.layoutOnScreen(stage);
    stage.show();
  }

  private void addSongToSession() {
    Parent root = null;
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/adonai/songselector.fxml"));
      root = loader.load();


      SongSelectorController songSelectorController = loader.getController();

      Stage stage = new Stage();
      stage.setResizable(true);


      stage.setTitle("Select song");
      Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);

      scene.getStylesheets().add("/adonai.css");
      stage.setScene(scene);
      screenManager.layoutOnScreen(stage);


      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
          System.out.println("Selected songs: " + songSelectorController.getSelectedSongs().size());
          Session selectedSession = lviSessions.getSelectionModel().getSelectedItem();
          for (Song next : songSelectorController.getSelectedSongs()) {
            selectedSession.getSongs().add(next.getId());

          }
          reload();
          lviSessionDetails.getSelectionModel().selectLast();

        }
      });
      stage.show();


    } catch (IOException e) {
      throw new IllegalStateException(e);
    }


  }

  private void removeSongFromSession() {
    Session selectedSession = lviSessions.getSelectionModel().getSelectedItem();
    int selected = lviSessionDetails.getSelectionModel().getSelectedIndex();
    Song songToRemove = lviSessionDetails.getSelectionModel().getSelectedItem();
    if (selectedSession != null && songToRemove != null) {
      LOGGER.info("Remove " + songToRemove.getTitle());
      selectedSession.getSongs().remove(songToRemove.getId());
      reload();
      lviSessionDetails.getSelectionModel().selectIndices(Integer.max(selected - 1, 0));
    } else
      LOGGER.info("selectedSession " + selectedSession + "- songToRemove " + songToRemove);
  }

  private void startMp3() {
    try {
      playMP3.play();
    } catch (JavaLayerException e) {
      throw new IllegalStateException(e);
    }

  }


}
