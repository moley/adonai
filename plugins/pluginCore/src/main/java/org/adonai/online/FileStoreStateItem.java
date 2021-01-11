package org.adonai.online;

import java.io.File;
import lombok.Data;

@Data
public class FileStoreStateItem {

  private File localFile;

  private String localPath;

  private String remotePath;

  private long changedLocally;

  private long changedRemote;

  private boolean selected;

  public void setLocalFile (final File baseDir, final File file) {
    this.localFile = file;
    this.localPath = file.getAbsolutePath().substring(baseDir.getAbsoluteFile().getParent().length());
  }
}
