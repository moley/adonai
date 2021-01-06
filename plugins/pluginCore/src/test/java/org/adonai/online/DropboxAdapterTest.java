package org.adonai.online;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.DownloadBuilder;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadBuilder;
import com.dropbox.core.v2.sharing.DbxUserSharingRequests;
import com.dropbox.core.v2.sharing.ListSharedLinksResult;
import com.dropbox.core.v2.users.DbxUserUsersRequests;
import com.dropbox.core.v2.users.FullAccount;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DropboxAdapterTest {

  @Test public void download() throws IOException, DbxException {

    DbxClientV2 mockedClient = Mockito.mock(DbxClientV2.class);
    DbxUserFilesRequests mockedUserFileRequests = Mockito.mock(DbxUserFilesRequests.class);
    DownloadBuilder mockedDownloadBuilder = Mockito.mock(DownloadBuilder.class);
    Mockito.when(mockedUserFileRequests.downloadBuilder(Mockito.anyString())).thenReturn(mockedDownloadBuilder);
    Mockito.when(mockedClient.files()).thenReturn(mockedUserFileRequests);

    File tmpPath = Files.createTempDir();
    File configFile = new File(tmpPath, "config.xml");
    configFile.getParentFile().mkdirs();
    configFile.createNewFile();
    DropboxAdapter dropboxAdapter = new DropboxAdapter();
    dropboxAdapter.setClientV2(mockedClient);
    dropboxAdapter.download(tmpPath, "helloworld");

    Mockito.verify(mockedDownloadBuilder, Mockito.atLeast(1)).download(Mockito.any());
  }

  @Test
  public void upload() throws IOException, DbxException {
    DbxClientV2 mockedClient = Mockito.mock(DbxClientV2.class);
    FileMetadata mockedFileMetaData = Mockito.mock(FileMetadata.class);
    DbxUserFilesRequests mockedUserFileRequests = Mockito.mock(DbxUserFilesRequests.class);
    DbxUserUsersRequests mockedUserUsersRequest = Mockito.mock(DbxUserUsersRequests.class);
    FullAccount mockedFullAcount = Mockito.mock(FullAccount.class);
    Mockito.when(mockedUserUsersRequest.getCurrentAccount()).thenReturn(mockedFullAcount);
    UploadBuilder mockedUploadBuilder = Mockito.mock(UploadBuilder.class);
    DbxUserSharingRequests mockedDbxUserSharingRequests = Mockito.mock(DbxUserSharingRequests.class);
    ListSharedLinksResult mockedListSharedLinksResult = Mockito.mock(ListSharedLinksResult.class);
    Mockito.when(mockedListSharedLinksResult.getLinks()).thenReturn(new ArrayList<>());
    Mockito.when(mockedDbxUserSharingRequests.listSharedLinks()).thenReturn(mockedListSharedLinksResult);
    Mockito.when(mockedUploadBuilder.withMode(Mockito.any())).thenReturn(mockedUploadBuilder);
    Mockito.when(mockedUploadBuilder.uploadAndFinish(Mockito.any())).thenReturn(mockedFileMetaData);


    Mockito.when(mockedUserFileRequests.uploadBuilder(Mockito.anyString())).thenReturn(mockedUploadBuilder);
    Mockito.when(mockedClient.files()).thenReturn(mockedUserFileRequests);
    Mockito.when(mockedClient.users()).thenReturn(mockedUserUsersRequest);
    Mockito.when(mockedClient.sharing()).thenReturn(mockedDbxUserSharingRequests);

    File tmpPath = Files.createTempDir();
    File configFile = new File(tmpPath, "config.xml");
    configFile.getParentFile().mkdirs();
    configFile.createNewFile();
    DropboxAdapter dropboxAdapter = new DropboxAdapter();
    dropboxAdapter.setClientV2(mockedClient);
    Assert.assertNull (dropboxAdapter.upload(configFile, "", "helloworld"));

  }

}
