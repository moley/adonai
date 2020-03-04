package org.adonai.uitests;

import java.io.File;
import java.io.IOException;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.adonai.AbstractAdonaiUiTest;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.model.TenantModel;
import org.adonai.services.ModelService;
import org.adonai.testdata.TestDataCreator;
import org.adonai.uitests.pages.AddPartPage;
import org.adonai.uitests.pages.ImportSongWizardPage;
import org.adonai.uitests.pages.MainMaskPage;
import org.adonai.uitests.pages.SelectAdditionalPage;
import org.adonai.uitests.pages.SongDetailsPage;
import org.adonai.uitests.pages.SongEditorPage;
import org.adonai.uitests.pages.SongPartDetailsPage;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMaskSongTest extends AbstractAdonaiUiTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(MainMaskSongTest.class);


  private MainMaskPage mainMaskPage;

  private Configuration configuration;

  private TestDataCreator testDataCreator = new TestDataCreator();


  @BeforeClass
  public static void beforeClass () {
    TestUtil.initialize();
  }

  @Override
  public void start(Stage stage) throws Exception {
    LOGGER.info("start called with stage " + System.identityHashCode(stage));
    configuration = testDataCreator.createTestData(false);
    super.start(stage);
    mainMaskPage = new MainMaskPage( this);
    mainMaskPage.openStage();
  }

  @Override
  public void stop () {
    LOGGER.info("stop called");
  }

  @Test
  public void importSongInSongViewWithPreview () throws IOException {
    String clipboard = FileUtils.readFileToString(new File("src/test/resources/import/text/Das Glaube ich.txt"), "UTF-8");
    int numberOfSongsBefore = mainMaskPage.getSongsInSongbook().size();
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    mainMaskPage.add();
    ImportSongWizardPage importSongWizardPage = new ImportSongWizardPage(this);
    importSongWizardPage.importSongViaPreview(clipboard);
    Assert.assertEquals ("5 - DAS GLAUBE ICH", mainMaskPage.getCurrentContentText());
    mainMaskPage.stepToSongbook();
    int numberOfSongsAfter = mainMaskPage.getSongsInSongbook().size();
    Assert.assertEquals ("Number of songs did not increase", numberOfSongsBefore + 1, numberOfSongsAfter);

  }

  @Test
  public void importSongInSongView () throws IOException {
    String clipboard = FileUtils.readFileToString(new File("src/test/resources/import/text/Das Glaube ich.txt"), "UTF-8");
    int numberOfSongsBefore = mainMaskPage.getSongsInSongbook().size();
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    mainMaskPage.add();
    ImportSongWizardPage importSongWizardPage = new ImportSongWizardPage(this);
    importSongWizardPage.importSong(clipboard);
    Assert.assertEquals ("5 - DAS GLAUBE ICH", mainMaskPage.getCurrentContentText());
    mainMaskPage.stepToSongbook();
    int numberOfSongsAfter = mainMaskPage.getSongsInSongbook().size();
    Assert.assertEquals ("Number of songs did not increase", numberOfSongsBefore + 1, numberOfSongsAfter);

  }

  @Test
  public void addNewSongInSongView () {
    int numberOfSongsBefore = mainMaskPage.getSongsInSongbook().size();
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    mainMaskPage.add();
    final String TITLE_TEST = "Test";
    ImportSongWizardPage importSongWizardPage = new ImportSongWizardPage(this);
    importSongWizardPage.newSong(TITLE_TEST);
    Assert.assertEquals ("5 - TEST", mainMaskPage.getCurrentContentText());
    mainMaskPage.stepToSongbook();
    int numberOfSongsAfter = mainMaskPage.getSongsInSongbook().size();
    Assert.assertEquals ("Number of songs did not increase", numberOfSongsBefore + 1, numberOfSongsAfter);
  }

  @Test
  public void editSongPartDetails () {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    SongPartDetailsPage songPartDetailsPage = songEditorPage.clickPartHeader(0);
    songPartDetailsPage.setQuantity("10");
    mainMaskPage.getApplicationTest().type(KeyCode.ESCAPE);
    mainMaskPage.stepToSongbook();
    Song selectedSong = mainMaskPage.getLviSongs().getSelectionModel().getSelectedItem();
    SongStructItem selectedStructPart = selectedSong.getFirstStructItem();
    Assert.assertEquals ("10", selectedStructPart.getQuantity());
  }

  @Test
  public void editSongDetails () {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    SongDetailsPage songDetailsPage = songEditorPage.songDetailsPage();
    Assert.assertEquals ("SONG1_TENANT1", songDetailsPage.getTitle());

    songDetailsPage.setTitle("Song1Changed");

    mainMaskPage.getApplicationTest().type(KeyCode.ESCAPE);

    mainMaskPage.stepToSongbook();
    Song selectedSong = mainMaskPage.getLviSongs().getSelectionModel().getSelectedItem();
    Assert.assertEquals ("Song1Changed", selectedSong.getTitle());
  }

  @Test
  public void addPartAfter () {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    songEditorPage.mouseOverSongPartHeader(0);
    AddPartPage addPartPage = songEditorPage.addAfter(0);
    addPartPage.search("New Intro");
    mainMaskPage.stepToSongbook();
    Song selectedSong = mainMaskPage.getLviSongs().getSelectionModel().getSelectedItem();
    SongStructItem songStructItem = selectedSong.getStructItems().get(1);
    SongPart songPart = selectedSong.findSongPart(songStructItem);
    Assert.assertEquals ("Wrong part in song " + selectedSong.getTitle(), SongPartType.INTRO, songPart.getSongPartType());
  }

  @Test
  public void cancelAddPartAfter () {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    songEditorPage.mouseOverSongPartHeader(0);
    AddPartPage addPartPage = songEditorPage.addAfter(0);
    addPartPage.cancelSearch("New Intro");
    mainMaskPage.stepToSongbook();
    Song selectedSong = mainMaskPage.getLviSongs().getSelectionModel().getSelectedItem();
    Assert.assertEquals ("Wrong part in song " + selectedSong.getTitle(), SongPartType.REFRAIN, selectedSong.getSongParts().get(1).getSongPartType());
  }

  @Test
  public void addPartBefore () {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);

    songEditorPage.mouseOverSongPartHeader(0);
    AddPartPage addPartPage = songEditorPage.addBefore(0);
    addPartPage.search("New Intro");
    mainMaskPage.stepToSongbook();
    Song selectedSong = mainMaskPage.getLviSongs().getSelectionModel().getSelectedItem();
    SongStructItem songStructItem = selectedSong.getFirstStructItem();
    SongPart songPart = selectedSong.findSongPart(songStructItem);

    Assert.assertEquals ("Wrong part in song " + selectedSong.getTitle(), SongPartType.INTRO, songPart.getSongPartType());
  }

  @Test
  public void cancelAddPartBefore () {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    songEditorPage.mouseOverSongPartHeader(0);
    AddPartPage addPartPage = songEditorPage.addBefore(0);
    addPartPage.cancelSearch("New Intro");
    mainMaskPage.stepToSongbook();
    Song selectedSong = mainMaskPage.getLviSongs().getSelectionModel().getSelectedItem();
    Assert.assertEquals ("Wrong part in song " + selectedSong.getTitle(), SongPartType.VERS, selectedSong.getSongParts().get(0).getSongPartType());
  }

  @Test
  public void enterValidChord () throws InterruptedException {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    String text = songEditorPage.getSongLinePartText(0, 0,0);
    Assert.assertEquals ("First textfield invalid content", "This is a", text.trim());
    songEditorPage.chord("G");
    Assert.assertFalse ("ChordEditor is visible after adding an valid chord", songEditorPage.isChordEditorVisible());

  }

  @Test
  public void enterInvalidChord () throws InterruptedException {
    SongEditorPage songEditorPage = mainMaskPage.stepToSong(0);
    String text = songEditorPage.getSongLinePartText(0, 0,0);
    Assert.assertEquals ("First textfield invalid content", "This is a", text.trim());
    songEditorPage.chord("Ghh");
    Assert.assertTrue ("ChordEditor is not visible after adding an invalid chord", songEditorPage.isChordEditorVisible());

    key(KeyCode.ESCAPE);
    Assert.assertFalse ("ChordEditor is visible after escaping an invalid chord", songEditorPage.isChordEditorVisible());
  }

  @Test
  public void assignMp3 () throws InterruptedException, IOException {
    Song firstSong = configuration.getSongBooks().get(0).getSongs().get(0);
    Assert.assertEquals ("Number of additionals invalid before", 0, firstSong.getAdditionals().size());

    mainMaskPage.stepToSong(0);
    mainMaskPage.selectMp3();
    SelectAdditionalPage selectAdditionalPage = new SelectAdditionalPage(this);
    selectAdditionalPage.select("AnotherMp3");
    mainMaskPage.save ();

    ModelService modelService = new ModelService();
    Model model = modelService.load();
    TenantModel tenantModel = model.getCurrentTenantModel();
    Configuration configuration = tenantModel.get();

    for (Song next: configuration.getSongBooks().get(0).getSongs()) {
      LOGGER.info("Next: " + configuration.getTenant() + "-" + next.getId() + " - " + next.getTitle() + next.getAdditionals());
    }
    firstSong = configuration.getSongBooks().get(0).getSongs().get(0);
    Assert.assertTrue ("Wrong additional added (" + firstSong.getAdditionals().get(0).getLink() + ")", firstSong.getAdditionals().get(0).getLink().endsWith(".adonai/additionals/AnotherMp3.mp3"));
  }


}
