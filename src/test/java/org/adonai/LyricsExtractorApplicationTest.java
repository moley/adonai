package org.adonai;

import org.adonai.ui.LyricsExtractorApplication;

import java.io.File;

public class LyricsExtractorApplicationTest extends LyricsExtractorApplication {

  public static void main(String[] args) {
    System.setProperty("config", new File("src/test/resources/uitests").getAbsolutePath());
    launch(args);
  }



}
