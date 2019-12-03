package org.adonai.ui.mainpage;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.ui.AbstractPage;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testfx.util.WaitForAsyncUtils;

public class MainMaskPage extends AbstractPage {




  ApplicationTest applicationTest;


  public MainMaskPage (final ApplicationTest applicationTest) throws IOException {
    this.applicationTest = applicationTest;
  }

  public void openStage () {
    MaskLoader<MainPageController> maskLoader = new MaskLoader<>();
    Mask mask = maskLoader.load("mainpage");
    mask.getStage().setAlwaysOnTop(false);
    mask.getStage().show();
  }


  public ToggleButton getBtnSongbook () {
    return applicationTest.lookup(nodeWithId("togSongbooks")).query();
  }


  public ToggleButton getBtnSessions () {
    return applicationTest.lookup(nodeWithId("togSessions")).query();
  }

  public ToggleButton getBtnSession () {
    return applicationTest.lookup(nodeWithId("togSession")).query();
  }

  public ListView<Song> getLviSongs () {
    return applicationTest.lookup(nodeWithId("lviSongs")).query();
  }

  public BorderPane getSongEditorPane () {
    return applicationTest.lookup(nodeWithId("songeditor")).query();
  }

  public ListView<Session> getLviSessions () {
    return applicationTest.lookup(nodeWithId("lviSessions")).query();
  }

  public Label getLblCurrentEntity () {
    return applicationTest.lookup(nodeWithId("lblCurrentEntity")).query();
  }

  public ListView<Song> getLviSession () {
    return applicationTest.lookup(nodeWithId("lviSession")).query();
  }

  public Button getBtnPlus() {
    return applicationTest.lookup(nodeWithId("btnPlus")).query();
  }

  public Button getBtnExport() {
    return applicationTest.lookup(nodeWithId("btnExport")).query();
  }


  public Button getBtnMinus() {
    return applicationTest.lookup(nodeWithId("btnMinus")).query();
  }

  public Button getBtnMp3() {
    return applicationTest.lookup(nodeWithId("btnMp3")).query();
  }

  public void stepToSongbook ()  {
    applicationTest.clickOn(getBtnSongbook());
    Assert.assertTrue (getLviSongs().isVisible());
  }

  public String getCurrentContentText () {
    return getLblCurrentEntity().getText();
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

  public void add () {
    applicationTest.clickOn(getBtnPlus());
  }

  public void export () {
    applicationTest.clickOn(getBtnExport());
    //WaitForAsyncUtils.waitForFxEvents();
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

  public List<File> getExportedFiles () {
    Configuration configuration = new ConfigurationService().get();
    return Arrays.asList(configuration.getExportPathAsFile().listFiles());
  }

  public boolean exportFileExists (String name) {
    Configuration configuration = new ConfigurationService().get();
    File expectedFile = new File (configuration.getExportPathAsFile(), name);
    return expectedFile.exists();
  }




}
