package org.adonai.ui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.adonai.export.ExportConfiguration;
import org.adonai.ui.Consts;

import java.util.ResourceBundle;


public class SettingsExportConfigurationController {

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
  CheckBox chkShowPart;

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
      imvDefaultIcon.setImage(Consts.createImage("default"));
    }

    String icon = resources.getString(exportConfiguration.getDocumentBuilderClass() + "_icon");
    System.out.println ("Icon " + icon + " for " + exportConfiguration.getName());
    imvType.setImage(Consts.createImageLarge(icon));


    chkOpenPreview.selectedProperty().bindBidirectional(exportConfiguration.openPreviewProperty());
    chkShowChords.selectedProperty().bindBidirectional(exportConfiguration.withChordsProperty());
    chkShowTitle.selectedProperty().bindBidirectional(exportConfiguration.withTitleProperty());
    chkShowPart.selectedProperty().bindBidirectional(exportConfiguration.withPartTypeProperty());


    /**exportConfiguration.getChordTextDistance();
    exportConfiguration.getInterLineDistance();
    exportConfiguration.getInterSongDistance();
    exportConfiguration.getLeftBorder();
    exportConfiguration.getMinimalChordDistance();
    exportConfiguration.getNewPageStrategy();
    exportConfiguration.is**/



  }
}
