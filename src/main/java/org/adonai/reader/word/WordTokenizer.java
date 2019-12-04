package org.adonai.reader.word;

import org.adonai.export.txtfile.TextfileDocumentBuilder;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordTokenizer implements  ITokenizer{

  private static final Logger LOGGER = LoggerFactory.getLogger(WordTokenizer.class);



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

    if (LOGGER.isDebugEnabled()) {
      for (String next : tokens) {
        LOGGER.debug("Added token <" + next + ">");
      }
    }


    return tokens;

  }

}
