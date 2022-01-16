package org.adonai.actions;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.ScreenManager;
import org.adonai.fx.UiUtils;
import org.adonai.fx.imports.ImportWizard;
import org.adonai.fx.imports.SongImportController;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongBook;


public class AddSongAction {

  private SongImportController songImportController;

  private ScreenManager screenManager = new ScreenManager();

  public void add(final ApplicationEnvironment applicationEnvironment, Configuration configuration,
                  SongBook songBook, EventHandler<WindowEvent> closeRequest) {

    songImportController = new SongImportController();
    songImportController.setSongBook(songBook);
    Stage stage = new Stage();
    screenManager.layoutOnScreen(stage, 50, applicationEnvironment.getAdminScreen());
    stage.setResizable(false);
    stage.initStyle(StageStyle.UNDECORATED);

    ImportWizard importWizard = new ImportWizard(applicationEnvironment, stage, songImportController, configuration);
    Scene scene = new Scene(importWizard, stage.getWidth(), stage.getHeight(), false);
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
