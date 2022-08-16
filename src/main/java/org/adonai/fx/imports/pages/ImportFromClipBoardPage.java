package org.adonai.fx.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.reader.text.TextfileReaderParam;
import org.adonai.fx.imports.SongImportController;
import org.adonai.reader.text.TextfileReader;

import java.util.Arrays;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportFromClipBoardPage extends WizardPage {
  public final static String TITLE = "Import from clipboard";

  private Logger log = LoggerFactory.getLogger(ImportFromClipBoardPage.class);

  public ImportFromClipBoardPage(final ApplicationEnvironment applicationEnvironment, final Stage stage, final SongImportController controller) {
    super(applicationEnvironment, stage, TITLE, controller);
  }

  private TextArea txaImport;

  Parent getContent() {
    txaImport = new TextArea();
    txaImport.setUserData("importsongwizard.txaImport");
    txaImport.setWrapText(true);
    txaImport.setPromptText("Please copy your song with Copy and Paste into this textfield and press Next");
    txaImport.setStyle("-fx-font-family: monospaced;");
    VBox.setVgrow(txaImport, Priority.ALWAYS);
    nextButton.setDisable(true);
    txaImport.textProperty().addListener((observableValue, oldValue, newValue) -> {
      log.info("newValue = " + newValue);
      txaImport.setText(newValue.replace("\n\n", "\n"));
      nextButton.setDisable(newValue.isEmpty());
    });
    VBox vbox = new VBox(
      10,
      new Label("Please copy your song with Copy and Paste into this textfield and press Next"),
      txaImport
    );

    return vbox;
  }

  void nextPage() {
    if (importTextArea())
      navTo(PreviewPage.TITLE);
  }

  void finish () {
    if (importTextArea())
      super.finish();
  }

  boolean importTextArea () {
    if (! txaImport.getText().trim().isEmpty()) {
      TextfileReader textfileReader = new TextfileReader();
      TextfileReaderParam param = new TextfileReaderParam();
      try {
        controller.setSongToImport(textfileReader.read(Arrays.asList(txaImport.getText().split("\n")), param));
        return true;
      } catch (Exception e) {
        Notifications.create().text("Error on import: " + e.getLocalizedMessage()).showError();
        return false;
      }
    }

    return false;
  }


}
