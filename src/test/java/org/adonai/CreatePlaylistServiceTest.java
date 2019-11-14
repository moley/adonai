package org.adonai;

import java.io.File;
import java.io.IOException;
import org.adonai.model.Configuration;
import org.adonai.model.ConfigurationService;
import org.junit.Test;

public class CreatePlaylistServiceTest {

  @Test
  public void create () throws IOException {

    ConfigurationService configurationService = new ConfigurationService();
    Configuration configuration = configurationService.get();

    File file = new File (System.getProperty("user.home") + "/Downloads", "myPlaylist.m3u");
    file.getParentFile().mkdirs();
    CreatePlaylistService createPlaylistService = new CreatePlaylistService();

    createPlaylistService.create(file, configuration.getSongBooks().get(0).getSongs());

    System.out.println ("Playlist: " + file.getAbsolutePath());
  }
}
