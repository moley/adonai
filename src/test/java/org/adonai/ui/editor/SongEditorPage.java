package org.adonai.ui.editor;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.adonai.ui.AbstractPage;
import org.adonai.ui.MyNodeMatchers;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SongEditorPage extends AbstractPage {

  private ApplicationTest applicationTest;

  public SongEditorPage (final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }



  public String getSongLinePartText (final int partIndex, final int lineIndex, final int posInLine) {
    return getSongLinePartTextField(partIndex, lineIndex, posInLine).getText();
  }

  public String getSongLinePartChord (final int partIndex, final int lineIndex, final int posInLine) {
    return getSongLinePartLabel(partIndex, lineIndex, posInLine).getText();
  }

  public String getSongLinePartChordOriginal (final int partIndex, final int lineIndex, final int posInLine) {
    return getSongLinePartLabelOriginal(partIndex, lineIndex, posInLine).getText();
  }

  public boolean isChordEditorVisible () {
    try {
      NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("chordeditor.txtChord"));
      Node node = nodeQuery.query();
      return node.isVisible();
    } catch (Exception e) {
      return false;
    }
  }

  private TextField getTxtChordEditor () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("chordeditor.txtChord"));
    return nodeQuery.query();
  }

  public void chord (final String chord) throws InterruptedException {
    applicationTest.press(KeyCode.CONTROL, KeyCode.C).release(KeyCode.CONTROL, KeyCode.C);
    TextField txtChordEditor = getTxtChordEditor();
    applicationTest.write(chord);
    applicationTest.press(KeyCode.ENTER);
  }




  //private
  private TextField getSongLinePartTextField (final int partIndex, final int lineIndex, final int posInLine) {
    String key = "songeditor.linepart_" + partIndex + "_" + lineIndex + "_" + posInLine + ".txtText";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }

  private Label getSongLinePartLabel (final int partIndex, final int lineIndex, final int posInLine) {
    String key = "songeditor.linepart_" + partIndex + "_" + lineIndex + "_" + posInLine + ".lblChord";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }

  private Label getSongLinePartLabelOriginal (final int partIndex, final int lineIndex, final int posInLine) {
    String key = "songeditor.linepart_" + partIndex + "_" + lineIndex + "_" + posInLine + ".lblChordOriginal";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }
}
