package org.adonai.ui.mainpage;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class MainPageTester {
  public static void main(String[] args) throws IOException {
    File localTestData = new File("build/testdata").getAbsoluteFile();
    System.setProperty("adonai.home", localTestData.getAbsolutePath());

    File originTestData = new File (System.getProperty("user.home") + "/.adonai/config.xml");
    FileUtils.copyFile(originTestData, new File (localTestData, ".adonai/config.xml"));

    MainPageApplication.main(args);
  }
}
