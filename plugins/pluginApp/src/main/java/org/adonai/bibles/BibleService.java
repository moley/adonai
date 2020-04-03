package org.adonai.bibles;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BibleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BibleService.class);

  public Collection<Bible> getAllBibles() {

    Set<String> bibleUrls = new HashSet<>();
    bibleUrls.add("/bibles/SF_2009-01-20_GER_LUTH1912_(LUTHER 1912).xml");
    bibleUrls.add("/bibles/SF_2009-01-22_GER_ELB1905STR_(ELBERFELDER 1905).xml");
    LOGGER.info("Found bibles: " + bibleUrls);
    List<Bible> bibles = new ArrayList<Bible>();
    for (String next : bibleUrls) {
      InputStream inputStream = getClass().getResourceAsStream(next);

      JAXBContext jc = null;
      try {
        jc = JAXBContext.newInstance(Bible.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Bible nextBible = (Bible) unmarshaller.unmarshal(inputStream);
        bibles.add(nextBible);
      } catch (JAXBException e) {
        throw new IllegalStateException(e);
      }
    }

    return bibles;

  }

  public Bible getBible(final Bibles bibles) {
    for (Bible next: getAllBibles()) {
      if (next.getName().equalsIgnoreCase(bibles.getName())) {
        return next;
      }
    }

    throw new IllegalStateException("Bible " + bibles.getName() + " not found");

  }
}
