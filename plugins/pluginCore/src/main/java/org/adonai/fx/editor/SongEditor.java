package org.adonai.fx.editor;

import java.util.ArrayList;
import java.util.List;
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
import org.adonai.DateUtil;
import org.adonai.export.presentation.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongEditor extends VBox {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongEditor.class);

  private DateUtil dateUtil = new DateUtil();

  private Pane leftPane = new Pane();

  private Pane rightPane = new Pane();

  private List<Page> panes = new ArrayList<Page>();

  private int currentIndex = 0;

  //private Metronome metronome = new Metronome();

  public SongEditor(final List<Page> pages) {
    this.panes = pages;
    currentIndex = 0;
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

    if (!leftPane.getChildren().isEmpty())
      leftPane.getChildren().get(0).setVisible(true);

    if (!rightPane.getChildren().isEmpty())
      rightPane.getChildren().get(0).setVisible(true);
  }

  public void show() {
    enableAndAdd();

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
