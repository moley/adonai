package org.adonai.fx.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import org.adonai.ApplicationEnvironment;
import org.adonai.DateUtil;
import org.adonai.actions.SearchAction;
import org.adonai.export.presentation.Page;
import org.adonai.fx.renderer.SongCellRenderer;
import org.adonai.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongEditor extends VBox {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongEditor.class);

  private DateUtil dateUtil = new DateUtil();

  private Pane leftPane = new Pane();

  private Pane rightPane = new Pane();

  private List<Page> panes = new ArrayList<Page>();

  private int currentIndex = 0;

  private final ObjectProperty<Song> currentSongProperty = new SimpleObjectProperty<Song>();

  //private Metronome metronome = new Metronome();

  private ApplicationEnvironment applicationEnvironment;

  public SongEditor(final ApplicationEnvironment applicationEnvironment, final List<Page> pages) {
    this.applicationEnvironment = applicationEnvironment;
    this.panes = pages;
    currentIndex = 0;
  }

  public ObjectProperty<Song> currentSongProperty () {
    return currentSongProperty;
  }

  private void disableAndRemove() {
    if (!leftPane.getChildren().isEmpty())
      leftPane.getChildren().get(0).setVisible(false);

    if (!rightPane.getChildren().isEmpty())
      rightPane.getChildren().get(0).setVisible(false);
    leftPane.getChildren().clear();
    rightPane.getChildren().clear();
  }

  private void enableAndAdd() {

    if (currentIndex < panes.size())
      leftPane.getChildren().add(panes.get(currentIndex).getPane());
    if (currentIndex + 1 < panes.size())
      rightPane.getChildren().add(panes.get(currentIndex + 1).getPane());

    currentSongProperty.set(panes.get(currentIndex).getSong());

    if (!leftPane.getChildren().isEmpty())
      leftPane.getChildren().get(0).setVisible(true);

    if (!rightPane.getChildren().isEmpty())
      rightPane.getChildren().get(0).setVisible(true);
  }

  private void selectSong (Song song) {
    if (song != null) {
      for (Page nextPage : panes) {
        if (nextPage.getSong().equals(song)) {
          disableAndRemove();
          currentSongProperty.set(song);
          currentIndex = panes.indexOf(nextPage);
          enableAndAdd();
          return;
        }
      }
    }

    //default if song not found or no song selected so far
    song = panes.get(0).getSong();
    disableAndRemove();
    currentSongProperty.set(song);
    currentIndex = 0;
    enableAndAdd();
  }

  public void show() {
    selectSong(applicationEnvironment.getCurrentSong());

    Scene scene = getScene();

    if (scene == null)
      throw new IllegalStateException("Scene not yet set");

    scene.setOnKeyReleased(event -> {
       LOGGER.info("onKeyPressed " + event.getCode() + " recieved");
      if (event.getCode().equals(KeyCode.M)) {
        //metronome.setBpm(panes.get(currentIndex).getSong().getSpeed());
        //metronome.setVisible(! metronome.isVisible());
      } else if (event.getCode().equals(KeyCode.RIGHT)) {

        disableAndRemove();

        if (currentIndex < panes.size() - 1)
          currentIndex++;

        LOGGER.info("toLeft (" + currentIndex + ")");

        if (panes.get(currentIndex).getSong() != null)
          //metronome.setBpm(panes.get(currentIndex).getSong().getSpeed());

          enableAndAdd();

      } else if (event.getCode().equals(KeyCode.LEFT)) {
        disableAndRemove();

        if (currentIndex > 0)
          currentIndex--;

        LOGGER.info("toRight (" + currentIndex + ")");

        if (panes.get(currentIndex).getSong() != null)
          //metronome.setBpm(panes.get(currentIndex).getSong().getSpeed());

          enableAndAdd();

      } else if (event.getCode().equals(KeyCode.S)) {
        SearchAction<Song> searchAction = new SearchAction();
        FilteredList <Song> filteredList = new FilteredList<Song>(FXCollections.observableArrayList(applicationEnvironment.getSongsOfCurrentScope()));
        filteredList.setPredicate(song -> true);
        searchAction.open(applicationEnvironment, cellrendere -> new SongCellRenderer(), new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            Song selectedSong = searchAction.getSelectedItem();
            if (selectedSong != null) {
              applicationEnvironment.setCurrentSong(selectedSong);
              selectSong(selectedSong);
            }
          }
        }, filteredList, "", 50, 100);
      }

    });

    HBox hbox = new HBox();

    hbox.setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, null)));

    hbox.setFillHeight(true);
    hbox.setAlignment(Pos.CENTER_LEFT);
    hbox.getChildren().add(leftPane);
    hbox.getChildren().add(rightPane);
    HBox.setHgrow(leftPane, Priority.ALWAYS);
    HBox.setHgrow(rightPane, Priority.ALWAYS);

    getChildren().add(hbox);
    VBox.setVgrow(hbox, Priority.ALWAYS);

  }
}