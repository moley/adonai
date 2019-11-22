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

  private PartEditor partEditor;
  @FXML
  private ComboBox<SongPart> cboCopyExisting;

  @FXML
  private ComboBox<SongPartType> cboSongPartType;

  @FXML
  private TextField txtQuantity;

  @FXML
  private TextArea txtRemarks;



  public Song getCurrentSong () {
    if (getPartEditor() == null && getPartEditor().getSongEditor() == null)
      throw new IllegalStateException("Current part editor not set before initialization");

    return getPartEditor().getSongEditor().getSong();
  }

  public SongPart getCurrentPart () {
    if (getPartEditor() == null)
      throw new IllegalStateException("Current part editor not set before initialization");

    return getPartEditor().getPart();
  }

  public void setPartEditor(PartEditor partEditor) {
    this.partEditor = partEditor;




    SongPart songPart = getCurrentPart();
    SongPart referencedPart = songPart.getReferencedSongPart() != null ? getCurrentSong().findSongPartByUUID(songPart.getReferencedSongPart()): null;


    txtQuantity.textProperty().bindBidirectional(songPart.quantityProperty());
    txtRemarks.textProperty().bindBidirectional(songPart.remarksProperty());


    if (getCurrentSong() == null)
      throw new IllegalStateException("Current song not set before initialization");

    if (getCurrentPart() == null)
      throw new IllegalStateException("Current part not set before initialization");

    Collection<SongPart> songpartsWithoutCurrent = songInfoService.getRealSongPartsWithoutCurrent(getCurrentSong(), getCurrentPart());
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

  public PartEditor getPartEditor() {
    return partEditor;
  }
}
