package org.adonai.uitests;

import javafx.application.Application;
import javafx.stage.Stage;
import org.adonai.ui.screens.ScreenManager;

import java.io.IOException;

public class ScreenManagerStarter extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    ScreenManager screenManager = new ScreenManager();

  }

}
