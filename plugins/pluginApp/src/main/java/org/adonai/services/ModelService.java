package org.adonai.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

  public Model load () {
    Model model = new Model();

    for (String next: getTenants()) {
      TenantModel tenantModel = new TenantModel(next);

      tenantModel.load();
      model.getTenantModels().add(tenantModel);
    }

    return model;
  }

  public Collection<String> getTenants() {
    Collection<String> tenants = new ArrayList<String>();
    File adonaiHome = Consts.getAdonaiHome();
    for (File next : adonaiHome.listFiles()) {
      if (next.isDirectory() && next.getName().startsWith("tenant_"))
        tenants.add(next.getName().substring(7));
    }

    if (tenants.isEmpty()) {
      tenants.add(DEFAULT_TENANT);
      File defaultTenantPath = new File(adonaiHome, DEFAULT_TENANT);
      defaultTenantPath.mkdirs();
      for (File next : adonaiHome.listFiles()) {
        if (!next.getName().startsWith("tenant_")) {
          try {
            if (next.isDirectory())
              FileUtils.moveDirectory(next, new File (defaultTenantPath, next.getName()));
            else
              FileUtils.moveFile(next, new File(defaultTenantPath, next.getName()));

          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }
      }

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

    TenantModel tenantModel = new TenantModel(tenant);
    model.getTenantModels().add(tenantModel);
  }

  public void removeTenant(final Model model, final String tenant) {
    TenantModel tenantModel = model.getTenantModel(tenant);
    model.getTenantModels().remove(tenantModel);
    File tenantPath = getTenantPath(tenant);
    FileUtils.deleteQuietly(tenantPath);
  }







}
