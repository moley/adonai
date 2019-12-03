package org.adonai.player;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class Mp3Player {

  private MediaPlayer mediaPlayer;

  private File currentMp3File;

  private static final Logger LOGGER = Logger.getLogger(Mp3Player.class.getName());


  public void setFile (final File mp3File) {
    if (! mp3File.exists())
      throw new IllegalStateException("Mp3file " + mp3File.getAbsolutePath() + " does not exist");

    if (mediaPlayer != null && ! currentMp3File.equals(mp3File)) {
      LOGGER.info("Close former player with file " + currentMp3File.getAbsolutePath());
      currentMp3File = null;
      mediaPlayer.dispose();

    }

    if (currentMp3File == null) {
      LOGGER.info("Create new player with file " + mp3File.getAbsolutePath());
      currentMp3File = mp3File;
      mediaPlayer = new MediaPlayer(new Media(mp3File.toURI().toString()));
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


    if (mediaPlayer.getError() != null)
      throw new IllegalStateException("Error playing song " + currentMp3File.getAbsolutePath(), mediaPlayer.getError());



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
}
