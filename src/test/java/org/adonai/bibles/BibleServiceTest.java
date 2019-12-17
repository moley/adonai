package org.adonai.bibles;

import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;

public class BibleServiceTest {

  private BibleService bibleService = new BibleService();

  @Test
  public void getBibles () {
    Collection<Bible> bibles = bibleService.getAllBibles();
    Assert.assertEquals("Number of bibles invalid", 2, bibles.size());
  }

  @Test
  public void getBible () {
    Bible bible = bibleService.getBible(Bibles.ELBERFELDER_1905);
    Assert.assertTrue ("Name invalid", Bibles.ELBERFELDER_1905.getName().equalsIgnoreCase(bible.getName()));
  }

  @Test
  public void normalized () {
    Bible bible = bibleService.getBible(Bibles.ELBERFELDER_1905);
    Verse verse = bible.findVerse(Book.GENESIS, 1, 2);
    Assert.assertEquals ("Und die Erde war wüst und leer, und Finsternis war über der Tiefe; und der Geist Gottes schwebte über den Wassern.", verse.getText());


  }
}
