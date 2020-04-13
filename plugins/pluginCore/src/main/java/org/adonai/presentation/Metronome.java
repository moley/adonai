package org.adonai.presentation;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Metronome {

  private int waitInMillis = 0;

  private static final Logger LOGGER = LoggerFactory.getLogger(Metronome.class);

  private Button rating = new Button();

  boolean showing = true;

  private Thread metronomeThread;

  private boolean metronomeAvailable = true;

  public Metronome() {
    rating.setMinWidth(50);
    rating.setFocusTraversable(false);
    setVisible(false);

    metronomeThread = new Thread(new Runnable() {
      @Override public void run() {
        LOGGER.info("Metronome started");
        while (metronomeAvailable) {
          LOGGER.info("Metronome running (" + waitInMillis + ")");

          if (waitInMillis == 0) {
            rating.setVisible(false);
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              LOGGER.info("No bpm set");

            }
          } else {
            LOGGER.info("Next tick");
            try {
              Thread.sleep(waitInMillis);
            } catch (InterruptedException e) {

            }
            if (rating.isVisible()) {
              showing = !showing;

              String stying = showing ? "-fx-background-color: #000000;" : "-fx-background-color: #FFFFFF;";

              rating.setStyle(stying);
            }
          }
        }

      }
    });

    metronomeThread.start();
  }

  public void setVisible(final boolean visible) {
    rating.setVisible(visible);
  }

  public boolean isVisible () {
    return rating.isVisible();
  }

  public Control getControl() {
    return rating;
  }

  public void setBpm(final Integer bpm) {
    LOGGER.info("set bpm " + bpm);
    if (bpm == null || bpm.intValue() == 0)
      waitInMillis = 0;
    else
      //60 * 1000 ms
      //60 bpm 60 pro minute, 1 pro Sekunde
      //120 bpm 120 pro Minute, 2 pro Sekunde
      waitInMillis = (60 * 1000) / bpm;
  }

  public void stop() {
    LOGGER.info ("Stop metronome");
    metronomeAvailable = false;
  }
}
