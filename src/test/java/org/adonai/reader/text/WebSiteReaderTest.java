package org.adonai.reader.text;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.adonai.model.Song;
import org.adonai.reader.SongConsistencyChecker;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WebSiteReaderTest {

  private WebSiteReader webSiteReader = new WebSiteReader();

  @Test
  public void readExample() throws IOException {
    //From http://www.worshiptogether.com/songs/let-there-be-light-hillsong-worship
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/Let there be light (worshiptogether.com).txt"), "UTF-8");
    Song song = webSiteReader.read(content);
    System.out.println("Song: " + song.toString());


  }

  @Test
  public void readExample2() throws IOException {
    //From http://www.worshiptogether.com/songs/what-a-beautiful-name-hillsong-worship
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/text/What a beautiful name (worshiptogether.com).txt"), "UTF-8");
    Song song = webSiteReader.read(content);
    System.out.println("Song: " + song.toString());

    SongConsistencyChecker songConsistencyChecker = new SongConsistencyChecker();
    songConsistencyChecker.check(song);


  }


}
