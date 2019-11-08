package org.adonai.online;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.adonai.AdonaiProperties;

public class DropboxAdapter implements OnlineAdapter {

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  @Override
  public void upload(File configFile) {
    DbxClientV2 clientV2 = getAccount();
    try {
      FullAccount fullAccount = clientV2.users().getCurrentAccount();

      try (InputStream in = new FileInputStream(configFile)) {
        FileMetadata metadata = clientV2.files().uploadBuilder("/" + configFile.getName()).withMode(WriteMode.OVERWRITE).uploadAndFinish(in);
        System.out.println (metadata.getId());
      } catch (FileNotFoundException e) {
        throw new IllegalStateException(e);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      }
      System.out.println (fullAccount.getEmail());
    } catch (DbxException e) {
      throw new IllegalStateException(e);
    }

  }

  @Override
  public File download(final File toPath) {

    File downloadFile = new File (toPath, "config.xml");
    downloadFile.getParentFile().mkdirs();

    try {
      FileOutputStream fos = new FileOutputStream(downloadFile);
      DbxClientV2 clientV2 = getAccount();
      clientV2.files().downloadBuilder("/config.xml").download(fos);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException(e);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    } catch (DbxException e) {
      throw new IllegalStateException(e);
    }

    return downloadFile;
  }

  private DbxClientV2 getAccount () {
    DbxRequestConfig config = DbxRequestConfig.newBuilder("adonai/1.0").build();
    String accessToken = adonaiProperties.getProperty("adonai.dropbox.accesstoken");
    DbxClientV2 client = new DbxClientV2(config, accessToken);
    return client;
  }
}
