package org.adonai.ui.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
    txtTitle.setUserData("importsongwizard.txtTitle");
    txtTitle.selectAll();
    txtTitle.setTextFormatter(new TextFormatter<>((change) -> {
      change.setText(change.getText().toUpperCase());
      return change;
    }));
    txtTitle.textProperty().bindBidirectional(controller.getSongToImport().titleProperty());

    return new VBox(
      5,
      txtTitle
    );
  }

  public void nextPage() {
    navTo(PreviewPage.TITLE);
  }

}
