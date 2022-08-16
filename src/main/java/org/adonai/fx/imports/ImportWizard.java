package org.adonai.fx.imports;

import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.imports.pages.ImportFromFilePage;
import org.adonai.model.Configuration;
import org.adonai.fx.imports.pages.ChooseImportTypePage;
import org.adonai.fx.imports.pages.ImportFromClipBoardPage;
import org.adonai.fx.imports.pages.NewSongPage;
import org.adonai.fx.imports.pages.PreviewPage;
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

  public ImportWizard(final ApplicationEnvironment applicationEnvironment, Stage owner, final SongImportController importController, Configuration configuration) {
    super(new ChooseImportTypePage(applicationEnvironment, owner, importController),
      new ImportFromFilePage(applicationEnvironment, owner, importController),
      new NewSongPage(applicationEnvironment, owner, importController),
      new ImportFromClipBoardPage(applicationEnvironment, owner, importController),
      new PreviewPage(applicationEnvironment, owner, importController, configuration));
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
