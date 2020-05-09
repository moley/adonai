package org.adonai.export.presentation;

import javafx.scene.layout.Pane;
import org.adonai.model.Song;
import org.adonai.model.SongStructItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Page {

  private static final Logger LOGGER = LoggerFactory.getLogger(Page.class);


  private final Pane pane;

  private Song song;

  private SongStructItem songStructItem;

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

  public SongStructItem getSongStructItem() {
    return songStructItem;
  }

  public void setSongStructItem(SongStructItem songStructItem) {
    this.songStructItem = songStructItem;
  }
}
