package org.adonai.fx.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.adonai.ApplicationEnvironment;
import org.adonai.reader.text.TextfileReaderParam;
import org.adonai.fx.imports.SongImportController;
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
    VBox.setVgrow(txaImport, Priority.ALWAYS);
    nextButton.setDisable(true);
    txaImport.textProperty().addListener((observableValue, oldValue, newValue) -> {
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
