package org.adonai.ui.settings;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.adonai.AdonaiProperties;
import org.adonai.services.TenantService;
import org.adonai.ui.Consts;

public class SettingsTenantsController extends AbstractSettingsController {

  @FXML
  ListView<String> lviTenants;

  @FXML
  TextField txtNew;

  @FXML Label lblName;

  @FXML
  Button btnAddTenant;

  @FXML
  Button btnRemoveTenant;

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  private TenantService tenantService = new TenantService();

  private void refresh () {
    this.lviTenants.setItems(FXCollections.observableArrayList(tenantService.getTenants()));
    this.lviTenants.getSelectionModel().select(adonaiProperties.getCurrentTenant());
  }


  @FXML
  public void initialize() throws IOException {
    refresh();
    lviTenants.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        lblName.setText(newValue);
        adonaiProperties.setCurrentTenant(newValue);
        adonaiProperties.save();
      }
    });

    btnAddTenant.setGraphic(Consts.createIcon("fa-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAddTenant.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String name = txtNew.getText();
        tenantService.add(name);
        txtNew.setText("");
        refresh();
        lviTenants.requestFocus();
        lviTenants.getSelectionModel().select(name);
      }
    });
    btnRemoveTenant.setGraphic(Consts.createIcon("fa-minus", Consts.ICON_SIZE_VERY_SMALL));
    btnRemoveTenant.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        String selected = lviTenants.getSelectionModel().getSelectedItem();
        tenantService.remove(selected);
        refresh();
        lviTenants.getSelectionModel().selectFirst();
      }
    });

  }


}
