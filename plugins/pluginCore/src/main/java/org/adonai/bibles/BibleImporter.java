package org.adonai.bibles;

import generated.BIBLEBOOK;
import generated.CHAPTER;
import generated.GRAM;
import generated.VERS;
import generated.XMLBIBLE;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BibleImporter {

  private static final Logger LOGGER = LoggerFactory.getLogger(BibleImporter.class);

  public void importBibles (final File importPath, final File toPath) {
    JAXBContext jc = null;
    try {
      jc = JAXBContext.newInstance(XMLBIBLE.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();

      for (File configFile : importPath.listFiles()) {
        LOGGER.info("Importing " + configFile.getAbsolutePath());

        if (configFile.exists()) {

          String toName = configFile.getName();
          if (toName.indexOf("(") >= 0) {
            toName = toName.substring(toName.indexOf("(") + 1, toName.length()).replace(")", "");
          }

          Bible bible = new Bible();
          bible.setName(toName.replace(".xml", ""));

          JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(configFile);
          XMLBIBLE xmlbible = (XMLBIBLE) rootElement.getValue();
          if (LOGGER.isDebugEnabled())
            LOGGER.debug("Bible : " + xmlbible.getBiblename());
          for (JAXBElement<BIBLEBOOK> jaxbElementBiblebook : xmlbible.getBIBLEBOOK()) {
            BIBLEBOOK biblebookImportElement = jaxbElementBiblebook.getValue();
            BibleBook bibleBook = new BibleBook();
            bible.getBibleBooks().add(bibleBook);

            Book book = Book.findById(biblebookImportElement.getBnumber());
            bibleBook.setBook(book);

            if (LOGGER.isDebugEnabled())
              LOGGER.debug("Book " + biblebookImportElement + "_" + biblebookImportElement.getBname() + "-" + biblebookImportElement.getBsname() + "-" + biblebookImportElement
                .getBnumber());
            for (JAXBElement<CHAPTER> chapterjaxbElement : biblebookImportElement.getCHAPTER()) {
              CHAPTER chapterImportElement = chapterjaxbElement.getValue();
              Chapter chapter = new Chapter();
              chapter.setNumber(chapterImportElement.getCnumber().intValue());
              bibleBook.getChapters().add(chapter);

              if (LOGGER.isDebugEnabled())
                LOGGER.debug("   " + chapterImportElement.getCnumber());
              for (JAXBElement nextElement : chapterImportElement.getPROLOGOrCAPTIONOrVERS()) {

                if (nextElement.getDeclaredType().equals(VERS.class)) {
                  VERS versImportElement = (VERS) nextElement.getValue();
                  Verse verse = new Verse();
                  verse.setNumber(versImportElement.getVnumber().intValue());
                  chapter.getVerses().add(verse);
                  String versAsString = "";
                  for (Object nextGrammar : versImportElement.getContent()) {
                    if (nextGrammar instanceof JAXBElement) {
                      JAXBElement jaxbElement = (JAXBElement) nextGrammar;
                      if (jaxbElement.getValue() instanceof GRAM) {
                        GRAM gram = (GRAM) jaxbElement.getValue();
                        for (Object next : gram.getContent()) {
                          if (next instanceof String)
                            versAsString += next.toString();
                        }

                      } else
                        throw new IllegalStateException("Not supported type " + jaxbElement.getValue().getClass());
                    } else if (nextGrammar instanceof String) {
                      versAsString += nextGrammar.toString();
                    }

                  }
                  verse.setNumber(versImportElement.getVnumber().intValue());
                  verse.setText(normalizeText(versAsString));
                  if (LOGGER.isDebugEnabled())
                    LOGGER.debug("        VERS " + versImportElement.getVnumber() + "-" + versAsString);
                } else
                  throw new IllegalStateException(nextElement.getDeclaredType().toString());

              }
            }
          }

          JAXBContext jcBible = null;
          try {
            jcBible = JAXBContext.newInstance(Bible.class);
            Marshaller marshaller = jcBible.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);



            File toFile = new File (toPath, toName);

            LOGGER.info("Save bible " + toFile.getAbsolutePath());

            if (! toFile.getParentFile().exists())
              toFile.getParentFile().mkdirs();

            if (toFile.exists()) {
              FileUtils.deleteQuietly(toFile);
            }

            marshaller.marshal(bible, toFile);

          } catch (JAXBException e) {
            throw new IllegalStateException(e);
          }
        } else
          throw new IllegalStateException("File " + configFile.getAbsolutePath() + " does not exist");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error importing " + importPath.getAbsolutePath(), e);
    }

  }

  private String normalizeText (String origin) {
    origin = origin.replace("\n", "");
    origin = origin.trim();
    while (origin.contains("  ")) {
      origin = origin.replace("  ", " ");
    }

    origin = origin.replace(" ;", ";");
    origin = origin.replace(" .", ".");
    origin = origin.replace(" ,", ",");
    origin = origin.replace(" :", ":");
    origin = origin.replace(" !", "!");
    origin = origin.replace(" ?", "?");



    return origin;
  }

  //https://github.com/kohelet-net-admin/zefania-xml-bibles/tree/master/Bibles
  public final static void main(String[] args) {

    BibleImporter bibleImporter = new BibleImporter();
    bibleImporter.importBibles(new File ("bibles"), new File ("src/main/resources/bibles"));


  }

}
