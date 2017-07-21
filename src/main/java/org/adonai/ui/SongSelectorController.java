package org.adonai.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by OleyMa on 15.09.16.
 */
public class SongSelectorController {

  private static final Logger LOGGER = Logger.getLogger(SongSelectorController.class.getName());


  @FXML
  TableView<Song> tabSongs;

  @FXML
  Button btnSelect;

  @FXML
  Button btnCancel;

  @FXML
  TextField txtSearch;

  ConfigurationService configurationService = new ConfigurationService();

  Configuration configuration;

  ObservableList<Song> shownSongs = FXCollections.observableArrayList();

  private TableColumn colTitle = new TableColumn("Title");
  private TableColumn colSong = new TableColumn("Song");
  private TableColumn colBackground = new TableColumn("Background");





  private void refreshListView(final String filter) {
    LOGGER.info("refresh listview with search " + filter);
    List<Song> filtered = new ArrayList<Song>();
    for (Song next : configuration.getSongBooks().get(0).getSongs()) {
      if (filter.trim().isEmpty() || next.getTitle().toUpperCase().contains(filter.toUpperCase())) {
        filtered.add(next);
        LOGGER.info(" Add " + next.getId() + "-" + next.getAttachedSong() + " to table");
      }
    }
    shownSongs.removeAll(shownSongs);
    shownSongs.addAll(filtered);
  }

  private void connectBackground ( ){
    Song selectedSong = tabSongs.getSelectionModel().getSelectedItem();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lyricsextractor/extensionselector.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    ExtensionSelectorController extensionSelectorController = loader.getController();
    extensionSelectorController.init(ExtensionType.BACKGROUND);

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add("/lyricsextractor.css");

    Stage stage = new Stage();
    stage.setTitle("Connect background with " + selectedSong.getTitle());
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent event) {
        if (selectedSong != null && extensionSelectorController.getSelectedExtension() != null) {
          String songExtension = extensionSelectorController.getSelectedExtension().getAbsolutePath();
          selectedSong.setAttachedBackground(songExtension);
          LOGGER.info("connect song " + selectedSong + " with backgroundfile " + extensionSelectorController.getSelectedExtension());
          refreshListView(txtSearch.getText());

        }
      }
    });

    stage.show();
  }

  private void connectSong ( ){
    Song selectedSong = tabSongs.getSelectionModel().getSelectedItem();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/lyricsextractor/extensionselector.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    ExtensionSelectorController extensionSelectorController = loader.getController();
    extensionSelectorController.init(ExtensionType.SONG);

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add("/lyricsextractor.css");

    Stage stage = new Stage();
    stage.setTitle("Connect song with " + selectedSong.getTitle());
    stage.setScene(scene);
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

      @Override
      public void handle(WindowEvent event) {

        if (selectedSong != null && extensionSelectorController.getSelectedExtension() != null) {
          String songExtension = extensionSelectorController.getSelectedExtension().getAbsolutePath();
          selectedSong.setAttachedSong(songExtension);
          LOGGER.info("connect song " + selectedSong + " with songfile " + extensionSelectorController.getSelectedExtension());
          refreshListView(txtSearch.getText());
        }
      }
    });

    stage.show();
  }

  @FXML
  public void initialize() {
    configuration = configurationService.get();

    tabSongs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tabSongs.setItems(shownSongs);


    colTitle.setPrefWidth(80);
    colTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
    colTitle.setId("title");

    colSong.setPrefWidth(10);
    colSong.setCellValueFactory(new PropertyValueFactory<Song, String>("attachedSongShortNotification"));
    colSong.setId("song");

    colBackground.setPrefWidth(10);
    colBackground.setCellValueFactory(new PropertyValueFactory<Song, String>("attachedBackground"));
    colBackground.setId("background");
    tabSongs.getColumns().addAll(colTitle, colSong, colBackground);

    tabSongs.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        LOGGER.info("mouse handled" +event.getClickCount());
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Node node = ((Node) event.getTarget());
          String id = node.getId() != null ? node.getId() : node.getParent().getId();
          LOGGER.info("Node: " + node + " with id " + id);
          if (id != null) {


            if (id.equals(colSong.getId())) {
              LOGGER.info("Attach song");
              connectSong();
            } else if (id.equals(colBackground.getId())) {
              LOGGER.info("Attach background");
              connectBackground();
            }
          }

        }
      }
    });

    txtSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
        refreshListView(txtSearch.getText());
        if (event.getCode().equals(KeyCode.DOWN))
          tabSongs.requestFocus();
      }
    });

    tabSongs.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER))
          close();
      }
    });

    refreshListView(txtSearch.getText());

    btnSelect.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        close();
      }
    });

    btnCancel.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        tabSongs.getSelectionModel().clearSelection();
        close();
      }
    });


  }

  public void close() {
    Stage stage = (Stage) btnSelect.getScene().getWindow();
    stage.fireEvent(
      new WindowEvent(
        stage,
        WindowEvent.WINDOW_CLOSE_REQUEST
      )
    );
  }

  public Collection<Song> getSelectedSongs() {
    return tabSongs.getSelectionModel().getSelectedItems();
  }
}
