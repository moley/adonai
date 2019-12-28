package org.adonai;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class AdonaiPropertiesTest {

  private File tmpPath = Files.createTempDir();

  private static String savedUserHome = System.getProperty("user.home");

  @AfterClass
  public static void after () {
    System.setProperty("user.home", savedUserHome);
  }

  @Test
  public void read () throws IOException {
    System.setProperty("user.home", tmpPath.getAbsolutePath());
    File propFile = new File (tmpPath, ".adonai/adonai.properties");
    FileUtils.writeLines(propFile, Arrays.asList("hello=world"));
    AdonaiProperties adonaiProperties = new AdonaiProperties();
    Assert.assertEquals ("world", adonaiProperties.getProperty("hello"));

  }
}
