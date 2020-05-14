package org.adonai.ui.editor2;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.adonai.ApplicationEnvironment;
import org.adonai.model.Configuration;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongStructItem;
import org.adonai.services.SongCursor;
import org.adonai.services.SongRepairer;
import org.adonai.ui.Consts;
import org.adonai.ui.Mask;
import org.adonai.ui.MaskLoader;
import org.adonai.ui.UiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 22.11.16.
 */
@Deprecated
public class SongEditor extends PanelHolder {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongEditor.class);


  private Song song;

  private VBox content = new VBox(5); //spacing between parts

  private List<PartEditor> partEditors = new ArrayList<>();

  private Label lblHeaderInfo = new Label();

  private ToolBar tbaActions = new ToolBar();

  private HBox header = new HBox();

  private double currentScrollPosition;


  private Button btnCurrentChord = new Button();

  private Button btnOriginalChord = new Button();

  private Button btnStart = new Button();
  private Button btnEnd = new Button();




  private Button btnSongInfo = new Button();

  private Configuration configuration;

  private ApplicationEnvironment applicationEnvironment;

  ScrollPane scrollPane = new ScrollPane();


  private BooleanProperty showOriginChords = new SimpleBooleanProperty(false);

  public SongEditor(ApplicationEnvironment applicationEnvironment, Configuration configuration, Song song) {
    this.configuration = configuration;
    this.song = song;
    this.applicationEnvironment = applicationEnvironment;

    setIndex("songeditor");
    tbaActions.setStyle("-fx-background-color: transparent;");
    tbaActions.setUserData("songeditor.tbaActions");

    btnStart.setTooltip(new Tooltip("Navigate to the beginning of the song"));
    btnStart.setUserData("songeditor.btnStart");
    btnStart.setGraphic(Consts.createIcon("fa-arrow-up", Consts.ICON_SIZE_VERY_SMALL));
    btnStart.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        getScrollPane().setVvalue(0);
      }
    });

    tbaActions.getItems().add(btnStart);

    btnEnd.setTooltip(new Tooltip("Navigate to the end of the song"));
    btnEnd.setUserData("songeditor.btnEnd");
    btnEnd.setGraphic(Consts.createIcon("fa-arrow-down", Consts.ICON_SIZE_VERY_SMALL));
    btnEnd.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        getScrollPane().setVvalue(1);
      }
    });

    tbaActions.getItems().add(btnEnd);

    btnOriginalChord.setTooltip(new Tooltip("Show original chords"));
    btnOriginalChord.setUserData("songeditor.btnOriginalChord");
    btnOriginalChord.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        showOriginChords.setValue(true);
        adaptChordType(showOriginChords.get());
      }
    });
    tbaActions.getItems().add(btnOriginalChord);



    btnCurrentChord.setUserData("songeditor.btnCurrentChord");
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
    btnSongInfo.setUserData("songeditor.btnSongInfo");
    btnSongInfo.setGraphic(Consts.createIcon("fa-cogs", Consts.ICON_SIZE_VERY_SMALL));
    btnSongInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {

        MaskLoader<SongDetailsController> maskLoader = new MaskLoader();
        Mask<SongDetailsController> mask = maskLoader.load(applicationEnvironment, "editor2/songdetails");
        Bounds boundsBtnSongInfo = UiUtils.getBounds(btnSongInfo);
        mask.setPosition(boundsBtnSongInfo.getCenterX() - 800, boundsBtnSongInfo.getMaxY() + 20);
        mask.setSize(800, 500);
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

    content.setUserData("songeditor.panContent");
    content.setPadding(new Insets(20, 20, 20, 20));
    scrollPane.setContent(content);
    root.setId("songeditor");
    root.setUserData("songeditor.panRoot");
    lblHeaderInfo.setMaxHeight(Double.MAX_VALUE);
    lblHeaderInfo.setUserData("songeditor.lblHeaderInfo");


    HBox.setMargin(lblHeaderInfo, new Insets(3, 100, 0, 10));
    Region region = new Region();
    HBox.setHgrow(region, Priority.ALWAYS);
    header.getChildren().add(lblHeaderInfo);
    header.getChildren().add(region);
    header.getChildren().add(tbaActions);
    header.setId("songtitle");
    header.setUserData("songeditor.panHeader");


    root.setCenter(scrollPane);
    root.setTop(header);

    root.setPrefWidth(Double.MAX_VALUE);

    reload();

    PartEditor firstPartEditor = getFirstPartEditor();
    if (firstPartEditor != null) {
      LOGGER.info("Request first line part editor");
      firstPartEditor.getFirstLineEditor().getLinePartEditors().get(0).requestFocus(false);
    }
  }

  public double getCurrentScrollPosition () {
    return currentScrollPosition;
  }

  public VBox getContent () {
    return content;
  }

  public ScrollPane getScrollPane () {
    return scrollPane;
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
    LOGGER.info("reloadDetail()");

    currentScrollPosition = getScrollPane().getVvalue();
    content.getChildren().clear();

    partEditors.clear();

    btnOriginalChord.setDisable(song.getOriginalKey() == null || song.getOriginalKey().trim().isEmpty());
    btnOriginalChord.setText(song.getOriginalKey() != null ? song.getOriginalKey(): "unset");
    btnCurrentChord.setDisable(song.getCurrentKey() == null || song.getCurrentKey().trim().isEmpty());
    btnCurrentChord.setText(song.getCurrentKey() != null ? song.getCurrentKey(): "unset");


    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(song);


    for (int i = 0; i < song.getStructItems().size(); i++) {
      SongStructItem next = song.getStructItems().get(i);
      PartEditor currentPartEditor = new PartEditor(applicationEnvironment, this, next, i);
      partEditors.add(currentPartEditor);
      content.getChildren().add(currentPartEditor.getPanel());
    }

    getScrollPane().setVvalue(currentScrollPosition);


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

  public Configuration getConfiguration() {
    return configuration;
  }

  
}
