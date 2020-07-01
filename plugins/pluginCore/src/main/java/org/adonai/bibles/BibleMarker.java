package org.adonai.bibles;

public class BibleMarker {

  public void mark (final BibleBook bibleBook, final BiblePassage biblePassage) {

    for (Chapter next: bibleBook.getChapters()) {
      for (Verse verse: next.getVerses()) {
        verse.setMarked(biblePassage.contains(next, verse));
      }
    }


  }

  public void removeMarks (final BibleBook bibleBook) {
    for (Chapter next: bibleBook.getChapters()) {
      for (Verse verse: next.getVerses()) {
        verse.setMarked(false);
      }
    }
  }
}
