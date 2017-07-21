package org.adonai.reader.word;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

public class WordTokenizerTest {

  @Test
  public void tokenize () throws FileNotFoundException {
    File testWorshipmappe = new File("src/test/resources/worshipmappe.docx");
    WordTokenizer wordReader = new WordTokenizer();
    Collection<String> songs = wordReader.getTokens(new FileInputStream(testWorshipmappe));


  }
}
