package org.adonai.bibles;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class BibleServiceTest {

  private BibleService bibleService = new BibleService();
  private BibleContainer bibleContainer = bibleService.getAllBibles();

  @Test
  public void getBibles () {
    Collection<Bible> bibles = bibleContainer.getBibles();
    Assert.assertEquals("Number of bibles invalid", 2, bibles.size());
  }

  @Test
  public void getBible () {
    Bible bible = bibleContainer.getBible(Bibles.ELBERFELDER_1905);
    Assert.assertTrue ("Name invalid", Bibles.ELBERFELDER_1905.getName().equalsIgnoreCase(bible.getName()));
  }

  @Test
  public void normalized () {
    Bible bible = bibleContainer.getBible( Bibles.ELBERFELDER_1905);
    Verse verse = bible.findVerse(Book.GENESIS, 1, 2);
    Assert.assertEquals ("Und die Erde war wüst und leer, und Finsternis war über der Tiefe; und der Geist Gottes schwebte über den Wassern.", verse.getText());
  }

  @Test(expected = IllegalStateException.class)
  public void findInvalidChapter () {
    Bible bible = bibleContainer.getBible(Bibles.ELBERFELDER_1905);
    bible.findVerse(Book.GENESIS, 51, 2);
  }

  @Test(expected = IllegalStateException.class)
  public void findInvalidVerse () {
    Bible bible = bibleContainer.getBible(Bibles.ELBERFELDER_1905);
    bible.findVerse(Book.GENESIS, 50, 27);
  }
}
