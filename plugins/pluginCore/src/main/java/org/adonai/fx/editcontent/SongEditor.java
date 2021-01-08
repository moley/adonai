package org.adonai.fx.editcontent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import org.adonai.Key;
import org.adonai.actions.ConnectSongWithMp3Action;
import org.adonai.additionals.AdditionalsImporter;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.ExtensionSelectorController;
import org.adonai.fx.renderer.UserCellRenderer;
import org.adonai.fx.viewer.TextRenderer;
import org.adonai.fx.renderer.SongStructCellRenderer;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.model.User;
import org.adonai.reader.text.TextfileReader;
import org.adonai.reader.text.TextfileReaderParam;
import org.adonai.services.AddPartService;
import org.adonai.services.MovePartService;
import org.adonai.services.RemovePartService;
import org.adonai.services.SongCursor;
import org.adonai.services.SongRepairer;
import org.adonai.services.SongTransposeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongEditor extends AbstractController {

  private static final Logger log = LoggerFactory.getLogger(SongEditor.class);
  @FXML private  TabPane tabPane;
  @FXML private  Tab tabSongProperties;
  @FXML private  Tab tabTextEditor;
  @FXML private  Tab tabStructureEditor;

  //Structure Editor
  @FXML private TextField txtQuantity;
  @FXML private TextField txtRemarks;
  @FXML private ComboBox<SongPartType> cboType;


  @FXML private ListView<SongStructItem> lviStructure;
  @FXML private MenuButton btnAdd;
  @FXML private MenuButton btnCopy;
  @FXML private Button btnMoveUp;
  @FXML private Button btnRemove;
  @FXML private Button btnMoveDown;

  @FXML private Label lblTitleSongProperties;
  @FXML private Label lblTitleStructureEditor;

  @FXML private TextArea txaText;

  //Text Editor
  @FXML private TextArea txaAsText;

  //Song Properties
  @FXML private Label lblCurrentMp3;

  @FXML private Button btnAssignMp3;

  @FXML private ComboBox<Key> cboCurrentKey;

  @FXML private ComboBox<Key> cboOriginalKey;

  @FXML private ComboBox<User> cboLeadVoice;

  @FXML private TextField txtPreset;

  @FXML private TextField txtTitle;

  @FXML private Spinner<Integer> spiSpeed;

  @FXML private Button btnRecalculateOriginKey;

  @FXML private Button btnRecalculateCurrentKey;

  private Song song;

  private AddPartService addPartService = new AddPartService();

  private RemovePartService removePartService = new RemovePartService();

  private MovePartService movePartService = new MovePartService();

  private TextRenderer textRenderer = new TextRenderer();

  @FXML public void initialize() {
    lviStructure.setCellFactory(cellfactory -> new SongStructCellRenderer());
    btnMoveUp.setGraphic(Consts.createIcon("fas-angle-up", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveUp.setOnAction(action -> movePartUp());
    btnMoveUp.setTooltip(new Tooltip("Move selected part upwards"));
    btnRemove.setGraphic(Consts.createIcon("fas-trash", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setOnAction(action -> removePart());
    btnRemove.setTooltip(new Tooltip("Remove selected part"));
    btnMoveDown.setGraphic(Consts.createIcon("fas-angle-down", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveDown.setOnAction(action -> movePartDown());
    btnMoveDown.setTooltip(new Tooltip("Move selected part downwards"));
    btnAdd.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAdd.setTooltip(new Tooltip("Add new part after selected part"));
    btnCopy.setGraphic(Consts.createIcon("fas-copy", Consts.ICON_SIZE_VERY_SMALL));
    btnCopy.setTooltip(new Tooltip("Add copy of an already existing part after selected part"));
    cboType.setItems(FXCollections.observableArrayList(SongPartType.values()));
    lviStructure.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SongStructItem>() {
      @Override public void changed(ObservableValue<? extends SongStructItem> observable, SongStructItem oldValue,
          SongStructItem newValue) {
        if (oldValue != null)
          serializeCurrentSongPart(oldValue); //serialize old one
        if (newValue != null)
          loadCurrentSongPart(newValue); //and load new one
      }
    });

    txaText.setStyle("-fx-font-family: monospaced;");

    cboLeadVoice.setCellFactory(cellfactory -> new UserCellRenderer());
    spiSpeed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 300));
    spiSpeed.setEditable(true);

    btnRecalculateCurrentKey.setGraphic(Consts.createIcon("fas-calculator", Consts.ICON_SIZE_VERY_SMALL));
    btnRecalculateCurrentKey.setTooltip(new Tooltip("Calculate current key with chords of origin key"));
    btnRecalculateOriginKey.setGraphic(Consts.createIcon("fas-calculator", Consts.ICON_SIZE_VERY_SMALL));
    btnRecalculateOriginKey.setTooltip(new Tooltip("Calculate origin key with chords of current key"));

    cboCurrentKey.setItems(FXCollections.observableArrayList(Key.values()));

    cboOriginalKey.setItems(FXCollections.observableArrayList(Key.values()));


    tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
      @Override public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
        save(oldValue);
        load(newValue);
      }
    });

    tabPane.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent event) {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        save(selectedTab);
      }
    });

    btnRecalculateCurrentKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        song.setCurrentKey(cboCurrentKey.getSelectionModel().getSelectedItem().name());
        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.recalculateCurrent(song);

      }
    });
    btnRecalculateOriginKey.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {

        song.setOriginalKey(cboOriginalKey.getSelectionModel().getSelectedItem().name());
        SongTransposeService songTransposeService = new SongTransposeService();
        songTransposeService.recalculateOrigin(song);
      }
    });

    btnAssignMp3.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        ConnectSongWithMp3Action connectSongWithMp3Action = new ConnectSongWithMp3Action();
        connectSongWithMp3Action.connect(getApplicationEnvironment(), getConfiguration(), song, new EventHandler<WindowEvent>() {
          @Override public void handle(WindowEvent event) {
            log.info("handle onHiding event");

            ExtensionSelectorController extensionSelectorController = connectSongWithMp3Action
                .getExtensionSelectorController();
            File selectedExtension = extensionSelectorController.getSelectedExtension();

            if (selectedExtension != null) {
              String songExtension = selectedExtension.getAbsolutePath();
              Additional additional = new Additional();
              additional.setAdditionalType(AdditionalType.AUDIO);
              additional.setLink(songExtension);
              AdditionalsImporter additionalsImporter = new AdditionalsImporter();
              additionalsImporter.refreshCache(song, additional, true);
              song.setAdditional(additional);
              lblCurrentMp3.setText(getMp3Label(song));

              log.info("connect song " + song + " with songfile " + selectedExtension);
            } else
              log.info("no extension selected");
          }
        });

      }
    });

  }

  public void load (Tab selectedTab) {
    if (selectedTab.equals(tabStructureEditor)) {
      loadStructureEditor();
    }
    else if (selectedTab.equals(tabSongProperties)) {
      loadSongProperties();
    }
    else if (selectedTab.equals(tabTextEditor)) {
      loadTextEditor();
    }
  }

  public void save (Tab selectedTab) {
    if (selectedTab.equals(tabStructureEditor)) {
      saveStructureEditor();
    }
    else if (selectedTab.equals(tabSongProperties)) {
      saveSongProperties();
    }
    else if (selectedTab.equals(tabTextEditor)) {
      saveTextEditor();
    }

  }

  public void loadStructureEditor () {
    log.info("load structureeditor");
    this.lblTitleStructureEditor.setText(song.getTitle());
    reloadSongStructItems();
    loadCurrentSongPart(song.getStructItems().get(0));

  }

  public void saveStructureEditor () {
    log.info("save structureeditor");
  }

  public void loadTextEditor () {
    log.info("load texteditor");
    txaAsText.setText(song.toString() + "HHH");
  }

  public void saveTextEditor () {
    log.info("save texteditor");
    //TODO
  }

  public void loadSongProperties () {
    log.info("load song properties");
    this.lblTitleSongProperties.setText(song.getTitle());
    cboLeadVoice.setItems(FXCollections.observableArrayList(getConfiguration().getUsers()));
    lblCurrentMp3.setText(getMp3Label(song));

    txtPreset.setText(song.getPreset());
    txtTitle.setText(song.getTitle());
    if (song.getSpeed() != null)
      spiSpeed.getValueFactory().setValue(song.getSpeed());
    else
      spiSpeed.getValueFactory().setValue(0);

    //set lead voice
    if (song.getLeadVoice() != null) {
      cboLeadVoice.getSelectionModel().select(song.getLeadVoice());
    } else
      cboLeadVoice.getSelectionModel().clearSelection();

    //set current key
    if (song.getCurrentKey() != null) {
      cboCurrentKey.getSelectionModel().select(Key.fromString(song.getCurrentKey()));
    } else
      cboCurrentKey.getSelectionModel().clearSelection();

    //set original key
    if (song.getOriginalKey() != null) {
      cboOriginalKey.getSelectionModel().select(Key.fromString(song.getOriginalKey()));
    } else
      cboOriginalKey.getSelectionModel().clearSelection();
  }

  public void saveSongProperties () {
    log.info("save song properties");
    song.setPreset(txtPreset.getText());
    song.setTitle(txtTitle.getText());
    song.setSpeed(spiSpeed.getValue());
    song.setLeadVoice(cboLeadVoice.getSelectionModel().getSelectedItem());
    song.setOriginalKey(cboOriginalKey.getSelectionModel().getSelectedItem() != null ?
        cboOriginalKey.getSelectionModel().getSelectedItem().toString() :
        null);
    song.setCurrentKey(cboCurrentKey.getSelectionModel().getSelectedItem() != null ?
        cboCurrentKey.getSelectionModel().getSelectedItem().toString() :
        null);
    song.setSpeed(spiSpeed.getValue());
  }

  private Configuration getConfiguration () {
    return getApplicationEnvironment().getCurrentConfiguration();
  }

  /**
   * sets the current song
   */
  public void setSong(Song song) {
    this.song = song;
    lviStructure.getSelectionModel().clearSelection();
    loadStructureEditor();
    lviStructure.getSelectionModel().selectFirst();
  }

  private String getMp3Label(final Song song) {
    Additional additional = song.getAdditional(AdditionalType.AUDIO);
    return additional != null ?
        additional.getLink()
            .substring((additional.getLink().lastIndexOf("/") >= 0 ? additional.getLink().lastIndexOf("/") + 1: 0)) :
        "n.a.";
  }

  private void loadCurrentSongPart(final SongStructItem songStructItem) {

    SongPart songPart = findSongPart(songStructItem); //and load new one
    log.info("load song part " + songStructItem.getPartId() + "-" + songPart);
    txaText.setText(textRenderer.getRenderedText(songPart));
    txtRemarks.setText(songStructItem.getRemarks());
    txtQuantity.setText(songStructItem.getQuantity());
    cboType.getSelectionModel().select(songPart.getSongPartType());
  }

  private void serializeCurrentSongPart(SongStructItem songStructItem) {

    SongPart songPart = findSongPart(songStructItem);
    log.info("serialize song part " + songStructItem.getPartId() + "#" + songPart);
    List<String> lines = new ArrayList<>(Arrays.asList(txaText.getText().split("\n")));
    lines.add(0, "[" + songPart.getSongPartTypeLabel() + "]");
    TextfileReaderParam textfileReaderParam = new TextfileReaderParam();
    textfileReaderParam.setEmptyLineIsNewPart(false);
    textfileReaderParam.setWithTitle(false);
    TextfileReader textfileReader = new TextfileReader();
    Song serializedSong = textfileReader.read(lines, textfileReaderParam);
    serializedSong.setOriginalKey(song.getOriginalKey());
    serializedSong.setCurrentKey(song.getCurrentKey());

    songStructItem.setRemarks(txtRemarks.getText());
    songStructItem.setQuantity(txtQuantity.getText());
    songPart.setSongPartType(cboType.getSelectionModel().getSelectedItem());

    SongRepairer songRepairer = new SongRepairer();
    songRepairer.repairSong(serializedSong);
    SongPart serializedSongPart = serializedSong.getFirstPart();

    songPart.setLines(serializedSongPart.getLines());

    songRepairer.repairSong(song);
  }

  private SongPart findSongPart(final SongStructItem songStructItem) {
    Collection<String> found = new ArrayList<>();
    for (SongPart next : song.getSongParts()) {
      if (next.getId().equals(songStructItem.getPartId()))
        return next;
      else
        found.add(next.getId() + "\n");
    }
    throw new IllegalStateException(
        "Part with id " + songStructItem.getPartId() + " not found in current parts (Found ids: " + found + ")");
  }

  private SongCursor getSongCursor() {
    SongCursor songCursor = new SongCursor();
    songCursor.setCurrentSong(song);
    songCursor.setCurrentSongStructItem(lviStructure.getSelectionModel().getSelectedItem());
    return songCursor;
  }

  private void movePartUp() {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    movePartService.movePartUp(songCursor);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);
  }

  private void movePartDown() {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    movePartService.movePartDown(songCursor);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);

  }

  private void removePart() {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = removePartService.removePart(songCursor);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);
  }

  public Song getSong() {
    return song;
  }

  public void reloadSongStructItems() {
    lviStructure.setItems(FXCollections.observableArrayList(song.getStructItems()));

    btnAdd.getItems().clear();
    for (SongPartType nextType : SongPartType.values()) {
      MenuItem menuItem = new MenuItem(nextType.name());
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
          final SongPartType songPartType = nextType;
          addPartService.addPartAfter(getSongCursor(), null, songPartType);
          reloadSongStructItems();
        }
      });
      btnAdd.getItems().add(menuItem);

    }

    btnCopy.getItems().clear();
    HashSet<String> copiedItems = new HashSet<>();
    for (final SongStructItem nextStructItem : song.getStructItems()) {
      String copiedName = nextStructItem.getText();
      if (!copiedItems.contains(copiedName)) {
        MenuItem menuItem = new MenuItem(copiedName);
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent event) {
            final SongStructItem songStructItem = nextStructItem;
            SongPart songPart = song.findSongPart(songStructItem);
            addPartService.addPartAfter(getSongCursor(), songPart, null);
            reloadSongStructItems();
          }
        });
        btnCopy.getItems().add(menuItem);
      }
    }

  }

}
