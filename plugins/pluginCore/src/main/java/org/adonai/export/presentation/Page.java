package org.adonai.export.presentation;

import javafx.scene.layout.Pane;
import org.adonai.model.Song;

public class Page {

  private final Pane pane;

  private Song song;

  public Page () {
    this.pane = new Pane();
  }

  public Song getSong() {
    return song;
  }

  public Pane getPane() {
    return pane;
  }

  public void setSong(Song song) {
    this.song = song;
  }
}
