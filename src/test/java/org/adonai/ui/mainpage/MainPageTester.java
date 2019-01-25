package org.adonai.ui.mainpage;

import org.adonai.testdata.TestDataCreator;

import java.io.File;
import java.io.IOException;

public class MainPageTester {
  public static void main(String[] args) throws IOException {
    System.setProperty("adonai.home", new File("build/testdata").getAbsoluteFile().getAbsolutePath());
    new TestDataCreator().createTestData(true);
    MainPageApplication.main(args);
  }
}
