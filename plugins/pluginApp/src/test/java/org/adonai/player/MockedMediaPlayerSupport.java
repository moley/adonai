package org.adonai.player;

import java.io.File;
import javafx.scene.media.MediaPlayer;

public class MockedMediaPlayerSupport extends MediaPlayerSupport {

  private MediaPlayer mediaPlayer;

  public MediaPlayer createMediaPlayer (final File mp3File) {
    return mediaPlayer;
  }

  public void setMediaPlayer(MediaPlayer mediaPlayer) {
    this.mediaPlayer = mediaPlayer;
  }
}
