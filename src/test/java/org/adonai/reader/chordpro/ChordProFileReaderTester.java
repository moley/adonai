package org.adonai.reader.chordpro;

import com.glaforge.i18n.io.CharsetToolkit;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.adonai.model.Song;
import org.apache.commons.io.FileUtils;

public class ChordProFileReaderTester {

  public static void main(String[] args) throws IOException {

    ChordProFileReader chordProFileReader = new ChordProFileReader();
    File folder = new File("/Users/OleyMa/privat/worship/teamwork");
    for (File next: folder.listFiles()) {
      System.out.println (next.getAbsolutePath());
      Charset cs = CharsetToolkit.guessEncoding(next, 4096, StandardCharsets.UTF_8);
      List<String> lines = FileUtils.readLines(next, cs);
      Song song = chordProFileReader.read(lines);

      System.out.println (song.toString());


    }
  }

}
