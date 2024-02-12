package org.adonai.bibles;

import org.junit.Assert;
import org.junit.Test;

public class BibelReferenceTest {

  private BibleService bibleService = new BibleService();

  @Test
  public void oneChapter () {

    BibleReference biblePassage = new BibleReference(Bibles.LUTHER_1912, "1.Mose 12");
    BibleLocation bibleLocationFrom = biblePassage.getFrom();
    BibleLocation bibleLocationTo = biblePassage.getTo();
    Assert.assertEquals ("12,1", bibleLocationFrom.toString());
    Assert.assertEquals ("12,20", bibleLocationTo.toString());

  }

  @Test
  public void oneVerse () {
    BibleReference bibleReference = new BibleReference(Bibles.LUTHER_1912, "1.Mose 12,5");
    BibleLocation bibleLocationFrom = bibleReference.getFrom();
    BibleLocation bibleLocationTo = bibleReference.getTo();
    Assert.assertEquals ("12,5", bibleLocationFrom.toString());
    Assert.assertEquals ("12,5", bibleLocationTo.toString());
  }


  @Test
  public void multipleChapters () {
    BibleReference bibleReference = new BibleReference(Bibles.LUTHER_1912, "1.Mose 12-14");
    BibleLocation bibleLocationFrom = bibleReference.getFrom();
    BibleLocation bibleLocationTo = bibleReference.getTo();
    Assert.assertEquals ("12,1", bibleLocationFrom.toString());
    Assert.assertEquals ("14,24", bibleLocationTo.toString());


  }

  @Test
  public void versesFromTwoChapters () {
    BibleReference bibleReference = new BibleReference(Bibles.LUTHER_1912,  "1.Mose 12,5-13,1");
    BibleLocation bibleLocationFrom = bibleReference.getFrom();
    BibleLocation bibleLocationTo = bibleReference.getTo();
    Assert.assertEquals ("12,5", bibleLocationFrom.toString());
    Assert.assertEquals ("13,1", bibleLocationTo.toString());


  }


}
