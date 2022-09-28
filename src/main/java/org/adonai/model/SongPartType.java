package org.adonai.model;

public enum SongPartType {

  REFRAIN("Refrain"),
  VERS("Vers"),
  INTRO("Intro"),
  BRIDGE("Bridge"),
  INSTRUMENTAL("Instrumental"),
  SOLO ("Solo"),
  ZWISCHENSPIEL ("Zwischenspiel"),
  EXTRO ("Extro"),
  RAP ("Rap"),
  PRECHORUS ("PreChorus");

  private String displayname;

  private SongPartType (final String displayname) {
    this.displayname = displayname;
  }

  public String getDisplayname () {
    return displayname;
  }
}
