package org.adonai;

import javafx.application.Application;
import javafx.stage.Stage;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;
import org.adonai.fx.scripture.ScriptureController;

public class ScriptureDialog extends Application {

  @Override public void start(Stage primaryStage) throws Exception {

    MaskLoader<ScriptureController> maskLoader = new MaskLoader<>();
    Mask<ScriptureController> mask = maskLoader.loadWithStage("scripture");
    primaryStage.setScene(mask.getScene());
    primaryStage.setWidth(800);
    primaryStage.setHeight(1024);
    primaryStage.setResizable(false);

    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(ScriptureDialog.class);
  }
}