package org.adonai.ui.mainpage;

import java.io.File;

public class MainPageTester {
  public static void main(String[] args) {
    System.setProperty("adonai.home", new File("build/testdata").getAbsoluteFile().getAbsolutePath());
    MainPageApplication.main(args);
  }
}
