package org.adonai.uitests;

import java.io.File;
import org.adonai.ui.Consts;

public class TestUtil {

  public static void initialize () {
    System.setProperty("testfx.robot", "glass");
    System.setProperty("testfx.headless", "true");
    System.setProperty("monocle.platform","Headless");
    //System.setProperty("prism.order", "sw");
    //System.setProperty("prism.text", "t2k");
    //System.setProperty("prism.forceGPU", "true");
    System.setProperty("java.awt.headless", "true");
    System.setProperty("headless.geometry", "1280x800-32");

    Consts.setAdonaiHome(TestUtil.getDefaultTestDataPath());

  }

  public static File getDefaultTestDataPath () {
    return new File(".adonai");
  }
}
