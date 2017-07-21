package org.adonai.reader.word;

import org.junit.Test;
import org.adonai.model.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by OleyMa on 17.01.17.
 */
public class WordReaderIntegrationTest {

  @Test
  public void importRealTexts () throws IOException {
    File testWorshipmappe = new File("src/test/resources/worshipmappe.docx");
    WordReader wordReader = new WordReader();
    Collection<Song> songs = wordReader.read(new FileInputStream(testWorshipmappe));
    for (Song next: songs) {
      System.out.println ("Song " + next.getId() + "-" + next.getTitle());
    }

  }
}
