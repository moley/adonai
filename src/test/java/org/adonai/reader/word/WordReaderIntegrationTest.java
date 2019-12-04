package org.adonai.reader.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import org.adonai.model.Song;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 17.01.17.
 */
public class WordReaderIntegrationTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WordReaderIntegrationTest.class);


  @Test
  public void importRealTexts () throws IOException {
    File testWorshipmappe = new File("src/test/resources/worshipmappe.docx");
    WordReader wordReader = new WordReader();
    Collection<Song> songs = wordReader.read(new FileInputStream(testWorshipmappe));
    for (Song next: songs) {
      LOGGER.info("Song " + next.getId() + "-" + next.getTitle());
    }

  }
}
