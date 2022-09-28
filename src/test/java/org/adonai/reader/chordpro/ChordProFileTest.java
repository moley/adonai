package org.adonai.reader.chordpro;

import com.glaforge.i18n.io.CharsetToolkit;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.adonai.model.Line;
import org.adonai.model.Song;
import org.adonai.model.SongPart;
import org.adonai.model.SongPartType;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class ChordProFileTest {

  private ChordProFileReader chordProFileReader = new ChordProFileReader();

  private List<String> readFile (final File file) {
    try {
      Charset cs = CharsetToolkit.guessEncoding(file, 4096, StandardCharsets.UTF_8);
      return FileUtils.readLines(file, cs);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
  @Test
  public void importCopiedText () throws IOException {
    List<String> content = readFile(new File("src/test/resources/import/chordpro/withoutChords.txt"));
    Song song = chordProFileReader.read(content);
    Assert.assertEquals("BLA", song.getTitle());
    log.info("Song: " + song.getSongParts());
    Assert.assertEquals (SongPartType.VERS, song.getSongParts().get(0).getSongPartType());
    Assert.assertEquals (1, song.getSongParts().get(0).getLines().size());
    Assert.assertEquals ("Blub", song.getSongParts().get(0).getLines().get(0).getText());
    Assert.assertEquals (SongPartType.REFRAIN, song.getSongParts().get(1).getSongPartType());
    Assert.assertEquals (4, song.getSongParts().get(1).getLines().size());
    Assert.assertEquals ("Zeile1", song.getSongParts().get(1).getLines().get(0).getText());
    Assert.assertEquals ("Zeile2", song.getSongParts().get(1).getLines().get(1).getText());
    Assert.assertEquals ("Zeile3", song.getSongParts().get(1).getLines().get(2).getText());
    Assert.assertEquals ("Zeile4", song.getSongParts().get(1).getLines().get(3).getText());
  }

  @Test
  public void importFreeSong ()  {
    List<String> content = readFile(new File("src/test/resources/import/chordpro/macht-hoch-die-tur-die-tor-macht-weit-D.txt"));
    Song song = chordProFileReader.read(content);

    Assert.assertEquals ("MACHT HOCH DIE TUER DIE TOR MACHT WEIT", song.getTitle());
    Assert.assertEquals ("D", song.getCurrentKey());
    Assert.assertEquals ("D", song.getOriginalKey());
    log.info("Song: " + song.getSongParts());
  }

  @Test
  public void importBugs ()  {
    List<String> content = readFile(new File("src/test/resources/import/chordpro/bugs.txt"));
    Song song = chordProFileReader.read(content);

    SongPart firstPart = song.getSongParts().get(0);
    Line firstLine = firstPart.getFirstLine();
    Assert.assertEquals ("Bla (D)Blub (A)Bla (C)", firstLine.toString().trim());
    Assert.assertEquals (SongPartType.VERS, firstPart.getSongPartType());

    SongPart secondPart = song.getSongParts().get(1);
    Line secondLine = secondPart.getFirstLine();
    Assert.assertEquals ("(Hm7)       (G)          (Hm7)       (G)", secondLine.toString().trim());
    Assert.assertEquals (SongPartType.INSTRUMENTAL, secondPart.getSongPartType());

  }

}
