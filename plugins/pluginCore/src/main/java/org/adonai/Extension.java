package org.adonai;

import java.io.File;

/**
 * Created by OleyMa on 18.10.16.
 */
public class Extension {

  private File file;

  public Extension (final File file) {
    this.file = file;
  }

  public String toString () {
    return file.getName() + "(" + file.getParentFile().getName() + ")";
  }

  public File getFile ( ){
    return file;
  }
}
