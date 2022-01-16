package org.adonai.model;

import java.util.ArrayList;
import java.util.List;
import org.adonai.AdonaiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Model {

  private static final Logger log = LoggerFactory.getLogger(Model.class);


  private List<TenantModel> tenantModels = new ArrayList<>();

  public TenantModel getCurrentTenantModel () {
    AdonaiProperties adonaiProperties = new AdonaiProperties();
    return getTenantModel(adonaiProperties.getCurrentTenant());
  }

  public TenantModel getTenantModel (final String tenant) {
    for (TenantModel next: tenantModels) {
      if (next.getTenant().equals(tenant)) {
        log.info("get tanent model " + tenant);
        return next;
      }
    }

    throw new IllegalStateException("TenantModel for tenant '" + tenant + "' not found (Found " + getTenantModelNames() + ")");

  }

  public boolean hasChanged () {
    for (TenantModel next: tenantModels) {
      if (next.hasChanged())
        return true;
    }

    return false;

  }

  public void save () {
    for (TenantModel next : tenantModels) {
      next.save();
    }
  }

  public List<TenantModel> getTenantModels() {
    return tenantModels;
  }

  public List<String> getTenantModelNames() {
    List<String> names = new ArrayList<String>();
    for (TenantModel nextModel: tenantModels) {
      names.add(nextModel.getTenant());
    }
    return names;
  }

  public String toString () {
    String asString = "";
    for (TenantModel next: getTenantModels()) {
      asString += next.toString();
    }

    return asString;

  }
}
