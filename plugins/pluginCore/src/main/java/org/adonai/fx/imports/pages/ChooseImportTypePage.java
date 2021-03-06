package org.adonai.fx.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.imports.SongImportController;

public class ChooseImportTypePage extends WizardPage {
  private RadioButton fromTextFile;
  private RadioButton newSong;
  private ToggleGroup options = new ToggleGroup();

  public final static String TITLE = "Change Import Type";

  public ChooseImportTypePage(final ApplicationEnvironment applicationEnvironment, final SongImportController controller) {
    super(applicationEnvironment, TITLE, controller);
    fromTextFile.setToggleGroup(options);
    newSong.setToggleGroup(options);
    options.selectToggle(fromTextFile);
    finishButton.setDisable(true);

  }

  Parent getContent() {
    fromTextFile = new RadioButton("Import from clipboard");
    fromTextFile.setUserData("importsongwizard.rbFromTextFile");
    newSong = new RadioButton("New song");
    newSong.setUserData("importsongwizard.rbNewSong");
    VBox content =  new VBox(
      10,
      new Label("How do you want to import your song"), fromTextFile, newSong);

    return content;
  }

  void nextPage() {
    // If they have complaints, go to the normal next page
    finishButton.setDisable(false);
    Toggle selectedToggle = options.getSelectedToggle();
    if (selectedToggle.equals(fromTextFile)) {
      navTo(ImportFromClipBoardPage.TITLE);
    } else if (selectedToggle.equals(newSong)){
      navTo(NewSongPage.TITLE);
    } else throw new IllegalStateException("Invalid type selected adding song");


  }
}