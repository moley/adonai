package org.adonai.ui.mainpage;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.ui.AbstractPage;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.List;

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

  public ListView<Session> getLviSessions () {
    return applicationTest.lookup(nodeWithId("lviSessions")).query();
  }

  public ListView<Song> getLviSession () {
    return applicationTest.lookup(nodeWithId("lviSession")).query();
  }

  public Button getBtnPlus() {
    return applicationTest.lookup(nodeWithId("btnPlus")).query();
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

  public void remove () {
    applicationTest.clickOn(getBtnMinus());
  }

  public List<Session> getSessions () {
    return getLviSessions().getItems();
  }

  public List<Song> getSongsInSongbook () {
    return getLviSongs().getItems();
  }




}
