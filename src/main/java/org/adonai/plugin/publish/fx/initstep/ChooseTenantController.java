package org.adonai.plugin.publish.fx.initstep;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

  @FXML Button btnExit;

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

    btnExit.setOnAction(event -> {System.exit(0);});

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
      chooseTenant();
    });

    lviTenants.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        chooseTenant();
      }
    });
    Platform.runLater(() -> lviTenants.requestFocus());
  }

  public void chooseTenant () {
    String selectedTenant = lviTenants.getSelectionModel().getSelectedItem();
    log.info("Choose tenant button pressed with selected tenant " + selectedTenant);
    getApplicationEnvironment().getModel().setCurrentTenant(selectedTenant);

    getStage().close();
  }

  public void setApplicationEnvironment (ApplicationEnvironment applicationEnvironment) {
    super.setApplicationEnvironment(applicationEnvironment);
    lviTenants.setItems(FXCollections.observableArrayList(getApplicationEnvironment().getAllTenants()));
    lviTenants.getSelectionModel().selectFirst();
    
  }





}
