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

  private Boolean uploaded;

  private Boolean downloaded;

  public void setChangedRemote (final long changedRemote) {
    if (changedRemote == 0)
      throw new IllegalArgumentException("Changed remote must not be 0");
    this.changedRemote = changedRemote;
  }

  public void setLocalFile (final File baseDir, final File file) {
    this.localFile = file;
    this.localPath = file.getAbsolutePath().substring(baseDir.getAbsoluteFile().getParent().length());
    this.changedLocally = file.lastModified();
  }

  public boolean isLocalNewer () {
    return changedLocally > changedRemote;
  }

  public boolean isRemoteNewer () {
    return changedRemote > changedLocally;
  }


}
