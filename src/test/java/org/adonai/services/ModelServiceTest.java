package org.adonai.services;

import com.google.common.io.Files;
import java.io.File;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.Consts;
import org.adonai.model.Model;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ModelServiceTest {

  private File savedHome;
  private ModelService ModelService;
  private ApplicationEnvironment applicationEnvironment;

  @Before public void before() {
    savedHome = Consts.getAdonaiHome();

    applicationEnvironment = new ApplicationEnvironment();
    this.ModelService = new ModelService(applicationEnvironment);
    File adonaiHome = Files.createTempDir();
    Consts.setAdonaiHome(adonaiHome);
  }

  @After public void after() {
    Consts.setAdonaiHome(savedHome);
    applicationEnvironment.dispose();
  }

  @Test public void add() {

    Model model = new Model();

    ModelService.addTenant(model, "tenant1");
    ModelService.addTenant(model, "tenant2");
    Assert.assertTrue("Created tenant not found", ModelService.getTenants().contains("tenant1"));
    Assert.assertTrue("Created tenant not found", ModelService.getTenants().contains("tenant2"));

  }

  @Test public void remove() {

    Model model = new Model();

    ModelService.addTenant(model, "tenant1");
    ModelService.removeTenant(model, "tenant1");
    Assert.assertEquals("Created and removed tenant found" + ModelService.getTenants(), 1, ModelService.getTenants().size());
    Assert.assertTrue("Created tenant not found" + ModelService.getTenants(), ModelService.getTenants().contains("default"));

  }
}
