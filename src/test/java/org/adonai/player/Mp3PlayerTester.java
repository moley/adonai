package org.adonai.player;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.junit.Test;
import org.mockito.Mockito;

public class Mp3PlayerTester {

  public static void main(String[] args) {
    Mp3Player mp3Player = new Mp3Player();
    mp3Player.setFile(new File ("/Users/OleyMa/Music/iTunes/iTunes Media/Music/Compilations/Feiert Jesus! 27/08 Alles Was Atmet.m4a"));
    mp3Player.play();
  }
}
