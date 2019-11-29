package org.adonai.ui.editor2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.adonai.StringUtils;
import org.adonai.model.Configuration;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.services.AddPartService;
import org.adonai.services.RemovePartService;
import org.adonai.services.SongCursor;
import org.adonai.services.SongInfoService;
import org.adonai.services.SongRepairer;
import org.adonai.ui.Consts;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.UiUtils;
import org.controlsfx.control.PopOver;

/**
 * Created by OleyMa on 22.11.16.
 */
public class SongEditor extends PanelHolder {

  private static final Logger LOGGER = Logger.getLogger(SongEditor.class.getName());


  private Song song;

  private VBox content = new VBox(5); //spacing between parts

  private List<PartEditor> partEditors = new ArrayList<>();

  private ContextMenu contextMenu = new ContextMenu();

  private AddPartService addPartService = new AddPartService();

  private RemovePartService removePartService = new RemovePartService();

  private SongInfoService songInfoService = new SongInfoService();

  private Label lblHeaderInfo = new Label();

  private ToolBar tbaActions = new ToolBar();

  private HBox header = new HBox();


  private Button btnCurrentChord = new Button();

  private Button btnOriginalChord = new Button();


  private Button btnSongInfo = new Button();

  private Configuration configuration;

  private BooleanProperty showOriginChords = new SimpleBooleanProperty(false);

