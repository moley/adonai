package org.adonai.fx.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.imports.SongImportController;

public class ChooseImportTypePage extends WizardPage {
  private RadioButton fromChordProFile;
  private RadioButton fromClipboard;
  private RadioButton newSong;
  private ToggleGroup options = new ToggleGroup();

  public final static String TITLE = "Change Import Type";

  public ChooseImportTypePage(final ApplicationEnvironment applicationEnvironment, final Stage stage, final SongImportController controller) {
    super(applicationEnvironment, stage, TITLE, controller);
    fromChordProFile.setToggleGroup(options);
    fromClipboard.setToggleGroup(options);
    newSong.setToggleGroup(options);
    options.selectToggle(fromClipboard);
    finishButton.setDisable(true);

  }

  Parent getContent() {
    fromChordProFile = new RadioButton("From chordpro file");
    fromClipboard = new RadioButton("Import from clipboard");
    fromClipboard.setUserData("importsongwizard.rbFromTextFile");
    newSong = new RadioButton("New song");
    newSong.setUserData("importsongwizard.rbNewSong");
    VBox content =  new VBox(
      10,
      new Label("How do you want to import your song"), fromChordProFile, fromClipboard, newSong);

    return content;
  }

  void nextPage() {
    // If they have complaints, go to the normal next page
    finishButton.setDisable(false);
    Toggle selectedToggle = options.getSelectedToggle();
    if (selectedToggle.equals(fromClipboard)) {
      navTo(ImportFromClipBoardPage.TITLE);
    } else if (selectedToggle.equals(newSong)){
      navTo(NewSongPage.TITLE);
    } else if (selectedToggle.equals(fromChordProFile)) {
      navTo(ImportFromFilePage.TITLE);
    }
    else throw new IllegalStateException("Invalid type selected adding song");


  }
}
