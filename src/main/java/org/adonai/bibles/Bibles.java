package org.adonai.bibles;

public enum Bibles {

  LUTHER_1912("Luther 1912"),
  ELBERFELDER_1905 ("Elberfelder 1905");

  private final String name;

  private Bibles (final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
