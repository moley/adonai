package org.adonai.bibles;

import org.junit.Assert;
import org.junit.Test;

public class BibelPassageTest {

  private BibleService bibleService = new BibleService();
  private BibleContainer bibleContainer = bibleService.getAllBibles();

  @Test
  public void oneChapter () {

    BiblePassage biblePassage = new BiblePassage(bibleContainer, "LUTHER_1912#GENESIS(12)");
    BibleLocation bibleLocationFrom = biblePassage.getFrom();
    BibleLocation bibleLocationTo = biblePassage.getTo();
    Assert.assertEquals ("12,1", bibleLocationFrom.toString());
    Assert.assertEquals ("12,20", bibleLocationTo.toString());

  }

  @Test
  public void oneVerse () {
    BiblePassage biblePassage = new BiblePassage(bibleContainer, "LUTHER_1912#GENESIS(12,5)");
    BibleLocation bibleLocationFrom = biblePassage.getFrom();
    BibleLocation bibleLocationTo = biblePassage.getTo();
    Assert.assertEquals ("12,5", bibleLocationFrom.toString());
    Assert.assertEquals ("12,5", bibleLocationTo.toString());


  }


  @Test
  public void multipleChapters () {
    BiblePassage biblePassage = new BiblePassage(bibleContainer, "LUTHER_1912#GENESIS(12-14)");
    BibleLocation bibleLocationFrom = biblePassage.getFrom();
    BibleLocation bibleLocationTo = biblePassage.getTo();
    Assert.assertEquals ("12,1", bibleLocationFrom.toString());
    Assert.assertEquals ("14,24", bibleLocationTo.toString());


  }

  @Test
  public void versesFromTwoChapters () {
    BiblePassage biblePassage = new BiblePassage(bibleContainer, "LUTHER_1912#GENESIS(12,5-13,1)");
    BibleLocation bibleLocationFrom = biblePassage.getFrom();
    BibleLocation bibleLocationTo = biblePassage.getTo();
    Assert.assertEquals ("12,5", bibleLocationFrom.toString());
    Assert.assertEquals ("13,1", bibleLocationTo.toString());


  }


}
