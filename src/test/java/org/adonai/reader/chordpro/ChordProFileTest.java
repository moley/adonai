package org.adonai.reader.chordpro;

import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class ChordProFileTest {

  private ChordProFileReader chordProFileReader = new ChordProFileReader();

  @Test
  public void importFreeSong () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/chordpro/macht-hoch-die-tur-die-tor-macht-weit-D.txt"), "UTF-8");
    Song song = chordProFileReader.read(content);

    Assert.assertEquals ("MACHT HOCH DIE TUER DIE TOR MACHT WEIT", song.getTitle());
    Assert.assertEquals ("D", song.getCurrentKey());
    Assert.assertEquals ("D", song.getOriginalKey());
    log.info("Song: " + song.getSongParts());
  }

  @Test
  public void importBugs () throws IOException {
    List<String> content = FileUtils.readLines(new File("src/test/resources/import/chordpro/bugs.txt"), "UTF-8");
    Song song = chordProFileReader.read(content);

    SongPart firstPart = song.getFirstPart();
    Line firstLine = firstPart.getFirstLine();
    Assert.assertEquals ("Bla (D)Blub (A)Bla (C)", firstLine.toString().trim());

  }

}
