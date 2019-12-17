package org.adonai.bibles;

import java.math.BigInteger;

public enum Book {
  GENESIS (Testament.OLD, "1", "1.Mose", 50),
  EXODUS (Testament.OLD, "2","2.Mose", 40),
  LEVITICUS (Testament.OLD, "3",  "2.Mose", 27 ),
  NUMBERS (Testament.OLD, "4",  "3.Mose", 36),
  DEUTERONOMY (Testament.OLD, "5", "4.Mose", 34),
  JOSHUA (Testament.OLD,"6",  "Josua", 24),
  JUDGES (Testament.OLD,"7",  "Richter", 21),
  RUTH (Testament.OLD,"8",  "Rut", 4),
  FIRST_SAMUEL (Testament.OLD, "9", "1.Samuel", 31),
  SECOND_SAMUEL (Testament.OLD, "10", "2.Samuel", 24),
  FIRST_KINGS (Testament.OLD, "11", "1.Könige", 22),
  SECOND_KINGS(Testament.OLD, "12", "2.Könige", 25),
  FIRST_CHRONICLES (Testament.OLD, "13", "1.Chronik", 29),
  SECOND_CHRONICLES (Testament.OLD, "14", "2.Chronik",36),
  EZRA (Testament.OLD, "15", "Esra", 10),
  NEHEMIAH (Testament.OLD, "16", "Nehemia", 13),
  ESTHER (Testament.OLD, "17",  "Esther", 10),
  JOB (Testament.OLD, "18", "Hiob", 42),
  PSALMS (Testament.OLD, "19",  "Psalm", 150),
  PROVERBS (Testament.OLD, "20",  "Sprüche", 31),
  ECCLESIASTES (Testament.OLD, "21",  "Prediger", 12),
  THE_SONG_OF_SOLOMON(Testament.OLD, "22",  "Hoheslied", 8),
  ISAJAH (Testament.OLD,"23",  "Jesaja", 60),
  JEREMIAH (Testament.OLD, "24",  "Jeremia", 52),
  LAMENTATIONS (Testament.OLD, "25",  "Klagelieder", 5),
  EZEKIEL (Testament.OLD, "26", "Hesekiel", 48),
  DANIEL (Testament.OLD, "27", "Daniel", 12),
  HOSEA(Testament.OLD, "28", "Hosea", 14),
  JOEL (Testament.OLD, "29", "Joel", 3),
  AMOS (Testament.OLD, "30", "Amos", 9),
  OBADIAH (Testament.OLD, "31", "Obadja", 1),
  JONAH (Testament.OLD, "32", "Jona", 4),
  MICHA (Testament.OLD, "33", "Micha", 7),
  NAHUM (Testament.OLD, "34", "Nahum", 3),
  HABAKKUK (Testament.OLD, "35", "Habakuk", 3),
  ZEPHANIAH (Testament.OLD, "36", "Zefanja", 3),
  HAGGAI (Testament.OLD, "37", "Haggai", 2),
  ZECHARIAH (Testament.OLD, "38", "Sacharja", 14),
  MALACHI (Testament.OLD, "39", "Maleachi", 3),


  MATTHEW (Testament.NEW, "40", "Matthäus", 28),
  MARK (Testament.NEW, "41", "Markus", 16),
  LUKE (Testament.NEW, "42", "Lukas", 24),
  JOHN (Testament.NEW, "43", "Johannes", 21),
  ACTS_OF_THE_APOSTLES (Testament.NEW, "44",  "Apostelgeschichte", 28),
  ROMANS (Testament.NEW, "45",  "Römer", 16),
  FIRST_CORINTHIANS (Testament.NEW,"46",  "1.Korinther", 16),
  SECOND_CORINTHIANS (Testament.NEW, "47", "2.Korinther", 13),
  GALATIANS (Testament.NEW, "48", "Galater", 6),
  EPHESIANS (Testament.NEW, "49", "Epheser", 6),
  PHILIPPIANS (Testament.NEW, "50", "Philipper", 4),
  COLOSSIANS (Testament.NEW, "51", "Kolosser", 4),
  FIRST_THESSALONIANS (Testament.NEW, "52", "1.Thessalonicher", 5),
  SECOND_THESSALONIANS (Testament.NEW, "53", "2.Thessalonicher", 3),
  FIRST_TIMOTHY (Testament.NEW, "54", "1.Timotheus", 6),
  SECOND_TIMOTHY (Testament.NEW, "55", "2.Timotheus", 4),
  TITUS (Testament.NEW, "56", "Titus", 3),
  PHILEMON (Testament.NEW, "57", "Philemon", 1),
  HEBREWS (Testament.NEW, "58", "Hebräer", 12),
  JAMES (Testament.NEW, "59", "Jakobus", 5),
  FIRST_PETER (Testament.NEW, "60", "1.Petrus", 5),
  SECOND_PETER (Testament.NEW, "61", "2.Petrus", 3),
  FIRST_JOHN (Testament.NEW, "62", "1.Johannes", 5),
  SECOND_JOHN (Testament.NEW, "63", "2.Johannes", 1),
  THIRD_JOHN (Testament.NEW, "64", "3.Johannes", 1),
  JUDE (Testament.NEW, "65", "Judas", 1),
  REVELATION (Testament.NEW, "66", "Offenbarung", 12);

  private final String bibleserverName;

  private final int numberChapters;
  private final Testament testament;
  private final String id;

  private Book (final Testament testament, final String id, final String bibleserverName, int numberChapters) {
    this.bibleserverName = bibleserverName;
    this.numberChapters = numberChapters;
    this.testament = testament;
    this.id = id;
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

  public String getId () {
    return id;
  }

  public static Book findById (BigInteger bigInteger) {
    String idAsString = bigInteger.toString();
    for (Book next: values()) {
      if (next.getId().equals(idAsString))
        return next;
    }

    throw new IllegalStateException("Book " + bigInteger + " not found in (" + Book.values() + ")");

  }
}
