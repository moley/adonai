package org.adonai.ui.editor2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.services.AddPartService;
import org.adonai.services.RemovePartService;
import org.adonai.services.SongCursor;
import org.adonai.ui.Consts;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.UiUtils;
import org.controlsfx.control.PopOver;

/**
 * Created by OleyMa on 22.11.16.
 */
public class PartEditor extends PanelHolder {

  private List<LineEditor> lineEditors = new ArrayList<LineEditor>();

  private SongEditor songEditor;

  private SongPart part;

  private VBox bbaSongPartActions = new VBox();

  private HBox rootPane = new HBox();

  private VBox contentPane = new VBox();


  private SongPartColorMap songPartColorMap = new SongPartColorMap();

  private boolean editable;


  Label label;

  TitledPane titledPane = new TitledPane();



  private SongCursor getSongCursor () {
    Song song = songEditor.getSong();
    return new SongCursor(song, song.getIndex(part), 0, 0, 0);
  }

  private void reloadTitle () {
    boolean isRef = part.getReferencedSongPart() != null;
    String type = getShownPart().getSongPartTypeLabel();

    if (isRef)
      label.setGraphic(Consts.createIcon("fa-external-link", Consts.ICON_SIZE_VERY_SMALL));

    label.setText(type);

    String color = songPartColorMap.getColorForPart(getShownPart());
    String colorSelected = songPartColorMap.getColorSelectedForPart(getShownPart());
    titledPane.setStyle("-fx-color: " + color + "; -fx-focus-color: " + colorSelected );
  }

  public PartEditor(final SongEditor songEditor, final SongPart part, final boolean editable) {
    this.editable = editable;
    this.part = part;
    this.songEditor = songEditor;

    rootPane.setPadding(new Insets(0, 10, 0, 10));

    label = new Label();
    reloadTitle();

    label.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        bbaSongPartActions.setVisible(true);
      }
    });

    contentPane.setMinWidth(Consts.DEFAULT_WIDTH * 0.5);

    titledPane.setContent(contentPane);
    titledPane.setCollapsible(false);
    titledPane.setGraphic(label);
    contentPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        bbaSongPartActions.setVisible(false);
      }
    });
    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        bbaSongPartActions.setVisible(false);

        MaskLoader<SongPartDetailsController> maskLoader = new MaskLoader();
        Mask<SongPartDetailsController> mask = maskLoader.load("editor2/songpartdetails");
        Bounds boundsLabel = UiUtils.getBounds(label);
        mask.setPosition(boundsLabel.getMaxX() + 20, boundsLabel.getMinY());
        mask.setSize(800, 400);
        SongPartDetailsController songPartDetailsController = mask.getController();
        songPartDetailsController.setCurrentSong(songEditor.getSong());
        songPartDetailsController.setCurrentSongPart(part);
        songPartDetailsController.init();

        UiUtils.hideOnEsc(mask.getStage());
        UiUtils.hideOnFocusLost(mask.getStage());

        mask.getStage().setOnHiding(new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            reloadTitle();
          }
        });

        mask.show();

      }
    });

    rootPane.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        bbaSongPartActions.setVisible(false);
      }
    });

    Button btnAddBefore = new Button();
    btnAddBefore.setGraphic(Consts.createIcon("fa-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAddBefore.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        SongCursor songCursor = getSongCursor();
        System.out.println("Add Before in " + songCursor.toString());
        AddPartService addPartService = new AddPartService();
        addPartService.addPartBefore(songCursor);
        getSongEditor().reload();


      }
    });

    Button btnMoveUp = new Button();
    btnMoveUp.setGraphic(Consts.createIcon("fa-angle-up", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveUp.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        System.out.println("Move up");
      }
    });

    Button btnRemove = new Button();
    btnRemove.setId("iconbutton");
    btnRemove.setGraphic(Consts.createIcon("fa-remove", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongCursor songCursor = getSongCursor();
        System.out.println("Remove" + songCursor.toString());
        RemovePartService removePartService = new RemovePartService();
        removePartService.removePart(songCursor);
        getSongEditor().reload();
      }
    });

    contentPane.setPadding(new Insets(20, 5, 20, 5));
    contentPane.setId("parteditorcontentPane");
    contentPane.setFillWidth(true);

    Button btnMoveDown = new Button();
    btnMoveDown.setGraphic(Consts.createIcon("fa-angle-down", Consts.ICON_SIZE_VERY_SMALL));

    btnMoveDown.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        System.out.println("Move down");
      }
    });

    Button btnAddAfter = new Button();
    btnAddAfter.setGraphic(Consts.createIcon("fa-plus", Consts.ICON_SIZE_VERY_SMALL));

    btnAddAfter.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongCursor songCursor = getSongCursor();
        System.out.println("Add After in " + songCursor.toString());
        AddPartService addPartService = new AddPartService();
        addPartService.addPartAfter(songCursor);
        getSongEditor().reload();
      }
    });



    bbaSongPartActions.setVisible(false);
    bbaSongPartActions.setFillWidth(false);
    bbaSongPartActions.getChildren().addAll(btnAddBefore,
                                            //btnMoveUp,
                                            btnRemove,
                                            //btnMoveDown,
                                            btnAddAfter);

    rootPane.setSpacing(5);
    rootPane.setAlignment(Pos.CENTER_LEFT);


    setIndex("parteditor");


    rootPane.getChildren().add(bbaSongPartActions);
    rootPane.getChildren().add(titledPane);
    setPanel(rootPane);
    reload();

  }

  private SongPart getShownPart () {
    SongPart shownPart = part;
    if (part.getReferencedSongPart() != null)
      shownPart = songEditor.getSong().findSongPartByUUID(part.getReferencedSongPart());
    return shownPart;
  }

  public void reload() {
    contentPane.getChildren().clear();



    int index = 0;
    for (Line nextLine : getShownPart().getLines()) {
      LineEditor lineEditor = new LineEditor(this, nextLine, part.getReferencedSongPart() == null, "editor_" + index++);
      lineEditors.add(lineEditor);
      contentPane.getChildren().add(lineEditor.getPanel());
    }

    String textCssId = part.getReferencedSongPart() != null ? "texteditor_disabled" : "texteditor";
    String chordCssId = part.getReferencedSongPart() != null ? "chordlabel_disabled" : "chordlabel";

    for (LineEditor nextLineEditor : lineEditors) {
      for (LinePartEditor nextLinePartEditor : nextLineEditor.getLinePartEditors()) {
        nextLinePartEditor.getTxtText().setId(textCssId);
        nextLinePartEditor.getLblChord().setId(chordCssId);
      }

    }

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
