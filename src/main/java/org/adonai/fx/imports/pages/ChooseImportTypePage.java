package org.adonai.fx.imports.pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
  private RadioButton cloneFromOtherTenant;
  private RadioButton newSong;
  private ToggleGroup options = new ToggleGroup();

  public final static String TITLE = "Change Import Type";

  public ChooseImportTypePage(final ApplicationEnvironment applicationEnvironment, final Stage stage, final SongImportController controller) {
    super(applicationEnvironment, stage, TITLE, controller);
    fromChordProFile.setToggleGroup(options);
    fromClipboard.setToggleGroup(options);
    newSong.setToggleGroup(options);
    cloneFromOtherTenant.setToggleGroup(options);
    options.selectToggle(fromClipboard);
    finishButton.setDisable(true);

  }

  Parent getContent() {
    fromChordProFile = new RadioButton("Import from chordpro file");

    cloneFromOtherTenant = new RadioButton("Clone from different tenant");

    fromClipboard = new RadioButton("Import from clipboard");
    fromClipboard.setUserData("importsongwizard.rbFromTextFile");

    newSong = new RadioButton("New song");
    newSong.setUserData("importsongwizard.rbNewSong");

    VBox content =  new VBox(
      15,
      new Label("How do you want to import your song"), newSong, fromChordProFile, fromClipboard, cloneFromOtherTenant );
    content.setAlignment(Pos.CENTER_LEFT);
    content.setFillWidth(true);
    VBox.setMargin(content, new Insets(30, 30, 30, 30));

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
    } else if (selectedToggle.equals(cloneFromOtherTenant)) {
      navTo(CloneFromTenantPage.TITLE);
    }
    else throw new IllegalStateException("Invalid type selected adding song");


  }
}
