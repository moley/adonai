package org.adonai.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.adonai.AdonaiProperties;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TenantService.class.getName());

  public Collection<String> getTenants() {
    Collection<String> tenants = new ArrayList<String>();
    File adonaiHome = Consts.getAdonaiHome();
    for (File next : adonaiHome.listFiles()) {
      if (next.isDirectory() && next.getName().startsWith("tenant_"))
        tenants.add(next.getName().substring(7));
    }

    if (tenants.isEmpty()) {
      tenants.add("tenant_default");
      File defaultTenantPath = new File(adonaiHome, "tenant_default");
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

  public void add(final String tenant) {
    File tenantPath = getTenantPath(tenant);
    if (tenantPath.exists())
      throw new IllegalStateException("Tenantpath " + tenantPath.getAbsolutePath() + " already exists");

    tenantPath.mkdirs();
  }

  public void remove(final String tenant) {
    File tenantPath = getTenantPath(tenant);
    FileUtils.deleteQuietly(tenantPath);

  }


}
