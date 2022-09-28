package org.adonai.fx.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.adonai.fx.Consts;
import org.adonai.model.Model;
import org.adonai.model.SongBook;
import org.adonai.services.RenumberService;

public class SettingsGlobalConfigurationsController extends AbstractSettingsController{

  @FXML
  private Button btnRenumber;

  @FXML
  private TextField txtMp3Folder;

  public void setModel (final Model model)  {
    super.setModel(model);

    btnRenumber.setTooltip(new Tooltip("Reindex the id's of all songs (1,2,3,4...)"));
    btnRenumber.setGraphic(Consts.createIcon("fas-sort-numeric-up", Consts.ICON_SIZE_SMALL));
    btnRenumber.setOnAction(event -> {
      SongBook songBook = getConfiguration().getSongBooks().get(0); //TODO
      RenumberService renumberService = new RenumberService();
      renumberService.renumber(songBook);
    });

    txtMp3Folder.textProperty().bindBidirectional(getConfiguration().mp3ExtensionPathProperty());

  }
}
