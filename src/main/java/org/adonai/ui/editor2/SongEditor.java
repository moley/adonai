package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.adonai.model.Configuration;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.services.AddPartService;
import org.adonai.services.RemovePartService;
import org.adonai.services.SongCursor;
import org.adonai.services.SongInfoService;
import org.adonai.ui.Consts;

/**
 * Created by OleyMa on 22.11.16.
 */
public class SongEditor extends PanelHolder {

  private Song song;

  private VBox content = new VBox(30);

  private List<PartEditor> partEditors = new ArrayList<>();

  private HashMap<SongPartType, Color> colorMap = new HashMap<SongPartType, Color>();

  private ContextMenu contextMenu = new ContextMenu();

  private AddPartService addPartService = new AddPartService();

  private RemovePartService removePartService = new RemovePartService();

  private SongInfoService songInfoService = new SongInfoService();
  private TextField txtTitle = new TextField();

  private Configuration configuration;

  public SongEditor(Configuration configuration, Song song) {
    this.configuration = configuration;
    this.song = song;

    colorMap.put(SongPartType.REFRAIN, Color.DARKBLUE);
    colorMap.put(SongPartType.VERS, Color.DARKRED);
    colorMap.put(SongPartType.INSTRUMENTAL, Color.DARKGRAY);
    colorMap.put(SongPartType.BRIDGE, Color.DARKGRAY);
    colorMap.put(SongPartType.INTRO, Color.DARKGRAY);
    colorMap.put(SongPartType.ZWISCHENSPIEL, Color.DARKGRAY);

    txtTitle.textProperty().bindBidirectional(song.titleProperty());
    setIndex("songeditor");



    BorderPane root = new BorderPane();
    setPanel(root);

    ScrollPane scrollPane = new ScrollPane();

    content.setPadding(new Insets(5, 10, 5, 10));
    scrollPane.setContent(content);
    root.setId("songeditor");
    scrollPane.setPrefWidth(Consts.DEFAULT_WIDTH - Consts.DEFAULT_LISTVIEW_WIDTH);
    txtTitle.setId("songtitle");

    root.setCenter(scrollPane);
    root.setTop(txtTitle);

    root.setPrefWidth(Double.MAX_VALUE);

    txtTitle.textProperty().addListener((ov, oldValue, newValue) -> {
      txtTitle.setText(newValue.toUpperCase());
    });



    MenuItem menuItemAddPartBefore = new MenuItem("Add part before");
    menuItemAddPartBefore.setId("menuitemAddPartBefore");
    menuItemAddPartBefore.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SongPart focusedSongPart = addPartService.addPartBefore(getSongCursor());
        reload().focus(focusedSongPart);
      }
    });

    MenuItem menuItemRemovePart = new MenuItem("Remove");
    menuItemRemovePart.setId("menuItemRemovePart");
    menuItemRemovePart.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SongPart focusedSongPart = removePartService.removePart(getSongCursor());
        reload().focus(focusedSongPart);
      }
    });

    MenuItem menuItemAddPartAfter = new MenuItem("Add part after");
    menuItemAddPartAfter.setId("menuitemAddPartAfter");
    menuItemAddPartAfter.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        SongPart focusedSongPart = addPartService.addPartAfter(getSongCursor());
        reload().focus(focusedSongPart);

      }
    });


    contextMenu.getItems().add(menuItemAddPartBefore);
    contextMenu.getItems().add(menuItemRemovePart);
    contextMenu.getItems().add(menuItemAddPartAfter);


    reload();


    //if (currentPartEditor != null)
    //  currentPartEditor.getFirstLineEditor().getLinePartEditors().get(0).requestFocus(false);
  }

  public SongCursor getSongCursor() {
    SongCursor songCursor = new SongCursor();
    songCursor.setCurrentSong(song);
    //songCursor.setCurrentSongPart(partStructure.getSelectionModel().getSelectedItem());
    songCursor.setCurrentLine(songCursor.getCurrentSongPart().getFirstLine());
    if (songCursor.getCurrentLine() != null)
      songCursor.setCurrentLinePart(songCursor.getCurrentLine().getFirstLinePart());
    songCursor.setPositionInLinePart(0);
    return songCursor;
  }

  public void focus(SongPart songPart) {


  }

  public LinePartEditor getPartEditor(LinePart linePart) {
    throw new IllegalStateException("NYI");
    /**for (LineEditor nextLineEditor : currentPartEditor.getLineEditors()) {
      for (LinePartEditor nexLinePartEditor : nextLineEditor.getLinePartEditors()) {
        if (nexLinePartEditor.getLinePart().equals(linePart)) {
          System.out.println ("Focus txtfield of " + nexLinePartEditor.getLinePart().getText());
          return nexLinePartEditor;
        }
      }
    }
    throw new IllegalStateException("Did not find editor for linepart " + linePart);**/

  }

  public void reloadDetail() {
    content.getChildren().clear();

    for (SongPart next: song.getSongParts()) {
      PartEditor currentPartEditor = new PartEditor(this, next, next.getReferencedSongPart() == null);
      content.getChildren().add(currentPartEditor.getPanel());
    }

    /**SongPart selectedSongPart = partStructure.getSelectionModel().getSelectedItem();
    if (selectedSongPart != null) {
      currentPartEditor = new PartEditor(this, selectedSongPart, selectedSongPart.getReferencedSongPart() == null);
      content.getChildren().add(currentPartEditor.getPanel());
    }**/

  }

  public SongEditor reload() {

    reloadDetail();
    return this;
  }


  public Song getSong() {
    return song;
  }

  public void save() {
    throw new IllegalStateException("NYI");
    //currentPartEditor.save();
  }

  public boolean hasChange() {
    throw new IllegalStateException("NYI");
    //return currentPartEditor.hasChanged();
  }

  public Color getColorForPart(SongPart songPart) {

    Color color = null;
    if (songPart != null && songPart.getSongPartType() != null) {
      color = colorMap.get(songPart.getSongPartType());
      System.out.println("Determined color " + color + " for type " + songPart.getSongPartType());
    }
    if (color == null)
      color = Color.BLACK;


    return color;
  }


  public void log() {
    System.out.println("After save:" + song.toString());
  }

  public Configuration getConfiguration() {
    return configuration;
  }


  private class SongPartCell extends ListCell<SongPart> {

    public SongPartCell() {
      ListCell thisCell = this;

      setOnDragDetected(event -> {
        if (getItem() == null) {
          return;
        }

        Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(new Integer(getItem().hashCode()).toString());
        dragboard.setContent(content);

        event.consume();
      });

      setOnDragOver(event -> {
        if (event.getGestureSource() != thisCell &&
          event.getDragboard().hasString()) {
          event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
      });

      setOnDragEntered(event -> {
        if (event.getGestureSource() != thisCell &&
          event.getDragboard().hasString()) {
          setOpacity(0.3);
        }
      });

      setOnDragExited(event -> {
        if (event.getGestureSource() != thisCell &&
          event.getDragboard().hasString()) {
          setOpacity(1);
        }
      });

      setOnDragDropped(event -> {
        if (getItem() == null) {
          return;
        }

        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
          ObservableList<SongPart> items = getListView().getItems();
          int draggedIdx = -1;
          for (int i = 0; i < items.size(); i++) {
            if (new Integer(items.get(i).hashCode()).toString().equals(db.getString()))
              draggedIdx = i;

          }

          int thisIdx = items.indexOf(getItem());

          System.out.println("Vorher: " + song);

          SongPart temp = song.getSongParts().get(draggedIdx);
          song.getSongParts().set(draggedIdx, song.getSongParts().get(thisIdx));
          song.getSongParts().set(thisIdx, temp);

          items.set(draggedIdx, getItem());
          items.set(thisIdx, temp);

          System.out.println("Nachher: " + song);

          getListView().setItems(FXCollections.observableArrayList(song.getSongParts()));

          success = true;

        }
        event.setDropCompleted(success);

        event.consume();
      });

      setOnDragDone(DragEvent::consume);
    }

    @Override
    protected void updateItem(SongPart item, boolean empty) {

      super.updateItem(item, empty);

      if (item != null) {
        setText(songInfoService.getPreview(song, item));
      }
      else
        setText(null);

    }
  }
}
