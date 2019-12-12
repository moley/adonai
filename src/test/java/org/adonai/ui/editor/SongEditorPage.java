package org.adonai.ui.editor;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.adonai.ui.AbstractPage;
import org.adonai.ui.MyNodeMatchers;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SongEditorPage extends AbstractPage {

  private ApplicationTest applicationTest;

  public SongEditorPage (final ApplicationTest applicationTest) {
    this.applicationTest = applicationTest;
  }

  public TextField getSongLinePartTextField (final int partIndex, final int lineIndex, final int posInLine) {
    String key = "songeditor.linepart_" + partIndex + "_" + lineIndex + "_" + posInLine + ".txtText";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }

  public Label getSongLinePartLabel (final int partIndex, final int lineIndex, final int posInLine) {
    String key = "songeditor.linepart_" + partIndex + "_" + lineIndex + "_" + posInLine + ".lblChord";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }

  public Label getSongLinePartLabelOriginal (final int partIndex, final int lineIndex, final int posInLine) {
    String key = "songeditor.linepart_" + partIndex + "_" + lineIndex + "_" + posInLine + ".lblChordOriginal";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }
}
