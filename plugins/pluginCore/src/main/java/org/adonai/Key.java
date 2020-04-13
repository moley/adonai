package org.adonai;

public enum Key {

  C(0, 0, "C"),
  G(0, 1, "G"),
  D(0, 2, "D"),
  A(0, 3, "A"),
  E(0, 4, "E"),
  H(0, 5, "H"),
  Fis(0, 6, "F#"),
  Cis(0, 7, "C#"),
  Gis(0, 8, "G#"),
  Dis(0, 9, "D#"),

  F (1, 0, "F"),
  Bb (2, 0, "Bb"),
  Eb (3, 0, "Eb"),
  Ab (4, 0, "Ab"),
  Db (5, 0, "Db"),
  Gb (6, 0, "Gb"),
  Cb (7, 0, "Cb");

  int decrementSigns = 0;
  int incrementSigns = 0;

  private String description;

  Key (final int decrementSigns, final int incrementSigns, final String description) {
    this.decrementSigns = decrementSigns;
    this.incrementSigns = incrementSigns;
    this.description = description;
  }

  public static Key fromString (String key) {
    key = key.replace("#", "is");
    return Key.valueOf(key);
  }

  public String getDescription () {
    return description;
  }

}
