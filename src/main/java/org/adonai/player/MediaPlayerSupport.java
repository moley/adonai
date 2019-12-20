package org.adonai.player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerSupport {

  public MediaPlayer createMediaPlayer (final File mp3File) {
    return new MediaPlayer(new Media(mp3File.toURI().toString()));
  }


}
