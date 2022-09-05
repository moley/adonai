package org.adonai.plugin.publish.fx.initstep;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.AbstractController;
import org.adonai.services.ModelService;

@Slf4j
public class ChooseTenantController extends AbstractController {

  @FXML
  ListView<String> lviTenants;

  @FXML
  TextField txtNew;

  @FXML
  Button btnAddTenant;

  @FXML
  Button btnChooseTenant;

  @FXML
  Button btnRemoveTenant;

  @FXML
  public void initialize () {
    btnAddTenant.setOnAction(event -> {
      log.info("Add tenant button pressed with new tenant " + txtNew.getText());
      ModelService modelService = getApplicationEnvironment().getServices().getModelService();
      modelService.addTenant(getApplicationEnvironment().getModel(), txtNew.getText());
      setApplicationEnvironment(getApplicationEnvironment());
      lviTenants.getSelectionModel().select(txtNew.getText());
      txtNew.clear();
    });

    btnRemoveTenant.setOnAction(event -> {
      String selectedTenant = lviTenants.getSelectionModel().getSelectedItem();
      log.info("Remove tenant button pressed with selected tenant " + selectedTenant);
      ModelService modelService = getApplicationEnvironment().getServices().getModelService();
      modelService.removeTenant(getApplicationEnvironment().getModel(), selectedTenant);
      setApplicationEnvironment(getApplicationEnvironment());
      lviTenants.getSelectionModel().clearSelection();
      txtNew.clear();
    });

    btnChooseTenant.setOnAction(event -> {
      String selectedTenant = lviTenants.getSelectionModel().getSelectedItem();
      log.info("Choose tenant button pressed with selected tenant " + selectedTenant);
      getApplicationEnvironment().getModel().setCurrentTenant(selectedTenant);

      getStage().close();
    });
  }

  public void setApplicationEnvironment (ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);
    this.lviTenants.setItems(FXCollections.observableArrayList(getApplicationEnvironment().getAllTenants()));
    this.lviTenants.getSelectionModel().select(getApplicationEnvironment().getAllTenants().iterator().next());
  }





}
