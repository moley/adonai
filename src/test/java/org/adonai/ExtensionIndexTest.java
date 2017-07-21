package org.adonai;

import org.junit.Test;
import org.adonai.ui.ExtensionType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by OleyMa on 21.09.16.
 */
public class ExtensionIndexTest {

  @Test
  public void read () {
    Collection<String> paths = new ArrayList<String>();
    paths.add("/Users/OleyMa/Music/iTunes/iTunes Media/Music");
    ExtensionIndex index = new ExtensionIndex(paths);

    for (Extension next: index.getFiles()) {
      System.out.println ("All -" + next.getFile().getAbsolutePath());
    }

    for (Extension next: index.getFiles(ExtensionType.SONG, "Leben")) {
      System.out.println ("Gott -" + next.getFile().getAbsolutePath());
    }

  }
}