  public SongEditor(Configuration configuration, Song song) {
    this.configuration = configuration;
    this.song = song;

    setIndex("songeditor");
    tbaActions.setStyle("-fx-background-color: transparent;");

    btnOriginalChord.setTooltip(new Tooltip("Show original chords"));
    btnOriginalChord.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        showOriginChords.setValue(true);
        adaptChordType(showOriginChords.get());
      }
    });
    tbaActions.getItems().add(btnOriginalChord);



    btnCurrentChord.setTooltip(new Tooltip("Show current chords"));
    btnCurrentChord.setGraphic(Consts.createIcon("fa-arrow-right", Consts.ICON_SIZE_VERY_SMALL));
    btnCurrentChord.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        showOriginChords.setValue(false);
        adaptChordType(showOriginChords.get());
      }
    });
    tbaActions.getItems().add(btnCurrentChord);


    btnSongInfo.setTooltip(new Tooltip("Edit song informations"));
    btnSongInfo.setGraphic(Consts.createIcon("fa-cogs", Consts.ICON_SIZE_VERY_SMALL));
    btnSongInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {

        MaskLoader<SongDetailsController> maskLoader = new MaskLoader();
        Mask<SongDetailsController> mask = maskLoader.load("editor2/songdetails");
        Bounds boundsBtnSongInfo = UiUtils.getBounds(btnSongInfo);
        mask.setPosition(boundsBtnSongInfo.getCenterX() - 800, boundsBtnSongInfo.getMaxY() + 20);
        mask.setSize(800, 400);
        SongDetailsController songDetailsController = mask.getController();
        songDetailsController.setCurrentSong(song);
        songDetailsController.setConfiguration(configuration);
        songDetailsController.init();
        UiUtils.hideOnEsc(mask.getStage());
        UiUtils.hideOnFocusLost(mask.getStage());

        mask.getStage().setOnHiding(new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            reload();
          }
        });
        mask.show();

      }
    });
    tbaActions.getItems().add(btnSongInfo);



    BorderPane root = new BorderPane();
    setPanel(root);

    ScrollPane scrollPane = new ScrollPane();

    content.setPadding(new Insets(20, 20, 20, 20));
    scrollPane.setContent(content);
    root.setId("songeditor");

    String originalKey = StringUtils.getNotNull(song.getOriginalKey());
    String currentKey = StringUtils.getNotNull(song.getCurrentKey());
    String open = song.getCurrentKey() != null ? " ( Original " : "";
    String close = song.getCurrentKey() != null ? " ) " : "";

    lblHeaderInfo.setMaxHeight(Double.MAX_VALUE);


    HBox.setMargin(lblHeaderInfo, new Insets(3, 100, 0, 10));
    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    header.getChildren().add(lblHeaderInfo);
    header.getChildren().add(region);
    header.getChildren().add(tbaActions);
    header.setId("songheader");


    root.setCenter(scrollPane);
    root.setTop(header);

    root.setPrefWidth(Double.MAX_VALUE);

    MenuItem menuItemAddPartBefore = new MenuItem("Add part before");
    menuItemAddPartBefore.setId("menuitemAddPartBefore");
    menuItemAddPartBefore.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongPart focusedSongPart = addPartService.addPartBefore(getSongCursor());
        reload().focus(focusedSongPart);
      }
    });

    MenuItem menuItemRemovePart = new MenuItem("Remove");
    menuItemRemovePart.setId("menuItemRemovePart");
    menuItemRemovePart.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongPart focusedSongPart = removePartService.removePart(getSongCursor());
        reload().focus(focusedSongPart);
      }
    });

    MenuItem menuItemAddPartAfter = new MenuItem("Add part after");
    menuItemAddPartAfter.setId("menuitemAddPartAfter");
    menuItemAddPartAfter.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongPart focusedSongPart = addPartService.addPartAfter(getSongCursor());
        reload().focus(focusedSongPart);

      }
    });

    contextMenu.getItems().add(menuItemAddPartBefore);
    contextMenu.getItems().add(menuItemRemovePart);
    contextMenu.getItems().add(menuItemAddPartAfter);

    reload();

    PartEditor firstPartEditor = getFirstPartEditor();
    if (firstPartEditor != null)
      firstPartEditor.getFirstLineEditor().getLinePartEditors().get(0).requestFocus(false);
  }

  public void adaptChordType (final boolean showOriginChords) {
    for (PartEditor nextPartEditor:partEditors) {
      for (LineEditor nextLineEditor: nextPartEditor.getLineEditors()) {
        for (LinePartEditor nextLinePartEditor: nextLineEditor.getLinePartEditors()) {
          nextLinePartEditor.toggleChordType(showOriginChords);
        }
      }

    }
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

  public PartEditor getFirstPartEditor () {
    return partEditors.size() > 0 ? partEditors.get(0): null;
  }

  public LinePartEditor getPartEditor(LinePart linePart) {

    for (PartEditor nextPartEditor : partEditors) {
      for (LineEditor nextLineEditor : nextPartEditor.getLineEditors()) {
        for (LinePartEditor nexLinePartEditor : nextLineEditor.getLinePartEditors()) {
          if (nexLinePartEditor.getLinePart().equals(linePart)) {

            LOGGER.info("getPartEditor " + nexLinePartEditor);
            return nexLinePartEditor;
          }
        }
      }
    }
    throw new IllegalStateException("Did not find editor for linepart " + linePart);

  }

  public void reloadDetail() {
    content.getChildren().clear();

    partEditors.clear();

    btnOriginalChord.setDisable(song.getOriginalKey() == null || song.getOriginalKey().trim().isEmpty());
    btnOriginalChord.setText(song.getOriginalKey() != null ? song.getOriginalKey(): "unset");
    btnCurrentChord.setDisable(song.getCurrentKey() == null || song.getCurrentKey().trim().isEmpty());
    btnCurrentChord.setText(song.getCurrentKey() != null ? song.getCurrentKey(): "unset");


    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);


    for (SongPart next : song.getSongParts()) {
      PartEditor currentPartEditor = new PartEditor(this, next, next.getReferencedSongPart() == null);
      partEditors.add(currentPartEditor);
      content.getChildren().add(currentPartEditor.getPanel());
    }

  }

  public SongEditor reload() {

    reloadDetail();
    return this;
  }

  public Song getSong() {
    return song;
  }

  public boolean isShowOriginalChords () {
    return showOriginChords.get();
  }

  public void save() {
    throw new IllegalStateException("NYI");
    //currentPartEditor.save();
  }

  public boolean hasChange() {
    throw new IllegalStateException("NYI");
    //return currentPartEditor.hasChanged();
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
        if (event.getGestureSource() != thisCell && event.getDragboard().hasString()) {
          event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
      });

      setOnDragEntered(event -> {
        if (event.getGestureSource() != thisCell && event.getDragboard().hasString()) {
          setOpacity(0.3);
        }
      });

      setOnDragExited(event -> {
        if (event.getGestureSource() != thisCell && event.getDragboard().hasString()) {
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

    @Override protected void updateItem(SongPart item, boolean empty) {

      super.updateItem(item, empty);

      if (item != null) {
        setText(songInfoService.getPreview(song, item));
      } else
        setText(null);

    }
  }
}
