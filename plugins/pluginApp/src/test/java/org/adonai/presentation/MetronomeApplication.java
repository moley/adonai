package org.adonai.presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetronomeApplication extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(MetronomeApplication.class);

  public static void main(String[] args) {
    launch(args);
  }

  @Override public void start(Stage primaryStage) throws IOException, InterruptedException {

    Metronome metronome = new Metronome();
    metronome.setBpm(60);
    primaryStage.setScene(new Scene(metronome.getControl()));
    primaryStage.show();
  }
}