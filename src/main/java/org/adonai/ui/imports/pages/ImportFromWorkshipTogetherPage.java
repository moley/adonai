package org.adonai.ui.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.adonai.ui.imports.SongImportController;
import org.adonai.model.Song;
import org.adonai.reader.text.WebSiteReader;

public class ImportFromWorkshipTogetherPage extends WizardPage {

  public final static String TITLE = "Import from www.worshiptogether.com";

  private TextField txtUrl;


  public ImportFromWorkshipTogetherPage(final SongImportController controller) {
    super(TITLE, controller);
  }



  Parent getContent() {
    txtUrl = new TextField();

    txtUrl.setPromptText("Please copy the url to the song you want to import and press Next");
    nextButton.setDisable(true);
    txtUrl.textProperty().addListener((observableValue, oldValue, newValue) -> {
      nextButton.setDisable(newValue.isEmpty());
    });

    return new VBox(
      5,
      new Label("Please copy the url to the song you want to import and press Next"),
      txtUrl
    );
  }

  void nextPage() {

    WebSiteReader webSiteReader = new WebSiteReader();
    Song importSong = webSiteReader.readFromUrl(txtUrl.getText());
    controller.setSongToImport(importSong);

    navTo(PreviewPage.TITLE);
  }
}
