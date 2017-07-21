package org.adonai;

public enum Key {

  C(0, 0),
  G(0, 1),
  D(0, 2),
  A(0, 3),
  E(0, 4),
  H(0, 5),
  Fis(0, 6),
  Cis(0, 7),
  Gis(0, 8),
  Dis(0, 9),

  F (1, 0),
  Bb (2, 0),
  Eb (3, 0),
  Ab (4, 0),
  Db (5, 0),
  Gb (6, 0),
  Cb (7, 0);

  int decrementSigns = 0;
  int incrementSigns = 0;

  Key (final int decrementSigns, final int incrementSigns) {
    this.decrementSigns = decrementSigns;
    this.incrementSigns = incrementSigns;
  }

  public static Key fromString (String key) {
    key = key.replace("#", "is");
    return Key.valueOf(key);
  }

}
