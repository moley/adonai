package org.adonai.services;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.adonai.ui.Consts;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TenantServiceTest {

  private File savedHome;
  private TenantService tenantService = new TenantService();

  @Before public void before() {
    savedHome = Consts.getAdonaiHome();
    File adonaiHome = Files.createTempDir();
    Consts.setAdonaiHome(adonaiHome);
  }

  @After public void after() {
    Consts.setAdonaiHome(savedHome);
  }

  @Test public void createTenantsOnTheFly() throws IOException {

    File adonaiHome = Consts.getAdonaiHome();
    File additionals = new File(adonaiHome, "additionals");
    additionals.mkdirs();
    File audioFile = new File(additionals, "audio/1.mp3");
    audioFile.getParentFile().mkdirs();
    audioFile.createNewFile();
    File export = new File(adonaiHome, "export");
    export.mkdirs();
    File caches = new File(adonaiHome, "caches");
    caches.mkdirs();
    File config = new File(adonaiHome, "config.xml");
    config.createNewFile();

    Collection<String> tenants = tenantService.getTenants();
    Assert.assertEquals("Create default tenant on the fly number of tenantsInvalid  in " + adonaiHome.getAbsolutePath(),
        1, tenants.size());
    Assert.assertEquals("Number of tenant paths invalid in " + adonaiHome.getAbsolutePath(),
        adonaiHome.listFiles().length, 1);
    Assert.assertEquals("Number of tenant paths invalid in " + adonaiHome.getAbsolutePath(),
        new File(adonaiHome, "tenant_default").listFiles().length, 4);

  }

  @Test public void add() {

    tenantService.add("tenant1");
    tenantService.add("tenant2");
    Assert.assertTrue("Created tenant not found", tenantService.getTenants().contains("tenant1"));
    Assert.assertTrue("Created tenant not found", tenantService.getTenants().contains("tenant2"));

  }

  @Test public void remove() {

    tenantService.add("tenant1");
    tenantService.remove("tenant1");
    Assert.assertEquals("Created and removed tenant found" + tenantService.getTenants(), 1, tenantService.getTenants().size());
    Assert.assertTrue("Created tenant not found" + tenantService.getTenants(), tenantService.getTenants().contains("default"));

  }
}
