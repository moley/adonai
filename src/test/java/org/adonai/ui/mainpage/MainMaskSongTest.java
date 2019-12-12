package org.adonai.ui.mainpage;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.testdata.TestDataCreator;
import org.adonai.ui.TestUtil;
import org.adonai.ui.editor.SongEditorPage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MainMaskSongTest extends AbstractAdonaiUiTest {

  private MainMaskPage mainMaskPage;

  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData(TestUtil.getDefaultTestDataPath(), false);
    super.start(stage);
    mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();
  }

  @Test
  public void invalidChordNonExiting () throws InterruptedException {
    mainMaskPage.stepToSongbook();
    mainMaskPage.stepToSong(0);

    SongEditorPage songEditorPage = mainMaskPage.songEditorPage();
    TextField textField = songEditorPage.getSongLinePartTextField(0, 0,0);
    Assert.assertEquals ("First textfield invalid content", "This is a", textField.getText().trim());
    press(KeyCode.CONTROL, KeyCode.C);


    Thread.sleep(1000);



    //TODO Asserts
  }


}
