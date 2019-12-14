package org.adonai.ui.mainpage;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import org.adonai.actions.ExportAction;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.ui.AbstractPage;
import org.adonai.ui.Consts;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.UiUtils;
import org.adonai.ui.editor.SongEditorPage;
import org.adonai.ui.selectsong.SelectSongPage;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testfx.util.WaitForAsyncUtils;

public class MainMaskPage extends AbstractPage {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainMaskPage.class);

  ApplicationTest applicationTest;

  private Mask mask;


  public MainMaskPage (final ApplicationTest applicationTest) throws IOException {
    this.applicationTest = applicationTest;
  }

  public void openStage () {
    Consts.setDefaultHeight(640);
    Consts.setDefaultWidth(800);
    MaskLoader<MainPageController> maskLoader = new MaskLoader<>();
    mask = maskLoader.load("mainpage");
    mask.getStage().setX(10);
    mask.getStage().setY(10);
    mask.getStage().setAlwaysOnTop(false);
    mask.getStage().show();
  }

  public void closeStage () {
    mask.getStage().close();
  }


  public ToggleButton getBtnSongbook () {
    return applicationTest.lookup(nodeWithUserData("mainpage.togSongbooks")).query();
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

  public void stepToSong (int index) {
    LOGGER.info("stepToSong " + index);
    stepToSongbook();
    getLviSongs().getSelectionModel().select(index);
    applicationTest.doubleClickOn(getLviSongs());
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

  public void selectMp3 (final String name) {
    applicationTest.clickOn(getBtnMp3()).sleep(1000);
    SelectAdditionalPage selectAdditionalPage = new SelectAdditionalPage(applicationTest);
    selectAdditionalPage.select(name);
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
    Configuration configuration = new ConfigurationService().get();
    return Arrays.asList(configuration.getExportPathAsFile().listFiles());
  }

  public void exportFileExists (String name, final boolean exists) {
    LOGGER.info("Check if export file exists (" + name + "," + exists + ")");
    Configuration configuration = new ConfigurationService().get();
    File expectedFile = new File (configuration.getExportPathAsFile(), name);
    Assert.assertEquals ("Exportfile " + expectedFile.getAbsolutePath() + " existence wrong\n- existing files: " + getExportedFiles(), exists, expectedFile.exists());
  }

  public void save() throws InterruptedException {
    applicationTest.clickOn(getBtnSave()).wait(1000);
  }

  private Button getBtnSave() {
    return applicationTest.lookup(nodeWithUserData("mainpage.btnSave")).query();
  }
}
