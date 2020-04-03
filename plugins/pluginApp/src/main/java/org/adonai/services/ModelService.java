package org.adonai.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.adonai.ApplicationEnvironment;
import org.adonai.model.Model;
import org.adonai.model.TenantModel;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 03.09.16.
 */
public class ModelService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);

  public static final String DEFAULT_TENANT = "default";

  private final ApplicationEnvironment applicationEnvironment;

  public ModelService (final ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;

  }

  public Model load () {
    Model model = new Model();

    for (String next: getTenants()) {
      TenantModel tenantModel = new TenantModel(applicationEnvironment, next);

      try {
        tenantModel.load();
      } catch (Exception e) {
        throw new RuntimeException("Error loading Tenant model " + tenantModel.getTenant(), e);
      }
      model.getTenantModels().add(tenantModel);
    }

    return model;
  }

  public Collection<String> getTenants() {
    Collection<String> tenants = new ArrayList<String>();
    File adonaiHome = Consts.getAdonaiHome();
    if (! adonaiHome.exists())
      adonaiHome.mkdirs();

    for (File next : adonaiHome.listFiles()) {
      if (next.isDirectory() && next.getName().startsWith("tenant_"))
        tenants.add(next.getName().substring(7));
    }

    if (tenants.isEmpty()) {
      tenants.add(DEFAULT_TENANT);
      File defaultTenantPath = new File(adonaiHome, DEFAULT_TENANT);
      defaultTenantPath.mkdirs();
    }

    return tenants;
  }

  private File getTenantPath (final String tenant) {
    return new File (Consts.getAdonaiHome(), "tenant_" + tenant);
  }

  public void addTenant(final Model model, final String tenant) {
    File tenantPath = getTenantPath(tenant);
    if (tenantPath.exists())
      throw new IllegalStateException("Tenantpath " + tenantPath.getAbsolutePath() + " already exists");

    tenantPath.mkdirs();

    TenantModel tenantModel = new TenantModel(applicationEnvironment, tenant);
    model.getTenantModels().add(tenantModel);
  }

  public void removeTenant(final Model model, final String tenant) {
    TenantModel tenantModel = model.getTenantModel(tenant);
    model.getTenantModels().remove(tenantModel);
    File tenantPath = getTenantPath(tenant);
    FileUtils.deleteQuietly(tenantPath);
  }







}
