package org.adonai;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Configuration;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.junit.Assert;
import org.junit.Test;

public class PlaylistExportTest {


  @Test(expected = IllegalStateException.class)
  public void exportNonExisting () {
    File playlistPath = new File ("build/tmp/PlaylistExportTest_playlistpath");

    Configuration configuration = new Configuration();
    SongBook songBook = new SongBook();
    Song song1 = new Song();
    song1.setTitle("Title1");
    song1.setId(Integer.valueOf(1));
    Additional additional1 = new Additional();
    additional1.setAdditionalType(AdditionalType.AUDIO);
    File mp3_1 = new File ("src/test/resources/invalid.mp3").getAbsoluteFile();
    additional1.setCacheLink(mp3_1.getAbsolutePath());
    song1.getAdditionals().add(additional1);
    songBook.getSongs().addAll(Arrays.asList(song1));
    configuration.setSongBooks(Arrays.asList(songBook));
    PlaylistExport playlistExport = new PlaylistExport();
    playlistExport.export(configuration, playlistPath );
  }

  @Test
  public void export () throws IOException {
    File playlistPath = new File ("build/tmp/PlaylistExportTest_playlistpath");

    Configuration configuration = new Configuration();
    SongBook songBook = new SongBook();
    Song song1 = new Song();
    song1.setTitle("Title1");
    song1.setId(Integer.valueOf(1));
    Additional additional1 = new Additional();
    additional1.setAdditionalType(AdditionalType.AUDIO);
    File mp3_1 = new File ("src/test/resources/file_example_MP3_700KB.mp3").getAbsoluteFile();
    additional1.setCacheLink(mp3_1.getAbsolutePath());
    song1.getAdditionals().add(additional1);
    Song song2 = new Song();
    song2.setTitle("Title2");
    song2.setId(Integer.valueOf(2));
    Additional additional2 = new Additional();
    additional2.setAdditionalType(AdditionalType.AUDIO);
    File mp3_2 = new File ("src/test/resources/file_example_MP3_700KB.mp3").getAbsoluteFile();
    additional2.setCacheLink(mp3_2.getAbsolutePath());
    song2.getAdditionals().add(additional2);

    Song songWithout = new Song();

    songBook.getSongs().addAll(Arrays.asList(song1, song2, songWithout));
    configuration.setSongBooks(Arrays.asList(songBook));

    PlaylistExport playlistExport = new PlaylistExport();
    playlistExport.export(configuration, playlistPath );
    Assert.assertTrue ("Title1 does not exist in " + playlistPath.getAbsolutePath(), new File (playlistPath, "001-Title1.mp3").exists());
    Assert.assertTrue ("Title2 does not exist in " + playlistPath.getAbsolutePath(), new File (playlistPath, "002-Title2.mp3").exists());


  }
}
