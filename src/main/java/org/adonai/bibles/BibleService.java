package org.adonai.bibles;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BibleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BibleService.class);

  public Collection<Bible> getAllBibles() {

    Collection<Bible> bibles = new ArrayList<Bible>();
    Reflections reflections = new Reflections("bibles", new ResourcesScanner());
    Set<String> fileNames = reflections.getResources(Pattern.compile(".*\\.xml"));
    LOGGER.info("Found bibles: " + fileNames);
    for (String next : fileNames) {
      InputStream inputStream = getClass().getResourceAsStream("/" + next);

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
