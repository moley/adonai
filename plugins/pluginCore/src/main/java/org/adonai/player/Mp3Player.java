package org.adonai.player;

import java.io.File;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mp3Player {



  private MediaPlayer mediaPlayer;

  private MediaPlayerSupport mediaPlayerSupport = new MediaPlayerSupport();

  private File currentMp3File;

  private static final Logger LOGGER = LoggerFactory.getLogger(Mp3Player.class.getName());

  public void setFile (final File mp3File) {
    if (mp3File == null)
      throw new IllegalArgumentException("Parameter mp3File must not be null");

    if (! mp3File.exists())
      throw new IllegalStateException("Mp3file " + mp3File.getAbsolutePath() + " does not exist");


    if (mediaPlayer != null && currentMp3File != null && ! currentMp3File.equals(mp3File)) {
      LOGGER.info("Close former player with file " + currentMp3File.getAbsolutePath());
      currentMp3File = null;
      mediaPlayer.dispose();

    }

    if (currentMp3File == null) {
      LOGGER.info("Create new player with file " + mp3File.getAbsolutePath());
      currentMp3File = mp3File;
      mediaPlayer = mediaPlayerSupport.createMediaPlayer(mp3File);
      mediaPlayer.setVolume(0.5); //TODO
      mediaPlayer.setOnReady(new Runnable() {
        @Override public void run() {
          beginning();

        }
      });
      if (mediaPlayer.getError() != null)
        throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());
    }

  }

  public void play () {
    LOGGER.info("Play " + currentMp3File.getAbsolutePath());

    mediaPlayer.play();


    if (mediaPlayer.getError() != null) {
      LOGGER.error(mediaPlayer.getError().getLocalizedMessage(), mediaPlayer.getError());
      throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());
    }



  }

  public void pause () {
    LOGGER.info("Pause " + currentMp3File.getAbsolutePath());
    mediaPlayer.pause();
    if (mediaPlayer.getError() != null)
      throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());
  }

  public void backward () {
    LOGGER.info("Backward " + currentMp3File.getAbsolutePath());
    mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(new Duration(10*1000)));if (mediaPlayer.getError() != null)
      throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());

  }

  public void forward () {
    LOGGER.info("Foreward " + currentMp3File.getAbsolutePath());
    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(new Duration(10*1000)));
    if (mediaPlayer.getError() != null)
      throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());
  }

  public void beginning () {
    LOGGER.info("To beginning " + currentMp3File.getAbsolutePath());
    mediaPlayer.seek(new Duration(0));
    if (mediaPlayer.getError() != null)
      throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());
  }

  public void end () {
    LOGGER.info("To end " + currentMp3File.getAbsolutePath());
    mediaPlayer.seek(mediaPlayer.getMedia().getDuration());
    if (mediaPlayer.getError() != null)
      throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());

  }

  public void setMediaPlayer(MediaPlayer mediaPlayer) {
    this.mediaPlayer = mediaPlayer;
  }

  public void setCurrentMp3File(File currentMp3File) {
    this.currentMp3File = currentMp3File;
  }

  public void setMediaPlayerSupport (MediaPlayerSupport mediaPlayerSupport) {
    this.mediaPlayerSupport = mediaPlayerSupport;
  }
}
