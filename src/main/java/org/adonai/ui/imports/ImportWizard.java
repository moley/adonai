package org.adonai.ui.imports;

import javafx.stage.Stage;
import org.adonai.model.SongBook;
import org.adonai.ui.imports.pages.ChooseImportTypePage;
import org.adonai.ui.imports.pages.ImportFromClipBoardPage;
import org.adonai.ui.imports.pages.ImportFromWorkshipTogetherPage;
import org.adonai.ui.imports.pages.PreviewPage;
import org.adonai.model.Song;
import org.adonai.services.AddSongService;

/**
 * This class shows a satisfaction survey
 */
public class ImportWizard extends Wizard {
  Stage owner;
  SongImportController importController;

  public ImportWizard(Stage owner, final SongImportController importController) {
    super(new ChooseImportTypePage(importController),
      new ImportFromClipBoardPage(importController),
      new ImportFromWorkshipTogetherPage(importController),
      new PreviewPage(importController));
    this.importController = importController;
    this.owner = owner;
  }

  public void finish() {

    Song song = importController.getSongToImport();
    SongBook songBook = importController.getSongBook();
    AddSongService addSongService = new AddSongService();
    addSongService.addSong(song, songBook);
    owner.close();
  }

  public void cancel() {
    System.out.println("Cancelled");
    owner.close();
  }
}
