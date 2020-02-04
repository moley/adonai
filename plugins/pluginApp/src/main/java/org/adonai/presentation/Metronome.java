package org.adonai.presentation;

import javafx.scene.control.Control;
import org.controlsfx.control.Rating;

public class Metronome {

  private int waitInMillis = 0;


  private Rating rating = new Rating();



  public Metronome () {
    rating.setMax(4);
    rating.setRating(1);
    Thread metronomeThread = new Thread(new Runnable() {
      @Override public void run() {
        try {
          Thread.sleep(waitInMillis);
        } catch (InterruptedException e) {

        }
        if (rating.getRating() == rating.getMax())
          rating.setRating(1);
        else
          rating.setRating(rating.getRating() + 1);

      }
    });
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
