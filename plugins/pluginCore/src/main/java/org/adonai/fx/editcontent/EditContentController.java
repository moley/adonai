package org.adonai.fx.editcontent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import org.adonai.export.ExportToken;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.editor.TextRenderer;
import org.adonai.fx.renderer.SongStructCellRenderer;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.reader.text.TextfileReader;
import org.adonai.reader.text.TextfileReaderParam;
import org.adonai.services.AddPartService;
import org.adonai.services.MovePartService;
import org.adonai.services.RemovePartService;
import org.adonai.services.SongCursor;
import org.adonai.services.SongRepairer;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditContentController extends AbstractController {

  private static final Logger log = LoggerFactory.getLogger(EditContentController.class);
  @FXML private TextField txtQuantity;
  @FXML
  private TextField txtRemarks;
  @FXML
  private ComboBox<SongPartType> cboType;

  @FXML
  private TextArea txaText;

  @FXML private ListView<SongStructItem> lviStructure;
  @FXML private MenuButton btnAdd;
  @FXML private MenuButton btnCopy;
  @FXML private Button btnMoveUp;
  @FXML private Button btnRemove;
  @FXML private Button btnMoveDown;

  private Song song;

  private AddPartService addPartService = new AddPartService();

  private RemovePartService removePartService = new RemovePartService();

  private MovePartService movePartService = new MovePartService();

  private TextRenderer textRenderer =  new TextRenderer();

  private List<SongStructItem> songStructItems = new ArrayList<SongStructItem>();

  private List<SongPart> songParts = new ArrayList<SongPart>();

  @FXML
  public void initialize () {
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
      @Override public void changed(ObservableValue<? extends SongStructItem> observable, SongStructItem oldValue, SongStructItem newValue) {
        if (oldValue != null)
          serializeCurrentSongPart(oldValue); //serialize old one
        if (newValue != null)
        loadCurrentSongPart(newValue); //and load new one
      }
    });

  }

  /**
   * sets the current export token from outside
   * @param exportToken
   */
  public void setExportToken(ExportToken exportToken) {
    this.song = exportToken.getSong();
    this.songParts = this.song.getSongParts();
    this.songStructItems = this.song.getStructItems();

    reloadSongStructItems();
    lviStructure.getSelectionModel().select(exportToken.getSongStructItem());
    loadCurrentSongPart(exportToken.getSongStructItem());
  }

  private void loadCurrentSongPart (final SongStructItem songStructItem) {
    SongPart songPart = findSongPart(songStructItem); //and load new one
    txaText.setText(textRenderer.getRenderedText(songPart));
    txtRemarks.setText(songStructItem.getRemarks());
    txtQuantity.setText(songStructItem.getQuantity());
    cboType.getSelectionModel().select(songPart.getSongPartType());
  }

  public void serializeCurrentSongPart () {
    serializeCurrentSongPart(lviStructure.getSelectionModel().getSelectedItem());
  }

  private void serializeCurrentSongPart (SongStructItem songStructItem) {

    SongPart songPart = findSongPart(songStructItem);
    log.info("serialize song part " + songStructItem.getPartId() + "-" + songPart.getId());
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

  private SongPart findSongPart (final SongStructItem songStructItem) {
    Collection<String> found = new ArrayList<>();
    for (SongPart next: songParts) {
      if (next.getId().equals(songStructItem.getPartId()))
        return next;
      else
        found.add(next.getId() + "\n");
    }
    throw new IllegalStateException("Part with id " + songStructItem.getPartId() + " not found in current parts (Found ids: " + found + ")");
  }

  private SongCursor getSongCursor () {
    SongCursor songCursor = new SongCursor();
    songCursor.setCurrentSong(song);
    songCursor.setCurrentSongStructItem(lviStructure.getSelectionModel().getSelectedItem());
    return songCursor;
  }

  private void movePartUp () {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    movePartService.movePartUp(songCursor);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);
  }

  private void movePartDown () {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    movePartService.movePartDown(songCursor);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);

  }

  private void removePart () {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = removePartService.removePart(songCursor);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);
  }


  public Song getSong() {
    return song;
  }

  public void reloadSongStructItems() {
    lviStructure.setItems(FXCollections.observableArrayList(songStructItems));

    btnAdd.getItems().clear();
    for (SongPartType nextType: SongPartType.values()) {
      MenuItem menuItem = new MenuItem(nextType.name());
      menuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
          final SongPartType currentSongPartType = nextType;
          addPartService.addPartAfter(getSongCursor(), null, currentSongPartType);
          reloadSongStructItems();
        }
      });
      btnAdd.getItems().add(menuItem);

    }

    btnCopy.getItems().clear();
    HashSet<String> copiedItems = new HashSet<>();
    for (final SongStructItem nextStructItem: songStructItems) {
      String copiedName = nextStructItem.getText();
      if (!copiedItems.contains(copiedName)) {
        MenuItem menuItem = new MenuItem(copiedName);
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent event) {
            final SongStructItem currentSongStructItem = nextStructItem;
            SongPart songPart = song.findSongPart(currentSongStructItem);
            addPartService.addPartAfter(getSongCursor(), songPart, null);
            reloadSongStructItems();
          }
        });
        btnCopy.getItems().add(menuItem);
      }
    }

  }

}
