package org.adonai.fx.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.imports.SongImportController;

public class NewSongPage extends WizardPage {

  public final static String TITLE = "New song";


  public NewSongPage(final ApplicationEnvironment applicationEnvironment, final Stage stage, final SongImportController controller) {
    super(applicationEnvironment, stage, TITLE, controller);
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
