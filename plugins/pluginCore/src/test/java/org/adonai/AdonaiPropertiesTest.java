package org.adonai;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdonaiPropertiesTest {

  private static String savedUserHome = System.getProperty("user.home");

  @BeforeClass
  public static void before () {
    Consts.setAdonaiHome(Files.createTempDir());
  }

  @AfterClass
  public static void after () {
    System.setProperty("user.home", savedUserHome);
  }

  @Test
  public void read () throws IOException {
    File propFile = new File (Consts.getAdonaiHome(), "adonai.properties");
    FileUtils.writeLines(propFile, Arrays.asList("hello=world"));
    AdonaiProperties adonaiProperties = new AdonaiProperties();
    Assert.assertEquals ("world", adonaiProperties.getProperty("hello"));

  }
}
