package org.adonai.fx.bibles;

import javafx.application.Application;
import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.Mask;
import org.adonai.fx.MaskLoader;

public class BibleViewerApplication  extends Application {
  @Override public void start(Stage primaryStage) throws Exception {

    MaskLoader<BibleViewerController> bibleViewerControllerMaskLoader = new MaskLoader<>();
    Mask<BibleViewerController> bibleviewer = bibleViewerControllerMaskLoader.loadWithStage("bibleviewer",
        getClass().getClassLoader());

    bibleviewer.getController().setApplicationEnvironment(new ApplicationEnvironment());

    primaryStage.setScene(bibleviewer.getScene());
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(BibleViewerApplication.class, args);
  }

}
