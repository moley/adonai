package org.adonai.bibles;

public class BibleReference {

  private BibleLocation from;

  private BibleLocation to;

  public BibleReference (final BibleLocation from, final BibleLocation to) {
    this.from = from;
    this.to = to;
  }

  public BibleLocation getFrom() {
    return from;
  }

  public BibleLocation getTo() {
    return to;
  }
}
