package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 22.11.16.
 */
public class LineEditor extends PanelHolder {

  private static final Logger LOGGER = LoggerFactory.getLogger(LineEditor.class);



  private Line line;
  private PartEditor partEditor;

  private List<LinePartEditor> linePartEditors = new ArrayList<LinePartEditor>();

  private boolean editable;

  HBox content = new HBox();

  private int partIndex;
  private int lineIndex;




  public LineEditor (final PartEditor partEditor, final Line line, final boolean editable, final int partIndex, final int lineIndex) {
    this.index = partIndex + "_" + lineIndex;
    this.partEditor = partEditor;
    this.editable = editable;
    this.line = line;
    this.partIndex = partIndex;
    this.lineIndex = lineIndex;

    setPanel(content);

    reload();

  }

  public HBox getContent () {
    return content;
  }

  public PartEditor getPartEditor () {
    return partEditor;
  }


  public void reload () {
    content.getChildren().clear();
    content.setUserData(getPointer() + "content");
    linePartEditors.clear();
    for (int i = 0; i < line.getLineParts().size(); i++) {
      LinePart linePart = line.getLineParts().get(i);
      LinePartEditor linePartEditor = new LinePartEditor(this, linePart, editable, partIndex, lineIndex, i);
      linePartEditors.add(linePartEditor);
      content.getChildren().add(linePartEditor.getPanel());
    }

  }

  private String getPointer () {
    return "songeditor.line_" + partIndex + "_" + lineIndex + ".";
  }


  public int getIndex (final LinePartEditor linePartEditor) {
    return linePartEditors.indexOf(linePartEditor);
  }

  public LinePartEditor getLinePartEditor (final int index) {
    return linePartEditors.get(index);
  }



  public Line getLine (){
    return line;
  }

  public List<LinePartEditor> getLinePartEditors () {
    return linePartEditors;
  }


  public void select(final LineEditor lastPosition) {
    int oldCaretPosition = lastPosition.getCaretPosition();
    TextField txtFieldNew = linePartEditors.get(0).getTxtText();
    txtFieldNew.requestFocus();
    txtFieldNew.positionCaret(Math.min(oldCaretPosition, txtFieldNew.getText().length()));

  }

  public LinePartEditor getFirstLinePartEditor () {
    return ! linePartEditors.isEmpty() ? linePartEditors.get(0): null;
  }

  public int getCaretPosition () {
    TextField txtFieldNew = linePartEditors.get(0).getTxtText();
    return txtFieldNew.getCaretPosition();
  }



  public void save () {
    for (LinePartEditor linePartEditor: linePartEditors) {
      linePartEditor.save();
    }
  }

  public String toString () {
    return line.toString();
  }

  public boolean hasChanged() {
    for (LinePartEditor linePartEditor: linePartEditors) {
      if (linePartEditor.hasChanged()) {
        return true;
      }
    }

    return false;
  }
}
