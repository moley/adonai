package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.adonai.model.Line;
import org.adonai.model.LinePart;

/**
 * Created by OleyMa on 22.11.16.
 */
public class LineEditor extends PanelHolder {

  private static final Logger LOGGER = Logger.getLogger(LineEditor.class.getName());



  private Line line;
  private PartEditor partEditor;

  private List<LinePartEditor> linePartEditors = new ArrayList<LinePartEditor>();

  private boolean editable;

  HBox root = new HBox();




  public LineEditor (final PartEditor partEditor, final Line line, final boolean editable, final String index) {
    this.index = index;
    this.partEditor = partEditor;
    this.editable = editable;
    this.line = line;

    setPanel(root);

    reload();

  }

  public PartEditor getPartEditor () {
    return partEditor;
  }


  public void reload () {
    root.getChildren().clear();
    linePartEditors.clear();
    int partIndex = 0;
    for (LinePart linePart: line.getLineParts()) {
      LinePartEditor linePartEditor = new LinePartEditor(this, linePart, editable, getIndex() + "_" + partIndex++);
      linePartEditors.add(linePartEditor);
      root.getChildren().add(linePartEditor.getPanel());
    }

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