package org.adonai.reader.text;

public class TextfileReaderParam {

  private boolean emptyLineIsNewPart = false;

  private boolean withTitle = true;


  public boolean isEmptyLineIsNewPart() {
    return emptyLineIsNewPart;
  }

  public void setEmptyLineIsNewPart(boolean emptyLineIsNewPart) {
    this.emptyLineIsNewPart = emptyLineIsNewPart;
  }

  public boolean isWithTitle() {
    return withTitle;
  }

  public void setWithTitle(boolean withTitle) {
    this.withTitle = withTitle;
  }
}
