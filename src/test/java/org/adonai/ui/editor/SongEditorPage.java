package org.adonai.ui.editor;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

  public VBox getSongEditorContentPane () {
    NodeQuery partEditorContentPane = applicationTest.lookup("#parteditorcontentPane");
    return partEditorContentPane.query();
  }

  public VBox getContentElement (final int lineIndex, final int posInLine) {

    String key = "editor_" + lineIndex + "_" + posInLine;
    VBox editorContent = getSongEditorContentPane();
    for (Node nextNode: editorContent.getChildren()) {
      System.out.println ("Id: " + nextNode.getId());
      HBox nextHBox = (HBox) nextNode;
      for (Node nextHBoxEntry: nextHBox.getChildren()) {
        VBox nextHBoxEntryHbox = (VBox) nextHBoxEntry;
        System.out.println (nextHBoxEntryHbox.getId());
          if (nextHBoxEntryHbox.getId().equals(key))
            return nextHBoxEntryHbox;
      }
    }

    throw new IllegalStateException("No content found on position " + lineIndex + "-" + posInLine);
  }

  public Label getChordLabel (final int lineIndex, final int posInLine) {
    VBox vbox = getContentElement(lineIndex, posInLine);
    return (Label) vbox.getChildren().get(0);
  }

  public TextField getTextTextField (final int lineIndex, final int posInLine) {
    VBox vbox = getContentElement(lineIndex, posInLine);
    return (TextField) vbox.getChildren().get(1);
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
