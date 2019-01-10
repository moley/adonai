package org.adonai.bibles;

public enum Book {
  GENESIS (Testament.OLD, "1.Mose", 50),
  EXODUS (Testament.OLD, "2.Mose", 40),
  LEVITICUS (Testament.OLD, "2.Mose", 27 ),
  NUMBERS (Testament.OLD, "3.Mose", 36),
  DEUTERONOMY (Testament.OLD, "4.Mose", 34),
  JOSHUA (Testament.OLD, "Josua", 24),
  JUDGES (Testament.OLD, "Richter", 21),
  RUTH (Testament.OLD, "Rut", 4),
  FIRST_SAMUEL (Testament.OLD, "1.Samuel", 31),
  SECOND_SAMUEL (Testament.OLD, "2.Samuel", 24),
  FIRST_KINGS (Testament.OLD, "1.Könige", 22),
  SECOND_KINGS(Testament.OLD, "2.Könige", 25),
  FIRST_CHRONICLES (Testament.OLD, "1.Chronik", 29),
  SECOND_CHRONICLES (Testament.OLD, "2.Chronik",36),
  EZRA (Testament.OLD, "Esra", 10),
  NEHEMIAH (Testament.OLD, "Nehemia", 13),
  ESTHER (Testament.OLD, "Esther", 10),
  JOB (Testament.OLD, "Hiob", 42),
  PSALMS (Testament.OLD, "Psalm", 150),
  PROVERBS (Testament.OLD, "Sprüche", 31),
  ECCLESIASTES (Testament.OLD, "Prediger", 12),
  THE_SONG_OF_SOLOMON(Testament.OLD, "Hoheslied", 8),
  ISAJAH (Testament.OLD, "Jesaja", 60),
  JEREMIAH (Testament.OLD, "Jeremia", 52),
  LAMENTATIONS (Testament.OLD, "Klagelieder", 5),
  EZEKIEL (Testament.OLD, "Hesekiel", 48),
  DANIEL (Testament.OLD, "Daniel", 12),
  HOSEA(Testament.OLD, "Hosea", 14),
  JOEL (Testament.OLD, "Joel", 3),
  AMOS (Testament.OLD, "Amos", 9),
  OBADIAH (Testament.OLD, "Obadja", 1),
  JONAH (Testament.OLD, "Jona", 4),
  MICHA (Testament.OLD, "Micha", 7),
  NAHUM (Testament.OLD, "Nahum", 3),
  HABAKKUK (Testament.OLD, "Habakuk", 3),
  ZEPHANIAH (Testament.OLD, "Zefanja", 3),
  HAGGAI (Testament.OLD, "Haggai", 2),
  ZECHARIAH (Testament.OLD, "Sacharja", 14),
  MALACHI (Testament.OLD, "Maleachi", 3),


  MATTHEW (Testament.NEW, "Matthäus", 28),
  MARK (Testament.NEW, "Markus", 16),
  LUKE (Testament.NEW, "Lukas", 24),
  JOHN (Testament.NEW, "Johannes", 21),
  ACTS_OF_THE_APOSTLES (Testament.NEW, "Apostelgeschichte", 28),
  ROMANS (Testament.NEW, "Römer", 16),
  FIRST_CORINTHIANS (Testament.NEW, "1.Korinther", 16),
  SECOND_CORINTHIANS (Testament.NEW, "2.Korinther", 13),
  GALATIANS (Testament.NEW, "Galater", 6),
  EPHESIANS (Testament.NEW, "Epheser", 6),
  PHILIPPIANS (Testament.NEW, "Philipper", 4),
  COLOSSIANS (Testament.NEW, "Kolosser", 4),
  FIRST_THESSALONIANS (Testament.NEW, "1.Thessalonicher", 5),
  SECOND_THESSALONIANS (Testament.NEW, "2.Thessalonicher", 3),
  FIRST_TIMOTHY (Testament.NEW, "1.Timotheus", 6),
  SECOND_TIMOTHY (Testament.NEW, "2.Timotheus", 4),
  TITUS (Testament.NEW, "Titus", 3),
  PHILEMON (Testament.NEW, "Philemon", 1),
  HEBREWS (Testament.NEW, "Hebräer", 12),
  JAMES (Testament.NEW, "Jakobus", 5),
  FIRST_PETER (Testament.NEW, "1.Petrus", 5),
  SECOND_PETER (Testament.NEW, "2.Petrus", 3),
  FIRST_JOHN (Testament.NEW, "1.Johannes", 5),
  SECOND_JOHN (Testament.NEW, "2.Johannes", 1),
  THIRD_JOHN (Testament.NEW, "3.Johannes", 1),
  JUDE (Testament.NEW, "Judas", 1),
  REVELATION (Testament.NEW, "Offenbarung", 12);



  private final String bibleserverName;

  private final int numberChapters;
  private final Testament testament;

  private Book (final Testament testament, final String bibleserverName, int numberChapters) {
    this.bibleserverName = bibleserverName;
    this.numberChapters = numberChapters;
    this.testament = testament;
  }

  public String getBibleserverName() {
    return bibleserverName;
  }

  public int getNumberChapters() {
    return numberChapters;
  }

  public Testament getTestament() {
    return testament;
  }
}
