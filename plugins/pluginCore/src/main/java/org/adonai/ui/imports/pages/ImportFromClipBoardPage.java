package org.adonai.ui.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.adonai.ApplicationEnvironment;
import org.adonai.reader.text.TextfileReaderParam;
import org.adonai.ui.imports.SongImportController;
import org.adonai.reader.text.TextfileReader;

import java.util.Arrays;

public class ImportFromClipBoardPage extends WizardPage {
  public final static String TITLE = "Import from clipboard";

  public ImportFromClipBoardPage(final ApplicationEnvironment applicationEnvironment, final SongImportController controller) {
    super(applicationEnvironment, TITLE, controller);
  }

  private TextArea txaImport;

  Parent getContent() {
    txaImport = new TextArea();
    txaImport.setUserData("importsongwizard.txaImport");
    txaImport.setWrapText(true);
    txaImport.setPromptText("Please copy your song with Copy and Paste into this textfield and press Next");
    nextButton.setDisable(true);
    txaImport.textProperty().addListener((observableValue, oldValue, newValue) -> {
      nextButton.setDisable(newValue.isEmpty());
    });
    return new VBox(
      5,
      new Label("Please copy your song with Copy and Paste into this textfield and press Next"),
      txaImport
    );
  }

  void nextPage() {
    importTextArea();
    navTo(PreviewPage.TITLE);
  }

  void finish () {
    importTextArea();
    super.finish();
  }

  void importTextArea () {
    if (! txaImport.getText().trim().isEmpty()) {
      TextfileReader textfileReader = new TextfileReader();
      TextfileReaderParam param = new TextfileReaderParam();
      controller.setSongToImport(textfileReader.read(Arrays.asList(txaImport.getText().split("\n")), param));
    }


  }


}
