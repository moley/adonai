package org.adonai.online;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZipManagerTest {

  @Test
  public void writeRead () throws IOException {
    ZipManager zipManager = new ZipManager();
    zipManager.zip();

    zipManager.unzip(".adonaiBackup");
    File tenant1ConfigOriginal = new File (".adonai/tenant_tenant1/config.xml");
    File tenant2ConfigOriginal = new File (".adonai/tenant_tenant2/config.xml");
    File tenant1ConfigBackup = new File (".adonaiBackup/tenant_tenant1/config.xml");
    File tenant2ConfigBackup = new File (".adonaiBackup/tenant_tenant2/config.xml");
    Assert.assertEquals(FileUtils.readFileToString(tenant1ConfigOriginal, Charset.defaultCharset()),
        FileUtils.readFileToString(tenant1ConfigBackup, Charset.defaultCharset()));
    Assert.assertEquals(FileUtils.readFileToString(tenant2ConfigOriginal, Charset.defaultCharset()),
        FileUtils.readFileToString(tenant2ConfigBackup, Charset.defaultCharset()));

  }

  @Before@After
  public void initialize () throws IOException {
    File adonaiHomePath = Consts.getAdonaiHome();
    FileUtils.deleteDirectory(new File (adonaiHomePath.getParentFile(), ".adonaiBackup"));
    FileUtils.deleteQuietly(new File (adonaiHomePath.getParentFile(), ".adonai.zip"));
  }
}
