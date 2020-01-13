package org.adonai.presentation;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import org.adonai.ui.Consts;
import org.adonai.ui.UiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlView extends Parent {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControlView.class);


  private Pane leftPane = new Pane();

  private Pane rightPane = new Pane();

  private List<Pane> panes = new ArrayList<>();

  private int currentIndex = 0;


  public ControlView(final List<Pane> panes) {
    this.panes = panes;
    currentIndex = 0;
  }

  private void disableAndRemove () {
    leftPane.getChildren().get(0).setVisible(false);
    rightPane.getChildren().get(0).setVisible(false);
    leftPane.getChildren().clear();
    rightPane.getChildren().clear();
  }

  private void enableAndAdd () {
    leftPane.getChildren().add(panes.get(currentIndex));
    rightPane.getChildren().add(panes.get(currentIndex + 1));
    leftPane.getChildren().get(0).setVisible(true);
    rightPane.getChildren().get(0).setVisible(true);
  }

  public void show()  {
    leftPane.getChildren().add(panes.get(0));
    rightPane.getChildren().add(panes.get(1));
    panes.get(0).setVisible(true);
    panes.get(1).setVisible(true);

    Scene scene = getScene();

    scene.setOnKeyPressed(event -> {
      LOGGER.info("Hello");
      if (event.getCode().equals(KeyCode.RIGHT)) {

        disableAndRemove();

        if (currentIndex < panes.size() - 1)
          currentIndex ++;

        LOGGER.info("toLeft (" + currentIndex + ")");

        enableAndAdd();

      } else if (event.getCode().equals(KeyCode.LEFT)) {
        disableAndRemove();

        if (currentIndex > 0)
          currentIndex --;

        LOGGER.info("toRight (" + currentIndex + ")");
        enableAndAdd();

      } else if (event.getCode().equals(KeyCode.ESCAPE)) {
        UiUtils.close(scene.getWindow());
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

    scene.setRoot(hbox);

  }
}