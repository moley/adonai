package org.adonai.uitests;

import java.io.File;
import java.io.IOException;
import org.adonai.testdata.TestDataCreator;

public class MainPageTesterTestData {
  public static void main(String[] args) throws IOException {
    File localTestData = new File("build/testdataTest").getAbsoluteFile();
    System.setProperty("adonai.home", localTestData.getAbsolutePath());

    TestDataCreator testDataCreator = new TestDataCreator();
    testDataCreator.createTestData(localTestData, false);

    MainPageApplication.main(args);
  }
}
