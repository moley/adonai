package org.adonai.actions.add;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.ui.Consts;
import org.adonai.ui.imports.ImportWizard;
import org.adonai.ui.imports.SongImportController;

public class AddSongAction implements AddContentHandler {

  public final static int ADD_SONG_DIALOG_WIDTH = 800;
  public final static int ADD_SONG_DIALOG_HEIGHT = 400;

  @Override
  public void add(Configuration configuration, SongBook songBook) {
    SongImportController songImportController = new SongImportController();
    songImportController.setSongBook(songBook);
    songImportController.setSongToImport(null);
    Stage stage = new Stage();

    ImportWizard importWizard = new ImportWizard(stage, songImportController);
    Scene scene = new Scene(importWizard, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT, false);
    scene.getStylesheets().add("/adonai.css");

    stage.setOnCloseRequest(event -> {
      stage.close();
      Song importedSong = songImportController.getSongToImport();
      System.out.println ("Imported song=" + importedSong.toString());
    });
    stage.setTitle("Import new song");
    stage.setScene(scene);
    stage.showAndWait();
  }
}
