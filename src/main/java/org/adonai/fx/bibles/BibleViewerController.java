package org.adonai.fx.bibles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.adonai.ApplicationEnvironment;
import org.adonai.bibles.BibleContainer;
import org.adonai.bibles.BibleService;
import org.adonai.fx.AbstractController;

public class BibleViewerController extends AbstractController {

  @FXML TextField txtSearch;

  private BibleContainer container = new BibleContainer();

  private BibleService bibleService = new BibleService();


  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);

    container = bibleService.getAllBibles();
  }
}
