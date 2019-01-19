package org.adonai.ui.imports.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.adonai.ui.imports.SongImportController;

public class ChooseImportTypePage extends WizardPage {
  private RadioButton fromTextFile;
  private RadioButton newSong;
  private ToggleGroup options = new ToggleGroup();

  public final static String TITLE = "Change Import Type";

  public ChooseImportTypePage(final SongImportController controller) {
    super(TITLE, controller);

    fromTextFile.setToggleGroup(options);
    newSong.setToggleGroup(options);
    options.selectToggle(fromTextFile);

  }

  Parent getContent() {
    fromTextFile = new RadioButton("Import from clipboard");
    newSong = new RadioButton("New song");
    return new VBox(
      10,
      new Label("How do you want to import your song"), fromTextFile, newSong);
  }

  void nextPage() {
    // If they have complaints, go to the normal next page
    Toggle selectedToggle = options.getSelectedToggle();
    if (selectedToggle.equals(fromTextFile)) {
      navTo(ImportFromClipBoardPage.TITLE);
    } else if (selectedToggle.equals(newSong)){
      navTo(NewSongPage.TITLE);
    } else throw new IllegalStateException("Invalid type selected adding song");


  }
}