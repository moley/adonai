package org.adonai.fx.editcontent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.adonai.export.ExportToken;
import org.adonai.fx.Consts;
import org.adonai.fx.ContentChangeableController;
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

public class EditContentController extends ContentChangeableController {

  @FXML
  private TextArea txaText;

  @FXML private ListView<SongStructItem> lviStructure;
  @FXML private Button btnAddBefore;
  @FXML private Button btnMoveUp;
  @FXML private Button btnRemove;
  @FXML private Button btnMoveDown;
  @FXML private Button btnAddAfter;

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
    btnAddBefore.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAddBefore.setOnAction(action-> addPartBefore());
    btnMoveUp.setGraphic(Consts.createIcon("fas-angle-up", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveUp.setOnAction(action -> movePartUp());
    btnRemove.setGraphic(Consts.createIcon("fas-trash", Consts.ICON_SIZE_VERY_SMALL));
    btnRemove.setOnAction(action -> removePart());
    btnMoveDown.setGraphic(Consts.createIcon("fas-angle-down", Consts.ICON_SIZE_VERY_SMALL));
    btnMoveDown.setOnAction(action -> movePartDown());
    btnAddAfter.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAddAfter.setOnAction(action -> addPartAfter());
    lviStructure.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SongStructItem>() {
      @Override public void changed(ObservableValue<? extends SongStructItem> observable, SongStructItem oldValue, SongStructItem newValue) {
        serializeCurrentSongPart(oldValue); //serialize old one
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
    loadCurrentSongPart(exportToken.getSongStructItem());
  }

  private void loadCurrentSongPart (final SongStructItem songStructItem) {
    SongPart songPart = findSongPart(songStructItem); //and load new one
    txaText.setText(textRenderer.getRenderedText(songPart));
  }

  private void serializeCurrentSongPart (SongStructItem songStructItem) {
    SongPart songPart = findSongPart(songStructItem);
    List<String> lines = new ArrayList<>(Arrays.asList(txaText.getText().split("\n")));
    lines.add(0, "[" + songPart.getSongPartTypeLabel() + "]");
    TextfileReaderParam textfileReaderParam = new TextfileReaderParam();
    textfileReaderParam.setEmptyLineIsNewPart(false);
    textfileReaderParam.setWithTitle(false);
    TextfileReader textfileReader = new TextfileReader();
    Song song = textfileReader.read(lines, textfileReaderParam);
    SongPart serializedSongPart = song.getFirstPart();
    songPart.setLines(serializedSongPart.getLines());
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

  @Override protected void save() {
    song.setStructItems(songStructItems);
    song.setSongParts(songParts);
  }

  private SongCursor getSongCursor () {
    SongCursor songCursor = new SongCursor();
    songCursor.setCurrentSong(song);
    songCursor.setCurrentSongStructItem(lviStructure.getSelectionModel().getSelectedItem());
    return songCursor;
  }

  private void addPartBefore () {
    SongStructItem songStructItem = addPartService.addPartBefore(getSongCursor(), null, SongPartType.REFRAIN);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);
  }

  private void addPartAfter () {
    SongStructItem songStructItem = addPartService.addPartAfter(getSongCursor(), null, SongPartType.REFRAIN);
    reloadSongStructItems();
    lviStructure.getSelectionModel().select(songStructItem);
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
    if (lviStructure.getSelectionModel().isEmpty())
      lviStructure.getSelectionModel().selectFirst();

  }

}
