package org.adonai.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by OleyMa on 21.09.16.
 */
public enum ExtensionType {

  SONG (".mp3"),
  BACKGROUND (".png");

  private Collection<String> suffixes = new ArrayList<String>();

  ExtensionType (final String... suffixes) {
    for (String next: suffixes)
      this.suffixes.add(next);
  }

  public Collection<String> getSuffixes () {
    return suffixes;
  }

  public boolean isSuffixMatching (final File file) {
    for (String next: suffixes) {
      if (file.getName().endsWith(next))
        return true;
    }

    return false;
  }


}
