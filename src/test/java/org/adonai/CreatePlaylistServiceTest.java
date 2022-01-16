package org.adonai;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.adonai.model.Song;
import org.adonai.model.SongBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class CreatePlaylistServiceTest {

  @Test
  public void create () throws IOException {



    File tmpPath = Files.createTempDir();
    File mp3File1 = new File (tmpPath, "song1.mp3");
    File tmpFile = new File (tmpPath, "myPlaylist.m3u");

    Song song1 = SongBuilder.instance().disableRepairer().withId("1").withTitle("song1").withMp3(mp3File1).get();
    Song song2 = SongBuilder.instance().disableRepairer().withId("2").withTitle("song2").withMp3(mp3File1).get();
    Song song3 = SongBuilder.instance().disableRepairer().withId("2").withTitle("song3").get();

    List<Song> songs = Arrays.asList(song1, song2, song3);
    tmpFile.getParentFile().mkdirs();
    CreatePlaylistService createPlaylistService = new CreatePlaylistService();

    createPlaylistService.create(tmpFile, songs);

    List<String> content = FileUtils.readLines(tmpFile, Charset.defaultCharset());
    Assert.assertEquals ("Invalid content " + content, 5, content.size());
    Assert.assertEquals ("Invalid header " + content, "#EXTM3U", content.get(0));

    Assert.assertEquals ("Invalid song1 extinf " + content, "#EXTINF:100,song1", content.get(1));
    Assert.assertEquals ("Invalid song1 link " + content, mp3File1.getAbsolutePath(), content.get(2));

    Assert.assertEquals ("Invalid song2 extinf " + content, "#EXTINF:100,song2", content.get(3));
    Assert.assertEquals ("Invalid song2 link " + content, mp3File1.getAbsolutePath(), content.get(4));
  }
}
