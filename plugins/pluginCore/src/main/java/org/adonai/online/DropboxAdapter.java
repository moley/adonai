package org.adonai.online;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import com.dropbox.core.v2.users.FullAccount;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.adonai.AdonaiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxAdapter implements OnlineAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(DropboxAdapter.class);




  private DbxClientV2 clientV2;

  @Override
  public String upload(File uploadFile, final String path, final String credentials) {

    String remotePath = "/" + path;
    LOGGER.info("Upload " + uploadFile.getAbsolutePath() + " to '" + remotePath + "'");
    DbxClientV2 clientV2 = getClientV2(credentials);

    FileMetadata fileMetadata = null;
    try {
      fileMetadata = ((FileMetadata)clientV2.files().getMetadata(remotePath));
    } catch (DbxException e) {
      LOGGER.debug("Error getting metdata on " + remotePath);
    }

    try {
      //FullAccount fullAccount = clientV2.users().getCurrentAccount();
      //LOGGER.info ("Email: " + fullAccount.getEmail());


      long remoteModifiedTime = fileMetadata != null ? fileMetadata.getClientModified().getTime(): 0;
      long localModifiedTime = uploadFile.lastModified();
      boolean locallyModified = remoteModifiedTime < localModifiedTime;

      LOGGER.info("Check if file " + uploadFile.getAbsolutePath() + " was modified ( remote " + remoteModifiedTime + "- local " + localModifiedTime + "-" + locallyModified);

      if (locallyModified) {
        try (InputStream in = new FileInputStream(uploadFile)) {
          FileMetadata metadata = clientV2.files().uploadBuilder(remotePath).withMode(WriteMode.OVERWRITE).uploadAndFinish(in);

          List<SharedLinkMetadata> links = clientV2.sharing().listSharedLinks().getLinks();
          SharedLinkMetadata sharedLinkMetadata = getOrCreateLinkMetadata(metadata.getPathLower(), links);

          String url = sharedLinkMetadata != null ? sharedLinkMetadata.getUrl() : null;
          LOGGER.info("Uploaded " + url);

          return null;

        } catch (IOException e) {
          throw new IllegalStateException("Error uploading " + uploadFile.getName() + " to " + path, e);
        }
      }
      else
        LOGGER.info("File " + uploadFile.getAbsolutePath() + " was already uploaded");

    } catch (DbxException e) {
      throw new IllegalStateException(e);
    }

    return null; //TODO remove return type

  }

  private SharedLinkMetadata getOrCreateLinkMetadata (final String path, List<SharedLinkMetadata> paths)
      throws DbxException {
    for (SharedLinkMetadata next: paths) {
      if (next.getPathLower().equalsIgnoreCase(path))
        return next;
    }

    return clientV2.sharing().createSharedLinkWithSettings(path,SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC).build());

  }

  @Override
  public File download(final File toPath, final String credentials) {

    LOGGER.info("Download configFile to " + toPath.getAbsolutePath());


    File downloadFile = new File (toPath, "adonai.zip");
    downloadFile.getParentFile().mkdirs();

    try {
      FileOutputStream fos = new FileOutputStream(downloadFile);
      DbxClientV2 clientV2 = getClientV2(credentials);
      clientV2.files().downloadBuilder("/adonai.zip").download(fos);
    } catch (IOException | DbxException e) {
      throw new IllegalStateException("Error downloading adonai.zip", e);
    }

    return downloadFile;
  }

  private DbxClientV2 getClientV2(final String accessToken) {
    if (clientV2 == null) {
      DbxRequestConfig config = DbxRequestConfig.newBuilder("adonai/1.0").build();
      clientV2 = new DbxClientV2(config, accessToken);
    }
    return clientV2;
  }

  public void setClientV2(DbxClientV2 clientV2) {
    this.clientV2 = clientV2;
  }

  public List<RemoteFile> list(String name, String token)  {
    List<RemoteFile> remoteFileList = new ArrayList<>();
    DbxClientV2 clientV2 = getClientV2(token);
    list(remoteFileList, clientV2, name);
    return remoteFileList;
  }

  private void list (List<RemoteFile> remoteFiles, DbxClientV2 clientV2, final String name) {
    ListFolderResult listFolderResult = null;
    try {
      listFolderResult = clientV2.files().listFolder("/" + name);
    } catch (DbxException e) {
      throw new IllegalStateException("Error getting list of " + name);
    }
    for (Metadata next: listFolderResult.getEntries()) {
      if (next instanceof FileMetadata) {
        FileMetadata fileMetadata = (FileMetadata) next;
        RemoteFile remoteFile = new RemoteFile();
        remoteFile.setPath(fileMetadata.getPathDisplay());
        remoteFile.setLastModified(fileMetadata.getServerModified().getTime());
        remoteFiles.add(remoteFile);
      }
      else
        list(remoteFiles, clientV2, next.getPathDisplay());
    }

  }
}
