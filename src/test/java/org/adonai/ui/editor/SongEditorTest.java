package org.adonai.ui.editor;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.adonai.SongTestData;
import org.adonai.model.Song;

public class SongEditorTest extends ApplicationTest {

  private SongEditorPage songEditorPage;

  @Override
  public void start(Stage stage) throws Exception {
    Song song = SongTestData.getSongWithTwoParts();
    Scene scene = new Scene(new SongEditor(song, false).getPanel(), 800, 600);
    stage.setScene(scene);
    stage.show();
    songEditorPage = new SongEditorPage( this);
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
