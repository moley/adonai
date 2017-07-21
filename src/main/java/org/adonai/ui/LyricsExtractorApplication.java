package org.adonai.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.adonai.screens.ScreenManager;

import java.io.IOException;

/**
 * Created by OleyMa on 02.09.16.
 */
public class LyricsExtractorApplication extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    ScreenManager screenManager = new ScreenManager();


    Parent root = FXMLLoader.load(getClass().getResource("/org/lyricsextractor/sample.fxml"));

    Scene scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/lyricsextractor.css");
    screenManager.layoutOnScreen(primaryStage);

    primaryStage.setTitle("Lyrics Extractor");
    primaryStage.setScene(scene);
    primaryStage.show();



  }
}
