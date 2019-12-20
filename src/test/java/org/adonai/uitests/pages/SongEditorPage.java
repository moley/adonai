package org.adonai.uitests.pages;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.adonai.uitests.MyNodeMatchers;
import org.apache.commons.math3.analysis.function.Add;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SongEditorPage extends AbstractPage {

  public SongEditorPage (final ApplicationTest applicationTest) {
    super (applicationTest);
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

  private Button getBtnSongDetails () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("songeditor.btnSongInfo"));
    return nodeQuery.query();
  }

  public SongDetailsPage songDetailsPage () {
    applicationTest.clickOn(getBtnSongDetails());
    SongDetailsPage songDetailsPage = new SongDetailsPage(applicationTest);
    return songDetailsPage;
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

  private Label getSongPartHeader (final int partIndex) {
    String key = "songeditor.part_" + partIndex + ".lblHeader";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }

  private Button getBtnAddBefore (final int partIndex) {
    String key = "songeditor.part_" + partIndex + ".btnAddBefore";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }

  private Button getBtnAddAfter (final int partIndex) {
    String key = "songeditor.part_" + partIndex + ".btnAddAfter";
    NodeQuery partEditorContentPane = applicationTest.lookup(MyNodeMatchers.withUserData(key));
    return partEditorContentPane.query();
  }

  public void mouseOverSongPartHeader (final int partIndex) {
    applicationTest.moveTo(getSongPartHeader(partIndex));
  }

  public SongPartDetailsPage clickPartHeader (final int partIndex) {
    applicationTest.clickOn(getSongPartHeader(partIndex));
    return new SongPartDetailsPage(applicationTest);
  }

  public AddPartPage addBefore (final int partIndex) {
    applicationTest.clickOn(getBtnAddBefore(partIndex));
    return new AddPartPage(applicationTest);
  }

  public AddPartPage addAfter (final int partIndex) {
    applicationTest.clickOn(getBtnAddAfter(partIndex));
    return new AddPartPage(applicationTest);
  }
}
