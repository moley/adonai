package org.adonai.ui.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.ui.Consts;

import java.io.IOException;
import java.util.List;

public class MainMaskPage {

  Scene  scene;
  Stage stage;
  ApplicationTest applicationTest;


  public MainMaskPage (final Stage stage, final ApplicationTest applicationTest) throws IOException {
    this.stage = stage;
    this.applicationTest = applicationTest;
    Parent root = FXMLLoader.load(getClass().getResource("/screens/main.fxml"));
    scene = new Scene(root, Consts.DEFAULT_WIDTH, Consts.DEFAULT_HEIGHT);
    scene.getStylesheets().add("/adonai.css");
    stage.setScene(scene);
    stage.show();
  }

  public void stepToSessions () {
    Node node = applicationTest.lookup(".tab-pane > .tab-header-area > .headers-region > .tab").nth(1).query();
    applicationTest.clickOn(node);
    //applicationTest.clickOn("#paneSessions");
  }

  public void stepToAllSongs () {
    Node node = applicationTest.lookup(".tab-pane > .tab-header-area > .headers-region > .tab").nth(0).query();
    applicationTest.clickOn(node);
    //applicationTest.clickOn("#paneAllSongs");
  }

  public void newSession () {
    applicationTest.clickOn(getSessionOverviewListView());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    applicationTest.clickOn("#menuItemNewSession");
  }



  public void addSongInSession () {
    applicationTest.rightClickOn(getSongsOfSessionListView());
    applicationTest.clickOn("#menuItemAddSongToSession");
  }

  public void copySong () {
    applicationTest.rightClickOn(getAllSongsListView());
    applicationTest.clickOn("#menuItemCopySong");
  }

  public void removeSession () {
    applicationTest.rightClickOn(getSessionOverviewListView());
    applicationTest.clickOn("#menuItemRemoveSession");
  }

  public List<Session> getSongOfSession () {
    return getSessionOverviewListView().getItems();
  }

  public List<Song> getAllSongs () {
    return getAllSongsListView().getItems();
  }

  public ListView<Session> getSessionOverviewListView () {
      NodeQuery partStructure = applicationTest.lookup("#lviSessions");
    return partStructure.query();

  }

  public ListView<Song> getAllSongsListView () {
    NodeQuery partStructure = applicationTest.lookup("#lviAllSongs");
    return partStructure.query();
  }

  public ListView<Song> getSongsOfSessionListView () {
    NodeQuery partStructure = applicationTest.lookup("#lviSessionDetails");
    return partStructure.query();
  }

  public TextField getTitleTextField () {
    NodeQuery partStructure = applicationTest.lookup("#txtSessionName");
    return partStructure.query();
  }
}
