package org.adonai.ui.settings;

import org.adonai.AdonaiProperties;
import org.adonai.model.Configuration;
import org.adonai.model.Model;
import org.adonai.model.TenantModel;
import org.adonai.ui.AbstractController;

public class AbstractSettingsController extends AbstractController {

  private Model model;

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public TenantModel getTenantModel () {
    AdonaiProperties adonaiProperties = new AdonaiProperties();
    String currentTenant = adonaiProperties.getCurrentTenant();
    return getModel().getTenantModel(currentTenant);
  }

  public Configuration getConfiguration () {
    TenantModel tenantModel = getTenantModel();
    return tenantModel.get();
  }
}
