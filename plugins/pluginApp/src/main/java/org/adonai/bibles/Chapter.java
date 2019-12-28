package org.adonai.bibles;

import java.util.ArrayList;
import java.util.List;

public class Chapter {

  private List<Verse> verses = new ArrayList<Verse>();

  private int number;

  public List<Verse> getVerses() {
    return verses;
  }

  public void setVerses(List<Verse> verses) {
    this.verses = verses;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public Verse findVerse(int verse) {

    for (Verse next : verses) {
      if (next.getNumber() == verse)
        return next;
    }

    throw new IllegalStateException("Verse " + verse + " not found in chapter " + number + "(" + verses.size() + ")");
  }

}
