package org.adonai.ui.editor2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.adonai.model.SongStructItem;
import org.adonai.services.SongRepairer;
import org.adonai.ui.AbstractController;

public class SongPartDetailsController extends AbstractController {


  @FXML
  private ComboBox<SongPartType> cboSongPartType;

  @FXML
  private TextField txtQuantity;

  @FXML
  private TextArea txtRemarks;



  private Song currentSong;

  private SongStructItem currenSongStructItem;






  public void init() {

    SongPart referencedPart = getCurrentSong().findSongPart(currenSongStructItem);


    txtQuantity.textProperty().bindBidirectional(currenSongStructItem.quantityProperty());
    txtRemarks.textProperty().bindBidirectional(currenSongStructItem.remarksProperty());


    if (getCurrentSong() == null)
      throw new IllegalStateException("Current song not set before initialization");

    if (getCurrentSongStructItem() == null)
      throw new IllegalStateException("Current part not set before initialization");

    SongPartCellFactory songPartCellFactory = new SongPartCellFactory(getCurrentSong());

    cboSongPartType.setItems(FXCollections.observableArrayList(SongPartType.values()));
    if (referencedPart.getSongPartType() != null)
      cboSongPartType.getSelectionModel().select(referencedPart.getSongPartType());

    cboSongPartType.valueProperty().addListener(new ChangeListener<SongPartType>() {

      @Override
      public void changed(ObservableValue<? extends SongPartType> observable, SongPartType oldValue, SongPartType newValue) {
        if (newValue != null) {
          SongPart songPart = getCurrentSongPart();
          songPart.setSongPartType(newValue);
          System.out.println ("Hello");

          SongRepairer songRepairer = new SongRepairer();
          songRepairer.repairSong(currentSong);

          System.out.println ("Hello");
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

  public SongPart getCurrentSongPart () {
    return getCurrentSong().findSongPart(getCurrentSongStructItem());
  }

  public SongStructItem getCurrentSongStructItem() {
    return currenSongStructItem;
  }

  public void setCurrentSongStructItem(SongStructItem currenSongStructItem) {
    this.currenSongStructItem = currenSongStructItem;
  }

}
