package org.adonai.ui.editor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.adonai.model.Line;
import org.adonai.model.SongPart;
import org.adonai.ui.Consts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OleyMa on 22.11.16.
 */
public class PartEditor extends PanelHolder {

  private List<LineEditor> lineEditors = new ArrayList<LineEditor>();


  private SongEditor songEditor;

  private SongPart part;

  private TabPane rootPane = new TabPane();

  private VBox contentPane = new VBox(0);


  private boolean editable;


  public PartEditor(final SongEditor songEditor, final SongPart part, final boolean editable) {
    this.editable = editable;
    this.part = part;
    this.songEditor = songEditor;
    try {

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/lyricsextractor/songpartdetails.fxml"));
      Parent songdetailsParent = fxmlLoader.load();
      SongPartDetailsController songPartDetailsController = fxmlLoader.getController();
      songPartDetailsController.setPartEditor(this);

      contentPane.setPadding(new Insets(20, 5, 20, 5));
      contentPane.setFillWidth(true);

      Tab contentTab = new Tab("Part content");
      contentTab.setContent(contentPane);

      Tab detailsTab = new Tab("Part details");
      detailsTab.setContent(songdetailsParent);
      rootPane.getTabs().add(contentTab);
      rootPane.getTabs().add(detailsTab);
      rootPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
        @Override
        public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
          System.out.println("Hallo " + part.toString());
          reload();

        }
      });

      rootPane.setPrefWidth(Consts.DEFAULT_WIDTH - Consts.DEFAULT_LISTVIEW_WIDTH);

      setPanel(rootPane);
      reload();

    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public void reload() {
    contentPane.getChildren().clear();

    SongPart shownPart = part;
    if (part.getReferencedSongPart() != null)
      shownPart = songEditor.getSong().findSongPartByUUID(part.getReferencedSongPart());

    for (Line nextLine : shownPart.getLines()) {
      LineEditor lineEditor = new LineEditor(this, nextLine, part.getReferencedSongPart() == null);
      lineEditors.add(lineEditor);
      contentPane.getChildren().add(lineEditor.getPanel());
    }

    String textCssId = part.getReferencedSongPart() != null ? "texteditor_disabled": "texteditor";
    String chordCssId = part.getReferencedSongPart() != null ? "chordlabel_disabled": "chordlabel";

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
