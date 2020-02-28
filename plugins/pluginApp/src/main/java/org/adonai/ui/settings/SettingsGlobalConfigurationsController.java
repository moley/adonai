package org.adonai.ui.settings;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import org.adonai.model.Model;
import org.adonai.model.SongBook;
import org.adonai.services.RenumberService;
import org.adonai.ui.Consts;

public class SettingsGlobalConfigurationsController extends AbstractSettingsController{

  @FXML
  private Button btnRenumber;

  public void setModel (final Model model)  {

    btnRenumber.setTooltip(new Tooltip("Reindex the id's of all songs (1,2,3,4...)"));
    btnRenumber.setGraphic(Consts.createIcon("fa-sort-numeric-asc", Consts.ICON_SIZE_SMALL));
    btnRenumber.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        SongBook songBook = getConfiguration().getSongBooks().get(0); //TODO
        RenumberService renumberService = new RenumberService();
        renumberService.renumber(songBook);
      }
    });

  }
}
