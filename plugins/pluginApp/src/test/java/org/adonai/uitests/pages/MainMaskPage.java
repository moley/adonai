package org.adonai.uitests.pages;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import org.adonai.AdonaiProperties;
import org.adonai.model.Configuration;
import org.adonai.services.ModelService;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.ui.Consts;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.UiUtils;
import org.adonai.ui.mainpage.MainPageController;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

public class MainMaskPage extends AbstractPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainMaskPage.class);

  private Mask mask;




  public MainMaskPage (final ApplicationTest applicationTest) throws IOException {
    super (applicationTest);
  }

  public void openStage () {
    Consts.setDefaultHeight(800);
    Consts.setDefaultWidth(1024);
    MaskLoader<MainPageController> maskLoader = new MaskLoader<>();
    mask = maskLoader.load("mainpage");
    mask.getStage().setX(10);
    mask.getStage().setY(10);
    mask.getStage().show();
  }

  public void closeStage () {
    mask.getStage().close();
  }


  public ToggleButton getBtnSongbook () {
    return applicationTest.lookup(nodeWithUserData("mainpage.togSongbooks")).query();
  }

  private Button getBtnConfigurations () {
    return applicationTest.lookup(nodeWithUserData("mainpage.btnConfigurations")).query();
  }


  public ToggleButton getBtnSessions () {
    return applicationTest.lookup(nodeWithUserData("mainpage.togSessions")).query();
  }

  public ToggleButton getBtnSession () {
    return applicationTest.lookup(nodeWithUserData("mainpage.togSession")).query();
  }

  public ListView<Song> getLviSongs () {
    return applicationTest.lookup(nodeWithUserData("mainpage.lviSongs")).query();
  }

  public BorderPane getSongEditorPane () {
    return applicationTest.lookup(nodeWithUserData("songeditor.panRoot")).query();
  }

  public ListView<Session> getLviSessions () {
    return applicationTest.lookup(nodeWithUserData("mainpage.lviSessions")).query();
  }

  public Label getLblCurrentEntity () {
    return applicationTest.lookup(nodeWithUserData("mainpage.lblCurrentEntity")).query();
  }

  public Label getLblCurrentType () {
    return applicationTest.lookup(nodeWithUserData("mainpage.lblCurrentType")).query();
  }

  public ListView<Song> getLviSession () {
    return applicationTest.lookup(nodeWithUserData("mainpage.lviSession")).query();
  }

  public Button getBtnPlus() {
    return applicationTest.lookup(nodeWithUserData("mainpage.btnAdd")).query();
  }

  public Button getBtnExport() {
    return applicationTest.lookup(nodeWithUserData("mainpage.btnExport")).query();
  }

  private Button getBtnUserAdmin() {
    return applicationTest.lookup(nodeWithUserData("mainpage.btnUserAdmin")).query();
  }


  public Button getBtnMinus() {
    return applicationTest.lookup(nodeWithUserData("mainpage.btnRemove")).query();
  }

  public Button getBtnMp3() {
    return applicationTest.lookup(nodeWithUserData("mainpage.btnMp3")).query();
  }

  public void stepToSongbook ()  {
    LOGGER.info("stepToSongbook");
    applicationTest.clickOn(getBtnSongbook());
    Assert.assertTrue (getLviSongs().isVisible());
  }

  public void search (final String searchString) throws InterruptedException {
    applicationTest.type(KeyCode.SPACE);
    applicationTest.clickOn(getTxtSearch());
    applicationTest.write(searchString);
  }

  public SongEditorPage stepToSong (int index) {
    LOGGER.info("stepToSong " + index);
    stepToSongbook();
    getLviSongs().getSelectionModel().select(index);
    applicationTest.doubleClickOn(getLviSongs());
    return new SongEditorPage(applicationTest);
  }

  public TextField getTxtSearch() {
    return applicationTest.lookup(nodeWithUserData("searchpage.txtSearchQuery")).query();
  }

  public SongEditorPage songEditorPage () {
    return new SongEditorPage(applicationTest);
  }


  public SelectSongPage selectSongPage () {
    return new SelectSongPage(applicationTest);
  }

  public String getCurrentContentText () {
    return getLblCurrentEntity().getText();
  }

  public String getCurrentTypeText () {
    return getLblCurrentType().getText();

  }

  public String getSelectedSession () {
    return getLviSessions().getSelectionModel().getSelectedItem().getName();
  }

  public void stepToSessions () {
    applicationTest.clickOn(getBtnSessions(), MouseButton.PRIMARY);
    Assert.assertTrue (getLviSessions().isVisible());
  }

  public void stepToSession (int index) {
    stepToSessions();
    getLviSessions().getSelectionModel().select(index);
    applicationTest.doubleClickOn(getLviSessions());
    Assert.assertTrue (getLviSession().isVisible());
  }

  public void selectMp3 () {
    applicationTest.clickOn(getBtnMp3()).sleep(1000);

  }

  public ConfigurationsPage configurations () throws InterruptedException {
    applicationTest.clickOn(getBtnConfigurations());
    return new ConfigurationsPage (applicationTest);
  }

  public void add () {
    applicationTest.clickOn(getBtnPlus()).sleep(1000);
  }

  public void export () {
    Button btnExport = getBtnExport();
    LOGGER.info("Trigger export on button " + btnExport.getText() + "-" + UiUtils.getBounds(btnExport));
    applicationTest.clickOn(btnExport, MouseButton.PRIMARY).sleep(2000);
    LOGGER.info("Export triggered");
  }

  public UserAdminPage stepToUserAdmin () {
    applicationTest.clickOn(getBtnUserAdmin());
    return new UserAdminPage(applicationTest);
  }

  public void remove () {
    applicationTest.clickOn(getBtnMinus());
  }

  public void applyMp3 () {
    applicationTest.clickOn(getBtnMp3());
  }

  public List<Session> getSessions () {
    return getLviSessions().getItems();
  }

  public List<Song> getSongsInSongbook () {
    return getLviSongs().getItems();
  }

  public List<Song> getSongsInSession () {
    return getLviSession().getItems();
  }

  public List<File> getExportedFiles () {
    Configuration configuration = getCurrentConfiguration();
    return Arrays.asList(configuration.getExportPathAsFile().listFiles());
  }

  public void exportFileExists (String name, final boolean exists) {
    LOGGER.info("Check if export file exists (" + name + "," + exists + ")");
    Configuration configuration = getCurrentConfiguration();
    File expectedFile = new File (configuration.getExportPathAsFile(), name);
    Assert.assertEquals ("Exportfile " + expectedFile.getAbsolutePath() + " existence wrong\n- existing files: " + getExportedFiles(), exists, expectedFile.exists());
  }

  public void save() {
    LOGGER.info("Save");
    applicationTest.clickOn(getBtnSave());
  }

  private Button getBtnSave() {

    return applicationTest.lookup(nodeWithUserData("mainpage.btnSave")).query();
  }
}
