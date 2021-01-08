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

public class RemoteFileStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoteFileStore.class);

  private DropboxAdapter dropboxAdapter = new DropboxAdapter();

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  public void upload(File file) throws IOException {
    String token = adonaiProperties.getDropboxAccessToken();
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

    }
  }

  private String getRelativeFileName (final File homePath, final File file) {
    String local = file.getAbsolutePath().substring(homePath.getAbsolutePath().length() + 1);
    return local;
  }

  public static void main(String[] args) throws IOException {

    RemoteFileStore remoteFileStore = new RemoteFileStore();
    remoteFileStore.upload (new File ("/Users/OleyMa/.adonai/tenant_mmp"));

  }


}
