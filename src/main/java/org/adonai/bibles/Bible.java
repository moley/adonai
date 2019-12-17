package org.adonai.bibles;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Bible {

  private String name;

  private List<BibleBook> bibleBooks = new ArrayList<BibleBook>();

  public List<BibleBook> getBibleBooks() {
    return bibleBooks;
  }

  public void setBibleBooks(List<BibleBook> bibleBooks) {
    this.bibleBooks = bibleBooks;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BibleBook findBook (final Book book) {
    for (BibleBook next: getBibleBooks()) {
      if (next.getBook().equals(book)) {
        return next;
      }
    }

    throw new IllegalStateException("Book " + book.getBibleserverName() + " not found in bible " + getName());

  }

  public Verse findVerse (final Book book, final int chapter, final int verse ) {

    BibleBook foundBibleBook = findBook(book);
    Chapter foundChapter = foundBibleBook.findChapter(chapter);
    return foundChapter.findVerse (verse);


  }
}
