package org.adonai.ui.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.adonai.services.AddSongService;
import org.adonai.ui.imports.SongImportController;

public class NewSongPage extends WizardPage {

  public final static String TITLE = "New song";


  public NewSongPage(final SongImportController controller) {
    super(TITLE, controller);
  }

  private TextField txtTitle;
  @Override
  public Parent getContent() {
    txtTitle = new TextField();
    txtTitle.setPromptText("Please type the name of your new song");
    nextButton.setDisable(true);
    txtTitle.textProperty().addListener((observableValue, oldValue, newValue) -> {
      nextButton.setDisable(newValue.isEmpty());
      finishButton.setDisable(true);
    });

    return new VBox(
      5,
      txtTitle
    );
  }

  public void nextPage() {

    AddSongService addSongService = new AddSongService();
    controller.setSongToImport(addSongService.createSong(txtTitle.getText()));
    navTo(PreviewPage.TITLE);
  }
}
