package org.adonai.ui.settings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.adonai.PlaylistExport;
import org.adonai.StringUtils;
import org.adonai.export.AbstractDocumentBuilder;
import org.adonai.export.ExportConfiguration;
import org.adonai.export.ExportConfigurationMerger;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.ui.Consts;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsExportController extends AbstractSettingsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SettingsExportController.class);


  @FXML
  private TextField txtExportpath;


  @FXML
  private Accordion accExportschemas;

  @FXML
  private Button btnCloneConfiguration;

  @FXML
  private Button btnRemoveConfiguration;

  @FXML
  ResourceBundle resources;


  private ConfigurationService configurationService = new ConfigurationService();

  HashMap<TitledPane, ExportConfiguration> configurationPerPane = new HashMap<TitledPane, ExportConfiguration>();

  @FXML
  public void initialize() throws IOException {

    txtExportpath.textProperty().bindBidirectional(getConfiguration().exportPathProperty());

    reloadConfigurations();
    btnRemoveConfiguration.setDisable(true);
    btnCloneConfiguration.setDisable(true);

    Configuration configuration = configurationService.get();

    accExportschemas.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
      @Override
      public void changed(ObservableValue<? extends TitledPane> observable, TitledPane oldValue, TitledPane newValue) {

        if (newValue == null) {
          btnRemoveConfiguration.setDisable(true);
          btnCloneConfiguration.setDisable(true);
        }

        if (accExportschemas.getExpandedPane() != null) {
          ExportConfiguration currentExportConfiguration = configurationPerPane.get(accExportschemas.getExpandedPane());
          btnRemoveConfiguration.setDisable(currentExportConfiguration.isDefaultConfiguration());
          btnCloneConfiguration.setDisable(false);
        }
      }
    });


    btnCloneConfiguration.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ExportConfiguration currentExportConfiguration = configurationPerPane.get(accExportschemas.getExpandedPane());
        LOGGER.info("Clone current configuration " + currentExportConfiguration.getName());

        LOGGER.info("Size before: " + configuration.getExportConfigurations().size());
        ExportConfigurationMerger merger = new ExportConfigurationMerger();
        ExportConfiguration merged = merger.getMergedExportConfiguration(currentExportConfiguration, currentExportConfiguration);
        merged.setName(currentExportConfiguration.getName() + "(2)");
        merged.setId(null);
        merged.getId(); //to initialize
        configuration.getExportConfigurations().add(merged);

        LOGGER.info("Size after: " + configuration.getExportConfigurations().size());
        try {
          reloadConfigurations();
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }


      }
    });

    btnRemoveConfiguration.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ExportConfiguration currentExportConfiguration = configurationPerPane.get(accExportschemas.getExpandedPane());
        LOGGER.info("Remove current configuration " + currentExportConfiguration.getName());
        configuration.getExportConfigurations().remove(currentExportConfiguration);

        try {
          reloadConfigurations();
        } catch (IOException e) {
          throw new IllegalStateException(e);
        }
      }
    });




  }

  private void reloadConfigurations () throws IOException {
    accExportschemas.getPanes().clear();
    configurationPerPane.clear();

    for (ExportConfiguration exportConfiguration: configurationService.get().getExportConfigurations()) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/exportconfiguration.fxml"));
      loader.setResources(resources);
      Parent root = loader.load();
      SettingsExportConfigurationController settingsExportConfigurationController = loader.getController();
      settingsExportConfigurationController.setExportConfiguration(exportConfiguration);
      TitledPane titledPane = new TitledPane(exportConfiguration.getName(), root);
      titledPane.setUserData("tpa" + StringUtils.removeWhitespaces(exportConfiguration.getName()));
      LOGGER.info("ExportConfiguration " + titledPane.getUserData() + " created");

      String icon = resources.getString(exportConfiguration.getDocumentBuilderClass() + "_icon_black");
      if (LOGGER.isDebugEnabled())
        LOGGER.debug("Using Icon " + icon + " for " + exportConfiguration.getName());

      titledPane.setGraphic(Consts.createImageView(icon, Consts.ICON_SIZE_SMALL));
      accExportschemas.getPanes().add(titledPane);
      configurationPerPane.put(titledPane, exportConfiguration);
    }
  }
}
