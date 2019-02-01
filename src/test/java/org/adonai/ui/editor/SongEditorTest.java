package org.adonai.ui.editor;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.SongTestData;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Song;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SongEditorTest extends AbstractAdonaiUiTest {

  private SongEditorPage songEditorPage;


  @Override
  public void start(Stage stage) throws Exception {
    Song song = SongTestData.getSongWithTwoParts();
    ConfigurationService configurationService = new ConfigurationService();
    Scene scene = new Scene(new SongEditor(configurationService.get(), song).getPanel(), 800, 600);
    scene.getStylesheets().add("/adonai.css");
    stage.setScene(scene);
    stage.show();
    songEditorPage = new SongEditorPage( this);

  }


  @Test
  public void stepDownAndUp () throws InterruptedException {
    TextField txtField = songEditorPage.getTextTextField(0, 0);
    TextField txtFieldSecondLine = songEditorPage.getTextTextField(1, 0);

    Assert.assertTrue ("First line not focused at beginning", txtField.isFocused());
    Assert.assertFalse ("Second line focused at beginning", txtFieldSecondLine.isFocused());

    key(KeyCode.DOWN);

    Assert.assertFalse ("First line focused after pressed cursor down", txtField.isFocused());
    Assert.assertTrue ("Second line not focused after pressed cursor down", txtFieldSecondLine.isFocused());

    key(KeyCode.UP);

    Assert.assertTrue ("First line not focused at beginning", txtField.isFocused());
    Assert.assertFalse ("Second line focused at beginning", txtFieldSecondLine.isFocused());






  }


  @Test
  public void newPartAfter () throws InterruptedException {
    int numberOfSongParts = songEditorPage.getSongParts().size();

    songEditorPage.addPartAfter();

    int numberOfSongPartsAfterAdding = songEditorPage.getSongParts().size();

    Assert.assertEquals ("VERS", songEditorPage.getSongParts().get(0).getSongPartTypeLabel());
    Assert.assertEquals ("UNDEFINED", songEditorPage.getSongParts().get(1).getSongPartTypeLabel());
    Assert.assertEquals (numberOfSongParts + 1, numberOfSongPartsAfterAdding);

  }

  @Test
  public void newPartBefore () throws InterruptedException {
    int numberOfSongParts = songEditorPage.getSongParts().size();

    songEditorPage.addPartBefore();

    int numberOfSongPartsAfterAdding = songEditorPage.getSongParts().size();

    Assert.assertEquals ("UNDEFINED", songEditorPage.getSongParts().get(0).getSongPartTypeLabel());
    Assert.assertEquals ("VERS", songEditorPage.getSongParts().get(1).getSongPartTypeLabel());
    Assert.assertEquals (numberOfSongParts + 1, numberOfSongPartsAfterAdding);

  }

  @Test
  public void removePart () throws InterruptedException {
    int numberOfSongParts = songEditorPage.getSongParts().size();

    songEditorPage.removePart();

    int numberOfSongPartsAfterAdding = songEditorPage.getSongParts().size();

    Assert.assertEquals (numberOfSongParts -1, numberOfSongPartsAfterAdding);

    songEditorPage.removePart(); //Remove last part invalid
    Assert.assertEquals (numberOfSongParts -1, numberOfSongPartsAfterAdding);


  }
}
