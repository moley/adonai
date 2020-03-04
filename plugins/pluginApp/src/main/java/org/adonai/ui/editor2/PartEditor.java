package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.services.AddPartService;
import org.adonai.services.RemovePartService;
import org.adonai.services.SongCursor;
import org.adonai.ui.Consts;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.UiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 22.11.16.
 */
public class PartEditor extends PanelHolder {

  private static final Logger LOGGER = LoggerFactory.getLogger(PartEditor.class);

  private List<LineEditor> lineEditors = new ArrayList<LineEditor>();

  private SongEditor songEditor;

  private SongStructItem structItem;

  private VBox bbaSongPartActions = new VBox();

  private HBox rootPane = new HBox();

  private VBox contentPane = new VBox();

  private SongPartColorMap songPartColorMap = new SongPartColorMap();

  private int partIndex;

  Label label;

  TitledPane titledPane = new TitledPane();

  private SongCursor getSongCursor() {
    Song song = songEditor.getSong();
    return new SongCursor(song, song.getIndex(structItem), 0, 0, 0);
  }

  private void reloadTitle() {
    String type = structItem.getText();
    if (structItem.getQuantity() != null && !structItem.getQuantity().trim()
        .isEmpty()) { //quantity is always used from the part itself
      type += " (" + structItem.getQuantity().trim() + "x)";
    }

    label.setText(type);
    label.setUserData(getPointer() + "lblHeader");

    SongPart songPart = getCurrentSongPart();

    String color = songPartColorMap.getColorForPart(songPart);
    String colorSelected = songPartColorMap.getColorSelectedForPart(songPart);
    titledPane.setStyle("-fx-color: " + color + "; -fx-focus-color: " + colorSelected);
    titledPane.setUserData(getPointer() + "paTitledPane");
  }

  private String getPointer() {
    return "songeditor.part_" + partIndex + ".";
  }

