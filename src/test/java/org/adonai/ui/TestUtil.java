package org.adonai.ui;

import java.io.File;

public class TestUtil {

  public static void initialize () {
    System.setProperty("testfx.robot", "glass");
    System.setProperty("testfx.headless", "true");
    System.setProperty("monocle.platform","Headless");
    System.setProperty("prism.order", "sw");
    System.setProperty("prism.text", "t2k");
    System.setProperty("java.awt.headless", "true");
    System.setProperty("headless.geometry", "1600x1200-32");
  }

  public static File getDefaultTestDataPath () {
    return new File("build/testdata");
  }
}
