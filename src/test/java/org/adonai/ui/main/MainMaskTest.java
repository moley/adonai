package org.adonai.ui.main;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.model.Song;
import org.adonai.ui.editor.SongEditorPage;
import org.adonai.ui.selectsong.SelectSongPage;


public class MainMaskTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);
    mainMaskPage = new MainMaskPage(stage, this);
  }

  @Test
  public void newSession () throws InterruptedException {
    mainMaskPage.stepToSessions();

    int numberOfSessionsBefore = mainMaskPage.getSongOfSession().size();

    mainMaskPage.newSession();

    int numberOfSessionsAfter = mainMaskPage.getSongOfSession().size();
    SelectSongPage selectSongPage = new SelectSongPage(this);
    Assert.assertFalse (selectSongPage.isInSongSelection());

    Assert.assertEquals (numberOfSessionsBefore + 1, numberOfSessionsAfter);

    ListView<Song> sessionDetails = mainMaskPage.getSongsOfSessionListView();
    Assert.assertTrue (sessionDetails.getItems() != null);

    Assert.assertTrue (mainMaskPage.getTitleTextField().getText() + " does not match default name", mainMaskPage.getTitleTextField().getText().startsWith("New session"));
    mainMaskPage.addSongInSession();

    Assert.assertTrue (selectSongPage.isInSongSelection());
    closeCurrentWindow();
  }

  @Test
  public void deleteSession () throws InterruptedException {
    mainMaskPage.stepToSessions();

    int numberOfSessionsBefore = mainMaskPage.getSongOfSession().size();

    mainMaskPage.removeSession();

    int numberOfSessionsAfter = mainMaskPage.getSongOfSession().size();

    Assert.assertEquals (numberOfSessionsBefore - 1, numberOfSessionsAfter);
  }

  @Test
  public void copySong () throws InterruptedException {
    SongEditorPage songEditorPage = new SongEditorPage(this);
    Assert.assertFalse (songEditorPage.isInSongEditor());
    int numberOfSessionsBefore = mainMaskPage.getAllSongs().size();
    mainMaskPage.stepToAllSongs();

    mainMaskPage.copySong();

    int numberOfSessionsAfter = mainMaskPage.getAllSongs().size();
    Assert.assertEquals (numberOfSessionsBefore  +1 , numberOfSessionsAfter);

    Assert.assertTrue (songEditorPage.isInSongEditor());
    Assert.assertEquals ("Copy of Song 1" , songEditorPage.getSongTitle());

    Assert.assertEquals (2, songEditorPage.getSongParts().size());





  }
}
