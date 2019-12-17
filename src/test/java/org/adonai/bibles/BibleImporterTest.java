package org.adonai.bibles;

import java.io.File;
import org.junit.Test;

public class BibleImporterTest {

  @Test
  public void importBibles () {
    BibleImporter bibleImporter = new BibleImporter();
    bibleImporter.importBibles(new File("bibles"), new File ("build/biblesImported"));
  }
}
