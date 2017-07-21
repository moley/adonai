package org.adonai;

import org.adonai.ui.ExtensionType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by OleyMa on 21.09.16.
 */
public class ExtensionIndex {

  private Collection<Extension> files = new ArrayList<Extension>();

  public ExtensionIndex (Collection<String> paths) {

    for (String next: paths) {
      readPath(new File (next));
    }

  }

  public void readPath (final File path) {
    if (path == null)
      return;

    for (File next: path.listFiles()) {
      if (next.isDirectory())
        readPath(next);
      else
        files.add(new Extension (next));
    }
  }

  public Collection<Extension> getFiles () {
    return files;
  }

  public Collection<Extension> getFiles (final ExtensionType type, final String searchkey) {
    Collection<Extension> filtered = new ArrayList<Extension>();
    for (Extension next:getFiles()) {
      if (type.isSuffixMatching(next.getFile()) && next.getFile().getName().toUpperCase().contains(searchkey.toUpperCase()))
        filtered.add(next);
    }

    return filtered;

  }
}
