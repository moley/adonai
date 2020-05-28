package org.adonai.presentation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import org.adonai.fx.UiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ControlView extends Parent {

  private static final Logger LOGGER = LoggerFactory.getLogger(ControlView.class);

  private DateUtil dateUtil = new DateUtil();

  private Pane leftPane = new Pane();

  private Pane rightPane = new Pane();

  private List<Page> panes = new ArrayList<Page>();

  private int currentIndex = 0;

  private Metronome metronome = new Metronome();

  public ControlView(final List<Page> pages) {
    this.panes = pages;
    currentIndex = 0;
  }

  private void disableAndRemove () {
    if (! leftPane.getChildren().isEmpty())
    leftPane.getChildren().get(0).setVisible(false);

    if (!rightPane.getChildren().isEmpty())
      rightPane.getChildren().get(0).setVisible(false);
    leftPane.getChildren().clear();
    rightPane.getChildren().clear();
  }

  private void enableAndAdd () {

    if (currentIndex < panes.size())
      leftPane.getChildren().add(panes.get(currentIndex).getPane());
    if (currentIndex + 1 < panes.size())
      rightPane.getChildren().add(panes.get(currentIndex + 1).getPane());

    if (! leftPane.getChildren().isEmpty())
      leftPane.getChildren().get(0).setVisible(true);

    if (!rightPane.getChildren().isEmpty())
      rightPane.getChildren().get(0).setVisible(true);
  }

  public void show()  {
    enableAndAdd();

    Scene scene = getScene();
    scene.setOnKeyPressed(event -> {
      LOGGER.info("onKeyPressed " + event.getCode() + " recieved");
      if (event.getCode().equals(KeyCode.M)) {
        metronome.setBpm(panes.get(currentIndex).getSong().getSpeed());
        metronome.setVisible(! metronome.isVisible());
      }
      else if (event.getCode().equals(KeyCode.RIGHT)) {

        disableAndRemove();

        if (currentIndex < panes.size() - 1)
          currentIndex ++;

        LOGGER.info("toLeft (" + currentIndex + ")");

        if (panes.get(currentIndex).getSong() != null)
          metronome.setBpm(panes.get(currentIndex).getSong().getSpeed());

        enableAndAdd();

      } else if (event.getCode().equals(KeyCode.LEFT)) {
        disableAndRemove();

        if (currentIndex > 0)
          currentIndex --;

        LOGGER.info("toRight (" + currentIndex + ")");

        if (panes.get(currentIndex).getSong() != null)
          metronome.setBpm(panes.get(currentIndex).getSong().getSpeed());

        enableAndAdd();

      } else if (event.getCode().equals(KeyCode.ESCAPE)) {
        metronome.stop();
        UiUtils.close(scene.getWindow());
      }

    });




    HBox hboxHeader = new HBox();
    hboxHeader.setSpacing(20);

    hboxHeader.getChildren().add(metronome.getControl());

    Label lblTime = new Label("Anfang");
    HBox.setMargin(lblTime, new Insets(5, 5, 5, 5));
    hboxHeader.getChildren().add(lblTime);
    Thread timeThread = new Thread(new Runnable() {
      @Override public void run() {
        while (true) {

          Platform.runLater(new Runnable() {
            @Override public void run() {
              lblTime.setText(dateUtil.getTimeAsString(LocalTime.now()));
            }
          });
          try {
            Thread.sleep(600);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }

      }
    });
    timeThread.start();



    HBox hbox = new HBox();

    hbox.setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), CornerRadii.EMPTY, null)));

    hbox.setFillHeight(true);
    hbox.setAlignment(Pos.CENTER_LEFT);
    hbox.getChildren().add(leftPane);
    hbox.getChildren().add(rightPane);
    HBox.setHgrow(leftPane, Priority.ALWAYS);
    HBox.setHgrow(rightPane, Priority.ALWAYS);

    VBox vbox = new VBox();
    vbox.getChildren().add(hboxHeader);
    vbox.getChildren().add(hbox);
    VBox.setVgrow(hbox, Priority.ALWAYS);


    scene.setRoot(vbox);

  }
}
