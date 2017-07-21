package org.adonai.reader.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class WordTokenizer implements  ITokenizer{


  public Collection<String> getTokens (final InputStream inputStream) {

    Collection<String> tokens = new ArrayList<String>();

    XWPFDocument docx = null;
    try {
      docx = new XWPFDocument(inputStream);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    //using XWPFWordExtractor Class



    for (int i = 0; i < docx.getParagraphs().size(); i++) {

      XWPFParagraph nextParagraph = docx.getParagraphs().get(i);

      String paragraph = nextParagraph.getText();
      tokens.add(paragraph);
    }

    for (String next: tokens) {
      System.out.println("-" + next);
    }


    return tokens;

  }

}
