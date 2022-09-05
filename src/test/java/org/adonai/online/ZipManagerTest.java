package org.adonai.online;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.adonai.ApplicationEnvironment;
import org.adonai.fx.Consts;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZipManagerTest {

  private ApplicationEnvironment applicationEnvironment;

  @Before
  public void before () {
    applicationEnvironment = new ApplicationEnvironment();
  }

  @After
  public void after () {
    applicationEnvironment.dispose();
  }

  private String getInitialContent () {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<configuration>\n</configuration>";
  }

  @Test
  public void writeRead () throws IOException {


    File tenant1ConfigOriginal = new File (Consts.getAdonaiHome(), "tenant_tenant1/config.xml");
    File tenant2ConfigOriginal = new File (Consts.getAdonaiHome(), "tenant_tenant2/config.xml");
    FileUtils.write(tenant1ConfigOriginal, getInitialContent(), Charset.defaultCharset());
    FileUtils.write(tenant2ConfigOriginal, getInitialContent(), Charset.defaultCharset());


    ZipManager zipManager = new ZipManager(applicationEnvironment);
    zipManager.zip();

    zipManager.unzip(".adonaiBackup");
    File tenant1ConfigBackup = new File (Consts.getAdonaiHome().getParentFile(), ".adonaiBackup/tenant_tenant1/config.xml");
    File tenant2ConfigBackup = new File (Consts.getAdonaiHome().getParentFile(), ".adonaiBackup/tenant_tenant2/config.xml");
    Assert.assertEquals(FileUtils.readFileToString(tenant1ConfigOriginal, Charset.defaultCharset()),
        FileUtils.readFileToString(tenant1ConfigBackup, Charset.defaultCharset()));
    Assert.assertEquals(FileUtils.readFileToString(tenant2ConfigOriginal, Charset.defaultCharset()),
        FileUtils.readFileToString(tenant2ConfigBackup, Charset.defaultCharset()));

  }

  @Before
  public void initialize () throws IOException {
    Consts.setAdonaiHome(Files.createTempDir());
    File adonaiHomePath = Consts.getAdonaiHome();
    FileUtils.deleteDirectory(new File (adonaiHomePath.getParentFile(), ".adonaiBackup"));
    FileUtils.deleteQuietly(new File (adonaiHomePath.getParentFile(), ".adonai.zip"));
  }

  @After
  public void clean () throws IOException {
    File adonaiHomePath = Consts.getAdonaiHome();
    FileUtils.deleteDirectory(new File (adonaiHomePath.getParentFile(), ".adonaiBackup"));
    FileUtils.deleteQuietly(new File (adonaiHomePath.getParentFile(), ".adonai.zip"));
    Consts.setAdonaiHome(null);

  }
}
