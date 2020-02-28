package org.adonai.ui.imports;

import javafx.stage.Stage;
import org.adonai.model.Configuration;
import org.adonai.ui.imports.pages.ChooseImportTypePage;
import org.adonai.ui.imports.pages.ImportFromClipBoardPage;
import org.adonai.ui.imports.pages.NewSongPage;
import org.adonai.ui.imports.pages.PreviewPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class shows a satisfaction survey
 */
public class ImportWizard extends Wizard {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImportWizard.class);

  Stage owner;
  SongImportController importController;
  Configuration configuration;

  public ImportWizard(Stage owner, final SongImportController importController, Configuration configuration) {
    super(new ChooseImportTypePage(importController),
      new NewSongPage(importController),
      new ImportFromClipBoardPage(importController),
      new PreviewPage(importController, configuration));
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
    LOGGER.info("Closing stage " + owner.getTitle());
    owner.close();
  }
}
