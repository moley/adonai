package org.adonai.bibles;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class BibleReference {

  private Logger LOGGER = LoggerFactory.getLogger(BibleReference.class);

  private BibleLocation from;

  private BibleLocation to;


  //BOOK(CHAPTER,VERSE-CHAPTER,VERSE)
  //1.Mose 12,15-13,16
  public BibleReference(final Bibles bibles, String passage) {
    String locationFrom = null;
    String locationTo = null;
    if (passage.contains("-")) {
      String [] locationTokens = passage.split("-");
      locationFrom = locationTokens[0];
      locationTo = locationTokens[1];
    }
    else locationFrom = passage;

    from = new BibleLocation(bibles, locationFrom, null);
    to = new BibleLocation(bibles, locationTo, from.getBook());
  }

  public BibleLocation getFrom() {
    return from;
  }

  public BibleLocation getTo() {
    return to;
  }

  @Override public String toString() {
    return "BibleReference{" + "from=" + from + ", to=" + to + '}';
  }
}
