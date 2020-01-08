package org.adonai.uitests.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import org.adonai.ui.Consts;
import org.adonai.ui.UiUtils;
import org.adonai.uitests.MyNodeMatchers;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

public class SettingsMaskPage {

  Scene  scene;
  Stage stage;
  ApplicationTest applicationTest;

  public SettingsMaskPage (final Stage stage, final ApplicationTest applicationTest) throws IOException {
    this.stage = stage;
    this.applicationTest = applicationTest;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/settings.fxml"));
    loader.setResources(ResourceBundle.getBundle("languages.adonai"));
    Parent root = loader.load();
    scene = new Scene(root, Consts.getDefaultWidth(), Consts.getDefaultHeight());
    UiUtils.applyCss(scene);

    stage.setScene(scene);
    stage.show();
  }

  private ListView getLviConfigurationsList () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("settings.lviConfigurationList"));
    return nodeQuery.query();
  }

  public void selectConfigurationType (final int index) {
    applicationTest.clickOn(getLviConfigurationsList());
    getLviConfigurationsList().getSelectionModel().select(index);
  }

  public void expandExportSchema (final String userdata) {
    Accordion accordion = getAccExportSchemas();
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData(userdata));
    TitledPane tpaSelectedExportSchema = nodeQuery.query();
    accordion.setExpandedPane(tpaSelectedExportSchema);
  }

  public Collection<String> getExportSchemas () {
    Collection<String> exportSchemas = new ArrayList<>();
    for (TitledPane titledPane: getAccExportSchemas().getPanes()) {
      exportSchemas.add(titledPane.getUserData().toString());
    }

    return exportSchemas;
  }

  public String expandedExportSchema () {
    Accordion accordion = getAccExportSchemas();
    return (String) accordion.getExpandedPane().getUserData();
  }

  private Button getBtnCloneExportConfiguration () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("settings_export.btnCloneConfiguration"));
    return nodeQuery.query();
  }

  private Button getBtnRemoveExportConfiguration () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("settings_export.btnRemoveConfiguration"));
    return nodeQuery.query();
  }

  public boolean isBtnCloneExportConfigurationDisabled () {
    return getBtnCloneExportConfiguration().isDisabled();
  }

  public void cloneExportConfiguration () {
    applicationTest.clickOn(getBtnCloneExportConfiguration());
  }

  public void removeExportConfiguration () {
    applicationTest.clickOn(getBtnRemoveExportConfiguration());
  }

  public boolean isBtnRemoveExportConfigurationDisabled () {
    return getBtnRemoveExportConfiguration().isDisabled();
  }


  private Accordion getAccExportSchemas () {
    NodeQuery nodeQuery = applicationTest.lookup(MyNodeMatchers.withUserData("settings_export.accExportschemas"));
    return nodeQuery.query();
  }

}
