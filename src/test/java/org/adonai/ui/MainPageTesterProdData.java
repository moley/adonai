package org.adonai.ui;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class MainPageTesterProdData {
  public static void main(String[] args) throws IOException {
    File localTestData = new File("build/testdataProd").getAbsoluteFile();
    System.setProperty("adonai.home", localTestData.getAbsolutePath());

    File originTestData = new File (System.getProperty("user.home") + "/.adonai/config.xml");
    FileUtils.copyFile(originTestData, new File (localTestData, ".adonai/config.xml"));


    MainPageApplication.main(args);
  }
}
