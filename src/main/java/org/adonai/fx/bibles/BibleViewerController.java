package org.adonai.fx.bibles;

import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.adonai.ApplicationEnvironment;
import org.adonai.bibles.BibleContainer;
import org.adonai.bibles.BibleService;
import org.adonai.fx.AbstractController;

public class BibleViewerController extends AbstractController {

  @FXML TextField txtSearch;

  @FXML Label lblPreview;

  private BibleContainer container = new BibleContainer();

  private BibleService bibleService = new BibleService();


  @Override public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);
    txtSearch.setOnAction(event -> lblPreview.setText("New data on " + new Date().toString()));

    container = bibleService.getAllBibles();
  }
}
