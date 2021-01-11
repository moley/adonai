package org.adonai.online;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.adonai.AdonaiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileStore.class);

  private DropboxAdapter dropboxAdapter = new DropboxAdapter();

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  public FileStoreState getRemoteState (final File baseDir) throws IOException {
    FileStoreState remoteFileStoreState = new FileStoreState();
    List<Path> collect = Files.walk(baseDir.toPath()).filter(Files::isRegularFile).collect(Collectors.toList());

    //add local data
    for (Path next: collect) {
      File nextFile = next.toFile();
      if (nextFile.getName().startsWith("."))
        continue;

      FileStoreStateItem item = new FileStoreStateItem();
      item.setLocalFile(baseDir, nextFile);
      remoteFileStoreState.addItem(item);
    }

    String token = adonaiProperties.getDropboxAccessToken();
    List<RemoteFile> list = dropboxAdapter.list(baseDir.getName(), token);
    for (RemoteFile nextRemote: list) {
      FileStoreStateItem remoteItem = remoteFileStoreState.findOrCreateItem(nextRemote.getPath());
      remoteItem.setRemotePath(nextRemote.getPath());
      remoteItem.setChangedRemote(nextRemote.getLastModified());
    }

    return remoteFileStoreState;
  }

  public void upload(FileStoreState state) throws IOException {
    /**String token = adonaiProperties.getDropboxAccessToken();
    LOGGER.info("Path " + file.getName());
    LOGGER.info("Token " + token);
    List<Path> collect = Files.walk(file.toPath()).filter(Files::isRegularFile).collect(Collectors.toList());
    for (Path next: collect) {
      File nextFile = next.toFile();
      if (nextFile.getName().startsWith("."))
        continue;

      String relativeName = getRelativeFileName(file.getParentFile(), nextFile);
      LOGGER.info("Synchronize " + next.toString() + " to " + relativeName);
      dropboxAdapter.upload(nextFile, relativeName, token);

    }**/
  }

  public void download (FileStoreState state) {

  }

  private String getRelativeFileName (final File homePath, final File file) {
    String local = file.getAbsolutePath().substring(homePath.getAbsolutePath().length() + 1);
    return local;
  }

  public static void main(String[] args) throws IOException {

    FileStore remoteFileStore = new FileStore();
    FileStoreState remoteState = remoteFileStore.getRemoteState(new File("/Users/OleyMa/.adonai/tenant_mmp"));
    for (FileStoreStateItem item: remoteState.getItems()) {
      System.out.println (item.getLocalPath() + item.getChangedLocally() + "<->" + item.getRemotePath() + "-" + item.getChangedRemote());
    }
    System.out.println ("Found " + remoteState.getItems().size() + " items");

  }


}
