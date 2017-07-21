package org.adonai;

import org.adonai.ui.AdonaiApplication;

import java.io.File;

public class AdonaiApplicationTest extends AdonaiApplication {

  public static void main(String[] args) {
    System.setProperty("config", new File("src/test/resources/uitests").getAbsolutePath());
    launch(args);
  }



}
