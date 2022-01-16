package org.adonai.online;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.adonai.AdonaiProperties;
import org.adonai.fx.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileStore.class);

  private DropboxAdapter dropboxAdapter = new DropboxAdapter();

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  public FileStoreState getRemoteState(final File baseDir) throws IOException {
    FileStoreState remoteFileStoreState = new FileStoreState();
    if (baseDir.exists()) {
      List<Path> collect = Files.walk(baseDir.toPath()).filter(Files::isRegularFile).collect(Collectors.toList());

      //add local data
      for (Path next : collect) {
        File nextFile = next.toFile();
        if (nextFile.getName().startsWith("."))
          continue;

        FileStoreStateItem item = new FileStoreStateItem();
        item.setLocalFile(baseDir, nextFile);
        remoteFileStoreState.addItem(item);
      }
    }

    String token = adonaiProperties.getDropboxAccessToken();
    List<RemoteFile> list = dropboxAdapter.list(baseDir.getName(), token);
    for (RemoteFile nextRemote : list) {
      FileStoreStateItem remoteItem = remoteFileStoreState.findOrCreateItem(nextRemote.getPath());
      remoteItem.setRemotePath(nextRemote.getPath());
      remoteItem.setChangedRemote(nextRemote.getLastModified());
    }

    return remoteFileStoreState;
  }

  public int upload(FileStoreState state, boolean overwrite, Progress progress) throws IOException {

    String token = adonaiProperties.getDropboxAccessToken();

    int numberOfUploadedFiles = 0;

    for (FileStoreStateItem next : state.getItems()) {
      if (next.isLocalNewer() || overwrite) {
        LOGGER.info("Uploading " + next.getLocalFile().getAbsolutePath() + "(" + overwrite + ")");
        next.setRemotePath(next.getLocalPath());
        dropboxAdapter.upload(next.getLocalFile(), next.getRemotePath(), token);
        numberOfUploadedFiles++;
        next.setUploaded(true);
      } else
        next.setUploaded(false);
    }
    LOGGER.info("Uploaded " + numberOfUploadedFiles + " files");
    return numberOfUploadedFiles;
  }

  public int download(final File baseDir, FileStoreState state, Progress progress) {
    String token = adonaiProperties.getDropboxAccessToken();

    int numberOfDownloadedFiles = 0;
    int totalNumberOfFiles = state.getItemsRemoteNewer().size();

    for (FileStoreStateItem next : state.getItems()) {
      if (next.isRemoteNewer()) {
        next.setLocalPath(next.getRemotePath());
        if (next.getLocalFile() == null)
          next.setLocalFile(baseDir.getAbsoluteFile().getParentFile(),
              new File(baseDir.getAbsoluteFile().getParentFile(), next.getRemotePath()));
        LOGGER.info("Downloading " + next.getLocalFile().getAbsolutePath() + "(local " + next
            .getChangedLocally() + ", remote " + next.getChangedRemote());
        if (progress != null)
          progress.show(totalNumberOfFiles, numberOfDownloadedFiles, "Downloading " + (numberOfDownloadedFiles + 1) + " of " + totalNumberOfFiles + ": " + next.getRemotePath() + "...");
        dropboxAdapter.download(next.getLocalFile(), next.getRemotePath(), token);
        next.getLocalFile().setLastModified(next.getChangedRemote());
        numberOfDownloadedFiles++;
        next.setDownloaded(true);
      } else
        next.setDownloaded(false);
    }
    if (progress != null)
      progress.show(totalNumberOfFiles, totalNumberOfFiles, "Downloaded " + totalNumberOfFiles + " files");
    LOGGER.info("Downloaded " + numberOfDownloadedFiles + " files");
    return numberOfDownloadedFiles;

  }

  public static void main(String[] args) throws IOException {

    FileStore remoteFileStore = new FileStore();
    File tenantPath = new File("/Users/OleyMa/.adonai/tenant_mmp");
    FileStoreState remoteState = remoteFileStore.getRemoteState(tenantPath);
    for (FileStoreStateItem item : remoteState.getItems()) {
      System.out.println(
          item.getLocalPath() + "(" + item.getChangedLocally() + "<->" + item.getChangedRemote() + ", " + item
              .isLocalNewer() + "-" + item.isRemoteNewer());
    }
    System.out.println("Found " + remoteState.getItems().size() + " items");

    //int numberOfUploaded = remoteFileStore.upload(remoteState, false);
    //System.out.println ("Number of uploaded: " + numberOfUploaded);

    int numberOfDownloaded = remoteFileStore.download(tenantPath, remoteState, new Progress() {
      @Override public void show(int totalNumer, int workedNumber, String text) {
        System.out.println ("Download " + workedNumber + "/" + totalNumer + ": " + text);

      }
    });
    System.out.println("Number of downloaded: " + numberOfDownloaded);

  }

}
