package org.adonai.fx.imports.pages;

import com.glaforge.i18n.io.CharsetToolkit;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.imports.SongImportController;
import org.adonai.reader.chordpro.ChordProFileReader;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.in;

public class ImportFromFilePage extends WizardPage {
  public final static String TITLE = "Import from file";

  private Logger log = LoggerFactory.getLogger(ImportFromFilePage.class);



  public ImportFromFilePage(final ApplicationEnvironment applicationEnvironment, final Stage stage, final SongImportController controller) {
    super(applicationEnvironment, stage, TITLE, controller);

  }

  private final FileChooser fileChooser = new FileChooser();

  private TextField txtFilename;
  private Button btnFromFile;

  Parent getContent() {
    txtFilename = new TextField();
    txtFilename.setUserData("importsongwizard.txtFile");
    txtFilename.setStyle("-fx-font-family: monospaced;");
    VBox.setVgrow(txtFilename, Priority.ALWAYS);

    btnFromFile = new Button();
    btnFromFile.setText("Open");
    btnFromFile.setOnAction(e -> {
      File file = fileChooser.showOpenDialog(getStage());
      if (file != null) {
        txtFilename.setText(file.getAbsolutePath());
      }
    });
    nextButton.setDisable(true);
    txtFilename.textProperty().addListener((observableValue, oldValue, newValue) -> {
      log.info("newValue = " + newValue);
      txtFilename.setText(newValue.replace("\n\n", "\n"));
      nextButton.setDisable(newValue.isEmpty());
    });
    VBox vbox = new VBox(
      10,
      new Label("Select the file to import and press Next"), txtFilename, btnFromFile
    );

    return vbox;
  }

  void nextPage() {
    if (importFile())
      navTo(PreviewPage.TITLE);
  }

  void finish () {
    if (importFile())
      super.finish();
  }

  boolean importFile() {
    File importfile = new File (txtFilename.getText());


    if (importfile.exists()) {

      try {
        Charset cs = CharsetToolkit.guessEncoding(importfile, 4096, StandardCharsets.UTF_8);
        ChordProFileReader textfileReader = new ChordProFileReader();
        List<String> lines = FileUtils.readLines(importfile, cs);
        controller.setSongToImport(textfileReader.read(lines));
        return true;
      } catch (Exception e) {
        Notifications.create().text("Error on import: " + e.getLocalizedMessage()).showError();
        log.error(e.getMessage(), e);
        return false;
      }
    }

    return false;
  }


}
