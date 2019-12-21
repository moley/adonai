package org.adonai.online;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
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
import java.util.List;
import org.adonai.AdonaiProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropboxAdapter implements OnlineAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(DropboxAdapter.class);


  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  public final static String PROPERTY_DROPBOX_ACCESSTOKEN = "adonai.dropbox.accesstoken";


  private DbxClientV2 clientV2;

  @Override
  public String upload(File uploadFile, final String path) {
    LOGGER.info("Upload " + uploadFile.getAbsolutePath() + " to '" + path + "'");
    DbxClientV2 clientV2 = getClientV2();
    try {
      FullAccount fullAccount = clientV2.users().getCurrentAccount();
      LOGGER.info ("Email: " + fullAccount.getEmail());

      try (InputStream in = new FileInputStream(uploadFile)) {
        FileMetadata metadata = clientV2.files().uploadBuilder("/" + path + uploadFile.getName()).withMode(WriteMode.OVERWRITE).uploadAndFinish(in);
        LOGGER.info ("Metadata ID " + metadata.getId() + "-" + metadata.getPathDisplay());

        List<SharedLinkMetadata> links = clientV2.sharing().listSharedLinks().getLinks();
        SharedLinkMetadata sharedLinkMetadata = getOrCreateLinkMetadata(metadata.getPathLower(), links);

        String url = sharedLinkMetadata != null ? sharedLinkMetadata.getUrl(): null;
        LOGGER.info("URL: " + url);

        return url;

      } catch (IOException e) {
        throw new IllegalStateException(e);
      }

    } catch (DbxException e) {
      throw new IllegalStateException(e);
    }

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
  public File download(final File toPath) {
    LOGGER.info("Download configFile to " + toPath.getAbsolutePath());


    File downloadFile = new File (toPath, "config.xml");
    downloadFile.getParentFile().mkdirs();

    try {
      FileOutputStream fos = new FileOutputStream(downloadFile);
      DbxClientV2 clientV2 = getClientV2();
      clientV2.files().downloadBuilder("/config.xml").download(fos);
    } catch (IOException | DbxException e) {
      throw new IllegalStateException(e);
    }

    return downloadFile;
  }

  public DbxClientV2 getClientV2() {
    if (clientV2 == null) {
      DbxRequestConfig config = DbxRequestConfig.newBuilder("adonai/1.0").build();
      String accessToken = adonaiProperties.getProperty(PROPERTY_DROPBOX_ACCESSTOKEN);
      clientV2 = new DbxClientV2(config, accessToken);
    }
    return clientV2;
  }

  public void setClientV2(DbxClientV2 clientV2) {
    this.clientV2 = clientV2;
  }

  public AdonaiProperties getAdonaiProperties() {
    return adonaiProperties;
  }



}
