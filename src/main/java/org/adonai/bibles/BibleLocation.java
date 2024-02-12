package org.adonai.bibles;

public class BibleLocation {

  private final Bibles bible;



  private Book book;

  private String chapter;

  private String verse;



  /**
   * z.B. 1.Mose 1,12
   * @param asString
   */
  public BibleLocation (Bibles bible, String asString, final Book defaultBook) {
    this.bible = bible;
    String [] tokens = asString.split(" ");
    //Find book from first token
    for (Book next: Book.values()) {
      if (tokens[0].equals(next.getBibleserverName())) {
        book = next;
      }
    }

    //or choose default book from previous
    if (book == null)
      book = defaultBook;

    //if not book cannot be found throw an error
    if (book == null)
      throw new IllegalStateException("Book " + asString + " with defaultbook " + defaultBook.name() + " cannot be found");

    if (tokens.length == 2) {
      String [] chapterAndVers = tokens[1].split(",");
      chapter = chapterAndVers[0];
      if (chapterAndVers.length == 2)
        verse = chapterAndVers[1];
    }
  }



  public Bibles getBible() {
    return bible;
  }

  public Book getBook() {
    return book;
  }

  @Override public String toString() {
    return "BibleLocation{" + "bible=" + bible + ", book=" + book + ", chapter='" + chapter + '\'' + ", verse='" + verse + '\'' + '}';
  }

  public String getReference () {
    throw new IllegalStateException("NYI");

  }

  public String getChapter() {
    return chapter;
  }

  public String getVerse() {
    return verse;
  }
}
