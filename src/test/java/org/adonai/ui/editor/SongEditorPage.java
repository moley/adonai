package org.adonai.ui.editor;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.adonai.ui.AbstractPage;
import org.adonai.ui.MyNodeMatchers;
import org.adonai.ui.NodeUtils;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SongEditorPage extends AbstractPage {

  private ApplicationTest applicationTest;

  public SongEditorPage (final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }



  public VBox getSongEditorContentPane () {
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withId("songEditorContent"));
    return partEditorContentPane.query();
  }

  public VBox findSongLinePartTextField (final int partIndex, final int lineIndex, final int posInLine) {

    String key = "linePartEditor_" + partIndex + "_" + lineIndex + "_" + posInLine;
    VBox editorContent = getSongEditorContentPane();

    NodeUtils children = new NodeUtils();

    List<Node> list = children.paneNodes(editorContent);
    for (Node next: list) {
      System.out.println (next.getId() + "-" + next.getClass().getSimpleName());
    }

    System.out.println ("Hello");

    for (Node nextPartEditor: editorContent.getChildren()) { //parteditor
      HBox nextHBox = (HBox) nextPartEditor; //e.g. partEditor_0
      for (Node nextHBoxEntry: nextHBox.getChildren()) {
        if (nextHBoxEntry instanceof TitledPane) {
          TitledPane titledPane = (TitledPane) nextHBoxEntry;
          VBox content = (VBox) titledPane.getContent();
          for (Node nextLineEditor: content.getChildren()) {

            HBox lineEditorContent = (HBox) nextLineEditor;
            for (Node nextLinePartEditor: lineEditorContent.getChildren()) {
              if (nextLinePartEditor.getId().equals(key))
                return (VBox) nextLinePartEditor;
            }
          }
        }
      }
    }

    throw new IllegalStateException("No content found on position " + lineIndex + "-" + posInLine);
  }
}
