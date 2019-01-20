package org.adonai.ui.main;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.ui.AbstractPage;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.mainpage.MainPageController;
import org.junit.Assert;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.List;

public class MainMaskPage extends AbstractPage {

  Mask<MainPageController> mask;
  ApplicationTest applicationTest;


  public MainMaskPage (final ApplicationTest applicationTest) throws IOException {
    this.applicationTest = applicationTest;
    MaskLoader<MainPageController> maskLoader = new MaskLoader<>();
    mask = maskLoader.load("mainpage");
    mask.getStage().setAlwaysOnTop(false);
    mask.getStage().show();
  }


  public Node getBtnSongbook () {
    return applicationTest.lookup(nodeWithId("togSongbooks")).query();
  }

  public Node getBtnSessions () {
    return applicationTest.lookup(nodeWithId("togSessions")).query();
  }

  public Node getBtnSession () {
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

  public Node getBtnPlus() {
    return applicationTest.lookup(nodeWithId("btnPlus")).query();
  }

  public Node getBtnMinus() {
    return applicationTest.lookup(nodeWithId("btnMinus")).query();
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
    return ((ListView)getLviSessions()).getItems();
  }




}
