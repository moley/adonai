package org.adonai.ui.imports;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.adonai.ui.imports.pages.ChooseImportTypePage;
import org.adonai.ui.imports.pages.ImportFromClipBoardPage;
import org.adonai.ui.imports.pages.NewSongPage;
import org.adonai.ui.imports.pages.PreviewPage;

import java.util.logging.Logger;

/**
 * This class shows a satisfaction survey
 */
public class ImportWizard extends Wizard {

  private static final Logger LOGGER = Logger.getLogger(ImportWizard.class.getName());

  Stage owner;
  SongImportController importController;

  public ImportWizard(Stage owner, final SongImportController importController) {
    super(new ChooseImportTypePage(importController),
      new NewSongPage(importController),
      new ImportFromClipBoardPage(importController),
      new PreviewPage(importController));
    this.importController = importController;
    this.owner = owner;
  }

  public void finish() {
    LOGGER.info("Finish called");

    close();
  }

  public void cancel() {
    LOGGER.info("Cancel called");
    importController.setSongToImport(null);
    close();
  }

  public void close () {
    owner.fireEvent( new WindowEvent( owner, WindowEvent.WINDOW_CLOSE_REQUEST));
  }
}
