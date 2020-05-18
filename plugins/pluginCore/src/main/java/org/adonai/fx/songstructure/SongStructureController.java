package org.adonai.fx.songstructure;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.adonai.fx.AbstractController;
import org.adonai.fx.Consts;
import org.adonai.fx.renderer.SongStructCellRenderer;
import org.adonai.model.Song;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.services.AddPartService;
import org.adonai.services.MovePartService;
import org.adonai.services.RemovePartService;
import org.adonai.services.SongCursor;

public class SongStructureController extends AbstractController {
  @FXML private  ListView<SongStructItem> lviStructure;
  @FXML private Button btnAddBefore;
  @FXML private Button btnMoveUp;
  @FXML private Button btnRemove;
  @FXML private Button btnMoveDown;
  @FXML private Button btnAddAfter;

  private Song song;

  private AddPartService addPartService = new AddPartService();

  private RemovePartService removePartService = new RemovePartService();

  private MovePartService movePartService = new MovePartService();


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
  }

  private SongCursor getSongCursor () {
    SongCursor songCursor = new SongCursor();
    songCursor.setCurrentSong(song);
    songCursor.setCurrentSongStructItem(lviStructure.getSelectionModel().getSelectedItem());
    return songCursor;
  }

  private void addPartBefore () {
    SongStructItem songStructItem = addPartService.addPartBefore(getSongCursor(), null, SongPartType.REFRAIN);
    loadData();
    lviStructure.getSelectionModel().select(songStructItem);
  }

  private void addPartAfter () {
    SongStructItem songStructItem = addPartService.addPartAfter(getSongCursor(), null, SongPartType.REFRAIN);
    loadData();
    lviStructure.getSelectionModel().select(songStructItem);
  }

  private void movePartUp () {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    movePartService.movePartUp(songCursor);
    loadData();
    lviStructure.getSelectionModel().select(songStructItem);
  }

  private void movePartDown () {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = songCursor.getCurrentSongStructItem();
    movePartService.movePartDown(songCursor);
    loadData();
    lviStructure.getSelectionModel().select(songStructItem);

  }

  private void removePart () {
    SongCursor songCursor = getSongCursor();
    SongStructItem songStructItem = removePartService.removePart(songCursor);
    loadData();
    lviStructure.getSelectionModel().select(songStructItem);
  }


  @FXML
  public void loadData () {
    lviStructure.setItems(FXCollections.observableArrayList(song.getStructItems()));
    if (lviStructure.getSelectionModel().isEmpty())
      lviStructure.getSelectionModel().selectFirst();
  }

  public Song getSong() {
    return song;
  }

  public void setSong(Song song) {
    this.song = song;
  }
}
