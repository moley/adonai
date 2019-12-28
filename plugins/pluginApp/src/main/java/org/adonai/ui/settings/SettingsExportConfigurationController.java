package org.adonai.ui.settings;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.adonai.export.ExportConfiguration;
import org.adonai.ui.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsExportConfigurationController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SettingsExportConfigurationController.class);


  @FXML
  TextField txtName;

  @FXML
  ImageView imvDefaultIcon;

  @FXML
  ImageView imvType;

  @FXML
  CheckBox chkOpenPreview;

  @FXML
  CheckBox chkShowTitle;

  @FXML
  CheckBox chkShowChords;


  @FXML
  ResourceBundle resources;

  private ExportConfiguration exportConfiguration;





  public ExportConfiguration getExportConfiguration() {
    return exportConfiguration;
  }

  public void setExportConfiguration(ExportConfiguration exportConfiguration) {
    if (this.exportConfiguration != null)
      throw new IllegalStateException("ExportConfiguration already set");
    this.exportConfiguration = exportConfiguration;
    txtName.textProperty().bindBidirectional(exportConfiguration.nameProperty());

    if (exportConfiguration.isDefaultConfiguration()) {
      txtName.setEditable(false);
      imvDefaultIcon.setImage(Consts.createImage("default", Consts.ICON_SIZE_SMALL));
    }

    String icon = resources.getString(exportConfiguration.getDocumentBuilderClass() + "_icon");
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("Icon " + icon + " for " + exportConfiguration.getName());
    imvType.setImage(Consts.createImage(icon, Consts.ICON_SIZE_LARGE));


    chkOpenPreview.selectedProperty().bindBidirectional(exportConfiguration.openPreviewProperty());
    chkShowChords.selectedProperty().bindBidirectional(exportConfiguration.withChordsProperty());
    chkShowTitle.selectedProperty().bindBidirectional(exportConfiguration.withTitleProperty());

    /**exportConfiguration.getChordTextDistance();
    exportConfiguration.getInterLineDistance();
    exportConfiguration.getInterSongDistance();
    exportConfiguration.getLeftBorder();
    exportConfiguration.getMinimalChordDistance();
    exportConfiguration.getNewPageStrategy();
    exportConfiguration.is**/



  }
}
