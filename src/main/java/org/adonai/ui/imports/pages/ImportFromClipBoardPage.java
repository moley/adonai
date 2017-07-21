package org.adonai.ui.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.adonai.ui.imports.SongImportController;
import org.adonai.reader.text.TextfileReader;

import java.util.Arrays;

public class ImportFromClipBoardPage extends WizardPage {
  public final static String TITLE = "Import from clipboard";

  public ImportFromClipBoardPage(final SongImportController controller) {
    super(TITLE, controller);
  }

  private TextArea txaImport;

  Parent getContent() {
    txaImport = new TextArea();
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
    TextfileReader textfileReader = new TextfileReader();
    controller.setSongToImport(textfileReader.read(Arrays.asList(txaImport.getText().split("\n"))));
    navTo(PreviewPage.TITLE);
  }
}
