package org.adonai.bibles;

import java.util.ArrayList;
import java.util.List;

public class BibleBook {


  private List<Chapter> chapters = new ArrayList<Chapter>();

  private Book book;

  public List<Chapter> getChapters() {
    return chapters;
  }

  public void setChapters(List<Chapter> chapters) {
    this.chapters = chapters;
  }


  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public Chapter findChapter (final int chapter) {
    for (Chapter next: chapters) {
      if (next.getNumber() == chapter)
        return next;
    }

    throw new IllegalStateException("Chapter " + chapter + " not found in " + book.getBibleserverName() + "(" + chapters.size() + ")");
  }
}
