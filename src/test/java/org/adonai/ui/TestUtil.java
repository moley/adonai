package org.adonai.ui;

import java.io.File;

public class TestUtil {

  public static void initialize () {
    //System.setProperty("testfx.robot", "glass");
    //System.setProperty("testfx.headless", "true");
    //System.setProperty("prism.order", "sw");
    //System.setProperty("prism.text", "t2k");
    //System.setProperty("java.awt.headless", "true");
  }

  public static File getDefaultTestDataPath () {
    return new File("build/testdata");
  }
}