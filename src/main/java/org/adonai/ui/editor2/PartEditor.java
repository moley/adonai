package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.adonai.model.Line;
import org.adonai.model.SongPart;
import org.adonai.ui.Consts;

/**
 * Created by OleyMa on 22.11.16.
 */
public class PartEditor extends PanelHolder {

  private List<LineEditor> lineEditors = new ArrayList<LineEditor>();


  private SongEditor songEditor;

  private SongPart part;

  private Label lblSongPartTitle = new Label("Bla");

  private HBox rootPane = new HBox(0);

  private VBox contentPane = new VBox(0);

  private boolean editable;


  public PartEditor(final SongEditor songEditor, final SongPart part, final boolean editable) {
    this.editable = editable;
    this.part = part;
    this.songEditor = songEditor;

      contentPane.setPadding(new Insets(20, 5, 20, 5));
      contentPane.setId("parteditorcontentPane");
      contentPane.setStyle(""
          + " -fx-border: 12px solid; -fx-border-color: gray; -fx-background-radius: 9.0;"
          + " -fx-border-radius: 9.0");
      contentPane.setFillWidth(true);

      contentPane.setPrefWidth(Consts.DEFAULT_WIDTH - Consts.DEFAULT_LISTVIEW_WIDTH);
      rootPane.setPrefWidth(Consts.DEFAULT_WIDTH - Consts.DEFAULT_LISTVIEW_WIDTH);

      setIndex("parteditor");
      rootPane.getChildren().add(lblSongPartTitle);
      rootPane.getChildren().add(contentPane);
      setPanel(rootPane);
      reload();


  }

  public void reload() {
    contentPane.getChildren().clear();

    SongPart shownPart = part;
    if (part.getReferencedSongPart() != null)
      shownPart = songEditor.getSong().findSongPartByUUID(part.getReferencedSongPart());

    int index = 0;
    for (Line nextLine : shownPart.getLines()) {
      LineEditor lineEditor = new LineEditor(this, nextLine, part.getReferencedSongPart() == null, "editor_" + index++);
      lineEditors.add(lineEditor);
      contentPane.getChildren().add(lineEditor.getPanel());
    }

    String textCssId = part.getReferencedSongPart() != null ? "texteditor_disabled": "texteditor";
    String chordCssId = part.getReferencedSongPart() != null ? "chordlabel_disabled": "chordlabel";

    if (getFirstLineEditor() != null)
      getFirstLineEditor().getFirstLinePartEditor().toHome();

    for (LineEditor nextLineEditor: lineEditors) {
      for (LinePartEditor nextLinePartEditor: nextLineEditor.getLinePartEditors()) {
        nextLinePartEditor.getTxtText().setId(textCssId);
        nextLinePartEditor.getLblChord().setId(chordCssId);
      }

    }

    System.out.println ("set id to " + contentPane.getId());

  }


  public LineEditor getLastLineEditor() {
    return lineEditors.get(lineEditors.size() - 1);
  }

  public LineEditor getFirstLineEditor() {
    return !lineEditors.isEmpty() ? lineEditors.get(0) : null;
  }


  public List<LineEditor> getLineEditors() {
    return lineEditors;
  }

  public void save() {
    for (LineEditor nextLine : lineEditors) {
      nextLine.save();
    }
  }


  public SongEditor getSongEditor() {
    return songEditor;
  }

  public SongPart getPart() {
    return part;
  }

  public boolean hasChanged() {
    for (LineEditor nextLine : lineEditors) {
      if (nextLine.hasChanged())
        return true;
    }
    return false;
  }

}
