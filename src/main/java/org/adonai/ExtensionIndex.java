package org.adonai;

import org.adonai.fx.ExtensionSelectorController;
import org.adonai.fx.ExtensionType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 21.09.16.
 */
public class ExtensionIndex {

  private Collection<Extension> files = new ArrayList<Extension>();

  private Collection<String> paths;

  private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionIndex.class);


  public ExtensionIndex (Collection<String> paths) {
    LOGGER.info("Create extension index with paths " + paths);
    this.paths = paths;
  }

  public void readPath (final File path) {
    LOGGER.info("read path " + path.getAbsolutePath());
    if (path.exists()) {
      LOGGER.error("Path " + path.getAbsolutePath() + " does not exist");
      return;
    }

    for (File next: path.listFiles()) {
      if (next.isDirectory())
        readPath(next);
      else
        files.add(new Extension (next));
    }
  }

  public Collection<Extension> getFiles () {
    files.clear();
    for (String next: paths) {
      readPath(new File (next));
    }
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
