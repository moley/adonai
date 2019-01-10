package org.adonai.bibles;


import generated.BIBLEBOOK;
import generated.CHAPTER;
import generated.VERS;
import generated.XMLBIBLE;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class BibleReader {

  //https://github.com/kohelet-net-admin/zefania-xml-bibles/tree/master/Bibles
  public final static void main (String [] args) {
    JAXBContext jc = null;
    try {
      jc = JAXBContext.newInstance(XMLBIBLE.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();

      //File configFile = new File ("src/main/resources/bibles/SF_2009-01-20_GER_LUTH1912_(LUTHER 1912).xml");
      File configFile = new File ("src/main/resources/bibles/SF_2009-01-22_GER_ELB1905STR_(ELBERFELDER 1905).xml");
      //unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
      if (configFile.exists()) {
        JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(configFile);
        XMLBIBLE xmlbible = (XMLBIBLE) rootElement.getValue();
        System.out.println (xmlbible.getBiblename());
        for (JAXBElement<BIBLEBOOK> book:  xmlbible.getBIBLEBOOK()) {
          BIBLEBOOK biblebook = book.getValue();
          System.out.println ("Book " + biblebook.getBname() + "-" + biblebook.getBnumber());
          for (JAXBElement<CHAPTER> chapterjaxbElement : biblebook.getCHAPTER()) {
            CHAPTER chapter = chapterjaxbElement.getValue();
            System.out.println ("   " + chapter.getCnumber());
            for (JAXBElement nextElement: chapter.getPROLOGOrCAPTIONOrVERS()) {


              if (nextElement.getDeclaredType().equals(VERS.class)) {
                VERS vers = (VERS) nextElement.getValue();
                System.out.println ("        VERS " + vers.getVnumber()  + "-" +  vers.getContent());
              }
              else
                throw new IllegalStateException(nextElement.getDeclaredType().toString());

            }
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }


  }

}
