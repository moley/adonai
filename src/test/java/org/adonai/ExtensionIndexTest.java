package org.adonai;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.adonai.fx.ExtensionType;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtensionIndexTest {

  private static File tmpPath = new File ("build/tmp/ExtensionIndexTest");

  @BeforeClass
  public static void before () throws IOException {
    FileUtils.deleteQuietly(tmpPath);
    tmpPath.mkdirs();

    File mp3_1 = new File (tmpPath, "bla.mp3");
    mp3_1.createNewFile();
    File mp3_2 = new File (tmpPath, "bla2.mp3");
    mp3_2.createNewFile();

  }

  @Test
  public void getExtensions () {
    ExtensionIndex extensionIndex = new ExtensionIndex(Arrays.asList(tmpPath.getAbsolutePath()));
    Assert.assertEquals (2, extensionIndex.getFiles(ExtensionType.SONG, "bla").size());

    Assert.assertEquals (1, extensionIndex.getFiles(ExtensionType.SONG, "bla2").size());
    Assert.assertEquals (0, extensionIndex.getFiles(ExtensionType.BACKGROUND, "bla").size());

    Extension extension = extensionIndex.getFiles(ExtensionType.SONG, "bla2").iterator().next();
    Assert.assertEquals ("bla2.mp3", extension.getFile().getName());

  }
}
