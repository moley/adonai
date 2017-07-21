package org.adonai.ui.editor;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;
import org.adonai.model.SongPart;

import java.util.List;

public class SongEditorPage {

  private ApplicationTest applicationTest;

  public SongEditorPage (final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }

  private ListView<SongPart> getPartStructureListView () {
    NodeQuery partStructure = applicationTest.lookup("#partstructure");
    ListView<SongPart> partStructureListView = partStructure.query();
    return partStructureListView;
  }

  public List<SongPart> getSongParts () {
    return getPartStructureListView().getItems();
  }

  public void addPartAfter () {
    applicationTest.rightClickOn(getPartStructureListView());
    applicationTest.clickOn("#menuitemAddPartAfter");
  }

  public void addPartBefore () {
    applicationTest.rightClickOn(getPartStructureListView());
    applicationTest.clickOn("#menuitemAddPartBefore");
  }

  public void removePart () {
    applicationTest.rightClickOn(getPartStructureListView());
    applicationTest.clickOn("#menuItemRemovePart");
  }

  public String getSongTitle () {
    TextField txtSongtitle = applicationTest.lookup("#songtitle").query();
    return txtSongtitle.getText();
  }

  public boolean isInSongEditor () {
    return getPartStructureListView() != null;
  }



  public void closeView () {

  }


}
