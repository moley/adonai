package org.adonai.fx.settings;

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
import org.adonai.model.Model;
import org.adonai.services.ModelService;
import org.adonai.fx.Consts;

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

  private ModelService modelService;


  private void refresh () {
    this.lviTenants.setItems(FXCollections.observableArrayList(getModelService().getTenants()));
    this.lviTenants.getSelectionModel().select(adonaiProperties.getCurrentTenant());
  }

  public ModelService getModelService () {
    if (modelService == null)
      modelService = new ModelService(getApplicationEnvironment());

    return modelService;
  }


  public void setModel (final Model model)  {
    super.setModel(model);
    refresh();
    lviTenants.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        lblName.setText(newValue);
        adonaiProperties.setCurrentTenant(newValue);
      }
    });

    btnAddTenant.setGraphic(Consts.createIcon("fas-plus", Consts.ICON_SIZE_VERY_SMALL));
    btnAddTenant.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        String name = txtNew.getText();
        getModelService().addTenant(getModel(), name);
        txtNew.setText("");
        refresh();
        lviTenants.requestFocus();
        lviTenants.getSelectionModel().select(name);
      }
    });
    btnRemoveTenant.setGraphic(Consts.createIcon("fas-minus", Consts.ICON_SIZE_VERY_SMALL));
    btnRemoveTenant.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        String selected = lviTenants.getSelectionModel().getSelectedItem();
        getModelService().removeTenant(getModel(), selected);
        refresh();
        lviTenants.getSelectionModel().selectFirst();
      }
    });

  }


}
