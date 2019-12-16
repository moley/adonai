package org.adonai.player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.junit.Test;
import org.mockito.Mockito;

public class Mp3PlayerTest {

  private File example = new File ("src/test/resources/file_example_MP3_700KB.mp3");

  @Test(expected = IllegalStateException.class)
  public void setFileNotExisting () {
    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setFile(new File("test.mp3"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void setFileNull () {
    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setFile(null);
  }

  @Test
  public void setFileValid () {
    MediaPlayer mockedMediaPlayer = Mockito.mock(MediaPlayer.class);

    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setFile(example, mockedMediaPlayer);

    Mockito.verify(mockedMediaPlayer, Mockito.times(1)).setVolume(Mockito.anyDouble());
  }

  @Test
  public void play () {
    MediaPlayer mockedMediaPlayer = Mockito.mock(MediaPlayer.class);

    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setCurrentMp3File(example);
    mp3Player.setMediaPlayer(mockedMediaPlayer);
    mp3Player.play();

    Mockito.verify(mockedMediaPlayer, Mockito.times(1)).play();


  }

  @Test
  public void pause () {
    MediaPlayer mockedMediaPlayer = Mockito.mock(MediaPlayer.class);

    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setCurrentMp3File(example);
    mp3Player.setMediaPlayer(mockedMediaPlayer);
    mp3Player.pause();

    Mockito.verify(mockedMediaPlayer, Mockito.times(1)).pause();
  }

  @Test
  public void backward () {
    Duration duration = new Duration(100);
    MediaPlayer mockedMediaPlayer = Mockito.mock(MediaPlayer.class);
    Mockito.when(mockedMediaPlayer.getCurrentTime()).thenReturn(duration);

    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setCurrentMp3File(example);
    mp3Player.setMediaPlayer(mockedMediaPlayer);
    mp3Player.backward();

    Mockito.verify(mockedMediaPlayer, Mockito.times(1)).seek(Mockito.any());
  }

  @Test
  public void forward () {
    Duration duration = new Duration(100);
    MediaPlayer mockedMediaPlayer = Mockito.mock(MediaPlayer.class);
    Mockito.when(mockedMediaPlayer.getCurrentTime()).thenReturn(duration);

    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setCurrentMp3File(example);
    mp3Player.setMediaPlayer(mockedMediaPlayer);
    mp3Player.forward();

    Mockito.verify(mockedMediaPlayer, Mockito.times(1)).seek(Mockito.any());
  }

  @Test
  public void beginning () {
    MediaPlayer mockedMediaPlayer = Mockito.mock(MediaPlayer.class);

    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setCurrentMp3File(example);
    mp3Player.setMediaPlayer(mockedMediaPlayer);
    mp3Player.beginning();

    Mockito.verify(mockedMediaPlayer, Mockito.times(1)).seek(Mockito.any());
  }

  @Test
  public void end () {
    MediaPlayer mockedMediaPlayer = Mockito.mock(MediaPlayer.class);
    Media mockedMedia = Mockito.mock(Media.class);
    Mockito.when(mockedMedia.getDuration()).thenReturn(new Duration (100));

    Mockito.when(mockedMediaPlayer.getMedia()).thenReturn(mockedMedia);

    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setCurrentMp3File(example);
    mp3Player.setMediaPlayer(mockedMediaPlayer);
    mp3Player.end();

    Mockito.verify(mockedMediaPlayer, Mockito.times(1)).seek(Mockito.any());
  }
}
