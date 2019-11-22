package org.adonai;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.adonai.model.*;
import org.adonai.ui.Consts;
import org.adonai.ui.editor2.SongEditor;

import java.io.IOException;

/**
 * Created by OleyMa on 22.11.16.
 */
public class SongEditorStarter extends Application {



    private static int songnumber = 0;

    public static void main(String[] args) {
      if (args.length > 0)
        SongEditorStarter.songnumber = Integer.valueOf(args[0]).intValue();

      launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

      ConfigurationService configurationService = new ConfigurationService();
      Configuration configuration = configurationService.get();
      SongBook songBook = configuration.getSongBooks().get(0);
      Song song = songBook.getSongs().get(songnumber);
      SongEditor songEditor = new SongEditor(configuration, song);

      Parent editor = songEditor.getPanel();



      Scene scene = new Scene(editor, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT, false);

      scene.getStylesheets().add("/adonai.css");

      primaryStage.setScene(scene);

      primaryStage.show();


    }

}