  public PartEditor(final SongEditor songEditor, final SongStructItem structItem, final int partIndex) {
    this.structItem = structItem;
    Song song = songEditor.getSong();

    LOGGER.info("Create new parteditor for part " + structItem.getPartId() + ": " + System.identityHashCode(this));

    this.songEditor = songEditor;
    this.partIndex = partIndex;

    rootPane.setPadding(new Insets(0, 10, 0, 10));
    rootPane.setUserData(getPointer() + "paRootPane");

    label = new Label();
    reloadTitle();

    label.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        bbaSongPartActions.setVisible(true);
      }
    });

    contentPane.setMinWidth(Consts.getDefaultWidth() * 0.5);
    contentPane.setUserData(getPointer() + "paContentPane");

    titledPane.setUserData(getPointer() + "paTitledPane");
    titledPane.setContent(contentPane);
    titledPane.setCollapsible(false);
    titledPane.setGraphic(label);

    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        bbaSongPartActions.setVisible(false);

        MaskLoader<SongPartDetailsController> maskLoader = new MaskLoader();
        Mask<SongPartDetailsController> mask = maskLoader.load("editor2/songpartdetails");
        Bounds boundsBtnSongInfo = UiUtils.getBounds(getSongEditor().getPanel());
        mask.setPosition(boundsBtnSongInfo.getMinX() + 20, boundsBtnSongInfo.getMinX() + 30);
        mask.setSize(800, 400);
        SongPartDetailsController songPartDetailsController = mask.getController();
        songPartDetailsController.setCurrentSong(songEditor.getSong());
        songPartDetailsController.setCurrentSongStructItem(structItem);
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
    btnAddBefore.setUserData(getPointer() + "btnAddBefore");
    btnAddBefore.setGraphic(Consts.createIcon("fa-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAddBefore.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        MaskLoader<AddPartController> maskLoader = new MaskLoader();
        Mask<AddPartController> mask = maskLoader.load("editor2/addpart");
        Bounds boundsBtnSongInfo = UiUtils.getBounds(getSongEditor().getPanel());
        mask.setPosition(boundsBtnSongInfo.getMinX() + 20, boundsBtnSongInfo.getMinX() + 30);
        mask.setSize(500, 700);
        AddPartController addPartController = mask.getController();
        addPartController.init(mask, getSongEditor().getSong());


        mask.getStage().setOnHidden(new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            LOGGER.info("onHidden called on addBefore");
            if (addPartController.getSelectedType() != null) {
              LOGGER.info("... with selected type " + addPartController.getSelectedType());
              SongCursor songCursor = getSongCursor();
              AddPartService addPartService = new AddPartService();
              SongStructItem newSongStruct = addPartService.addPartBefore(songCursor);
              SongPart newSongPart = song.findSongPart(newSongStruct);
              String selectedItem = addPartController.getSelectedType();
              SongPart copiedPart = addPartController.getSongPart(selectedItem);
              SongPartType songPartType = addPartController.getNewType(selectedItem);
              if (copiedPart != null) {
                newSongPart.setSongPartType(copiedPart.getSongPartType());
                newSongPart.setReferencedSongPart(copiedPart.getId());
              } else
                newSongPart.setSongPartType(songPartType);

              getSongEditor().reload();
            }
          }
        });
        mask.show();

      }
    });

    Button btnMoveUp = new Button();
    btnMoveUp.setGraphic(Consts.createIcon("fa-angle-up", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveUp.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        LOGGER.info("Move up");
      }
    });

    Button btnRemove = new Button();
    btnRemove.setId("iconbutton");
    btnRemove.setGraphic(Consts.createIcon("fa-remove", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongCursor songCursor = getSongCursor();
        LOGGER.info("Remove" + songCursor.toString());
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
        LOGGER.info("Move down");
      }
    });

    Button btnAddAfter = new Button();
    btnAddAfter.setUserData(getPointer() + "btnAddAfter");
    btnAddAfter.setGraphic(Consts.createIcon("fa-plus", Consts.ICON_SIZE_VERY_SMALL));

    btnAddAfter.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        MaskLoader<AddPartController> maskLoader = new MaskLoader();
        Mask<AddPartController> mask = maskLoader.load("editor2/addpart");
        Bounds boundsBtnSongInfo = UiUtils.getBounds(getSongEditor().getPanel());
        mask.setPosition(boundsBtnSongInfo.getMinX() + 20, boundsBtnSongInfo.getMinX() + 30);
        mask.setSize(500, 700);
        AddPartController addPartController = mask.getController();
        addPartController.init(mask, getSongEditor().getSong());
        UiUtils.hideOnEsc(mask.getStage());
        UiUtils.hideOnFocusLost(mask.getStage());

        mask.getStage().setOnHidden(new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            LOGGER.info("onHidden called on addAfter");
            if (addPartController.getSelectedType() != null) {
              LOGGER.info("... with selected type " + addPartController.getSelectedType());

              SongCursor songCursor = getSongCursor();
              AddPartService addPartService = new AddPartService();
              SongStructItem newStructItem = addPartService.addPartAfter(songCursor); //TODO move to service together with addPartBefore
              SongPart newSongPart = song.findSongPart(newStructItem);
              String selectedItem = addPartController.getSelectedType();
              SongPart copiedPart = addPartController.getSongPart(selectedItem);
              SongPartType songPartType = addPartController.getNewType(selectedItem);
              if (copiedPart != null) {
                newSongPart.setSongPartType(copiedPart.getSongPartType());
                newSongPart.setReferencedSongPart(copiedPart.getId());
              } else
                newSongPart.setSongPartType(songPartType);

              getSongEditor().reload();
            }
          }
        });
        mask.show();
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

  public SongPart getCurrentSongPart () {
    return songEditor.getSong().findSongPart(structItem);
  }

  public SongStructItem getCurrentSongStructItem () {
    return structItem;
  }

  public void reload() {
    contentPane.getChildren().clear();

    SongPart songPart = getCurrentSongPart();

    for (int i = 0; i < getCurrentSongPart().getLines().size(); i++) {
      Line nextLine = songPart.getLines().get(i);
      LineEditor lineEditor = new LineEditor(this, nextLine, songPart.getReferencedSongPart() == null, partIndex, i);
      lineEditors.add(lineEditor);
      contentPane.getChildren().add(lineEditor.getPanel());
    }

    String textCssId = "texteditor";
    String chordCssId = "chordlabel";

    for (LineEditor nextLineEditor : lineEditors) {
      for (LinePartEditor nextLinePartEditor : nextLineEditor.getLinePartEditors()) {
        nextLinePartEditor.getTxtText().setId(textCssId);
        nextLinePartEditor.getLblChord().setId(chordCssId);
      }

    }

  }

  public LineEditor getFirstLineEditor() {
    return !lineEditors.isEmpty() ? lineEditors.get(0) : null;
  }

  public List<LineEditor> getLineEditors() {
    return lineEditors;
  }


  public SongEditor getSongEditor() {
    return songEditor;
  }

}
