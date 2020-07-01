package org.adonai.bibles;

import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiblePassage {

  private Logger LOGGER = LoggerFactory.getLogger(BiblePassage.class);

  private BibleLocation from;

  private BibleLocation to;

  private Collection<Verse> containedVerses = new ArrayList<Verse>();

  //BIBLE#BOOK(CHAPTER,VERSE-CHAPTER,VERSE)
  public BiblePassage (BibleContainer bibleContainer, String passage) {
    String [] tokens = passage.split("\\(");
    String bibleAndBook = tokens[0];
    String location = tokens [1].replace(")", "");
    String locationFrom = location;
    String locationTo = location;
    if (location.contains("-")) {
      String [] locationTokens = location.split("-");
      locationFrom = locationTokens[0];
      locationTo = locationTokens[1];
    }

    //grab bible and book
    String[] bibleAndBookTokens = bibleAndBook.split("#");
    String bibleName = bibleAndBookTokens[0];
    String bookName = bibleAndBookTokens[1];

    Bibles bibles = Bibles.valueOf(bibleName);
    Book book = Book.valueOf(bookName);
    Bible bible = bibleContainer.getBible(bibles);
    BibleBook bibleBook = bible.findBook(book);

    //grab chapter and verse
    locationFrom = completeLocation(bibleBook, locationFrom, false);
    locationTo = completeLocation(bibleBook, locationTo, true);
    from = new BibleLocation(locationFrom);
    to = new BibleLocation(locationTo);

    LOGGER.info("Analyze passage " + passage);
    LOGGER.info("- Bible          : " + bibleName);
    LOGGER.info("- Book           : " + bookName);
    LOGGER.info("- Location       : " + location );
    LOGGER.info("- Location from  : " + locationFrom );
    LOGGER.info("- Location to    : " + locationTo );
  }

  /**
   * completes the chapter if not contained
   *
   * @param bibleBook biblebook needed to get number of chapters
   * @param location location itself
   * @param end  true: get until location, false: get from location
   */
  private String completeLocation (final BibleBook bibleBook, String location, final boolean end ) {
    if (! location.contains(",")) {
      if (end) {
        Chapter chapter = bibleBook.findChapter(Integer.parseInt(location));
        return location + "," + chapter.getVerses().size();
      }
      else {
        return location + ",1";
      }
    }
    return location;
  }

  public boolean contains (final Chapter chapter, final Verse verse) {
    return false;
  }

  public BibleLocation getFrom() {
    return from;
  }

  public BibleLocation getTo() {
    return to;
  }
}
