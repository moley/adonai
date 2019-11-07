package org.adonai.online;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class DropboxAdapterTest {

  @Test
  public void upload () throws IOException {

    File tmpDir = Files.createTempDir();
    tmpDir.mkdirs();
    DropboxAdapter dropboxAdapter = new DropboxAdapter();
    dropboxAdapter.upload(new File(System.getProperty("user.home") + "/.adonai/config.xml"));

    File downloaded = dropboxAdapter.download(tmpDir);
    System.out.println (downloaded.getAbsolutePath());
    System.out.println (FileUtils.readFileToString(downloaded, Charset.defaultCharset()));


  }
}
