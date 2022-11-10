package org.adonai.export;

public enum ExportTokenType {
  CHORD (true, false),
  TEXT (false, false),
  TITLE (true, false),
  STRUCTURE (false, false),
  REMARKS (false, true),
  NEW_PAGE (false, false);

  private boolean bold;
  private boolean italic;

  private ExportTokenType (final boolean bold, final boolean italic) {
    this.bold = bold;
    this.italic = italic;
  }

  public boolean isItalic() {
    return italic;
  }

  public boolean isBold () {
    return bold;
  }

}
