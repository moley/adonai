package org.adonai.fx.settings;

import org.adonai.fx.AbstractController;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.model.TenantModel;

public class AbstractSettingsController extends AbstractController {

  private Model model;

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public TenantModel getCurrentTenantModel() {
    return getModel().getCurrentTenantModel();
  }

  public Configuration getConfiguration () {
    TenantModel tenantModel = getCurrentTenantModel();
    return tenantModel.get();
  }

  public boolean isVisible () {
    return true;
  }
}
