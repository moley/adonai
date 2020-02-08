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



  public Metronome () {

    Thread metronomeThread = new Thread(new Runnable() {
      @Override public void run() {
        while (true) {

          if (waitInMillis == 0) {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              LOGGER.info("No bpm set");

            }
            return;
          }
          LOGGER.info("Next");
          try {
            Thread.sleep(waitInMillis);
          } catch (InterruptedException e) {

          }
          showing = !showing;

          String stying = showing ? "-fx-background-color: #000000;" : "-fx-background-coloe: #FFFFFF;";

          rating.setStyle(stying);
        }

      }
    });

    metronomeThread.start();
  }

  public Control getControl () {
    return rating;
  }

  public void setBpm (final int bpm) {

    //60 * 1000 ms
    //60 bpm 60 pro minute, 1 pro Sekunde
    //120 bpm 120 pro Minute, 2 pro Sekunde
    waitInMillis = (60 * 1000) / bpm;
  }


}
