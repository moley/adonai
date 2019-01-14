package org.adonai.export;

public enum ExportTokenType {
  CHORD (true),
  TEXT (false),
  TITLE (true),
  STRUCTURE (false);

  private boolean bold;

  private ExportTokenType (final boolean bold) {
    this.bold = bold;
  }

  public boolean isBold () {
    return bold;
  }
}
