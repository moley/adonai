package org.adonai.ui.editor2;

import java.util.Collection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.adonai.model.Line;
import org.adonai.model.LinePart;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.services.SongInfoService;

public class SongPartDetailsController {


  SongInfoService songInfoService = new SongInfoService();

  @FXML
  private ComboBox<SongPart> cboCopyExisting;

  @FXML
  private ComboBox<SongPartType> cboSongPartType;

  @FXML
  private TextField txtQuantity;

  @FXML
  private TextArea txtRemarks;



  private Song currentSong;

  private SongPart currentSongPart;






  public void init() {



    SongPart songPart = getCurrentSongPart();
    SongPart referencedPart = songPart.getReferencedSongPart() != null ? getCurrentSong().findSongPartByUUID(songPart.getReferencedSongPart()): null;


    txtQuantity.textProperty().bindBidirectional(songPart.quantityProperty());
    txtRemarks.textProperty().bindBidirectional(songPart.remarksProperty());


    if (getCurrentSong() == null)
      throw new IllegalStateException("Current song not set before initialization");

    if (getCurrentSongPart() == null)
      throw new IllegalStateException("Current part not set before initialization");

    Collection<SongPart> songpartsWithoutCurrent = songInfoService.getRealSongPartsWithoutCurrent(getCurrentSong(), getCurrentSongPart());
    cboCopyExisting.setItems(FXCollections.observableArrayList(songpartsWithoutCurrent));
    SongPartCellFactory songPartCellFactory = new SongPartCellFactory(getCurrentSong());
    cboCopyExisting.setCellFactory(songPartCellFactory);
    cboCopyExisting.setButtonCell((ListCell) songPartCellFactory.call(null));
    if (referencedPart != null)
      cboCopyExisting.getSelectionModel().select(referencedPart);

    cboSongPartType.setItems(FXCollections.observableArrayList(SongPartType.values()));
    if (songPart.getSongPartType() != null)
      cboSongPartType.getSelectionModel().select(songPart.getSongPartType());


    cboCopyExisting.valueProperty().addListener(new ChangeListener<SongPart>() {
      @Override
      public void changed(ObservableValue<? extends SongPart> observable, SongPart oldValue, SongPart newValue) {
        if (newValue != null) {
          songPart.setReferencedSongPart(newValue.getId());
          songPart.setSongPartType(null);
          songPart.getLines().clear();


          cboSongPartType.getSelectionModel().clearSelection();
          //getPartEditor().getSongEditor().reloadStructure();
        }
      }
    });


    cboSongPartType.valueProperty().addListener(new ChangeListener<SongPartType>() {

      @Override
      public void changed(ObservableValue<? extends SongPartType> observable, SongPartType oldValue, SongPartType newValue) {
        if (newValue != null) {
          songPart.setSongPartType(newValue);
          songPart.setReferencedSongPart(null);
          Line newLine = new Line();
          newLine.getLineParts().add(new LinePart(" "));
          songPart.getLines().add(newLine);

          cboCopyExisting.getSelectionModel().clearSelection();
          //getPartEditor().getSongEditor().reloadStructure();

        }
      }
    });

  }

  public Song getCurrentSong() {
    return currentSong;
  }

  public void setCurrentSong(Song currentSong) {
    this.currentSong = currentSong;
  }

  public SongPart getCurrentSongPart() {
    return currentSongPart;
  }

  public void setCurrentSongPart(SongPart currentSongPart) {
    this.currentSongPart = currentSongPart;
  }

}
