package org.adonai.actions;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.ui.Consts;
import org.adonai.ui.UiUtils;
import org.adonai.ui.imports.ImportWizard;
import org.adonai.ui.imports.SongImportController;


public class AddSongAction {

  private SongImportController songImportController;

  public void add(final Double x, final Double y,
                  SongBook songBook, EventHandler<WindowEvent> closeRequest) {

    songImportController = new SongImportController();
    songImportController.setSongBook(songBook);
    Stage stage = new Stage();
    stage.setWidth(Consts.getDefaultWidth());
    stage.setHeight(Consts.getDefaultHeight());
    stage.setResizable(false);
    stage.initStyle(StageStyle.UNDECORATED);
    stage.setX(x);
    stage.setY(y);

    ImportWizard importWizard = new ImportWizard(stage, songImportController);
    Scene scene = new Scene(importWizard, Consts.getDefaultWidth(), Consts.getDefaultHeight(), false);
    UiUtils.applyCss(scene);

    stage.setOnHiding(closeRequest);
    stage.setTitle("Import new song");
    stage.setScene(scene);
    stage.showAndWait();
  }

  public Song getNewSong () {
    return songImportController.getSongToImport();
  }
}
