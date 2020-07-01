package org.adonai.bibles;

import java.util.ArrayList;
import java.util.List;

public class BibleContainer {

  private List<Bible> bibles = new ArrayList<Bible>();

  public List<Bible> getBibles() {
    return bibles;
  }

  public void setBibles(List<Bible> bibles) {
    this.bibles = bibles;
  }

  public Bible getBible(final Bibles bibles) {
    for (Bible next: getBibles()) {
      if (next.getName().equalsIgnoreCase(bibles.getName())) {
        return next;
      }
    }

    throw new IllegalStateException("Bible " + bibles.getName() + " not found");

  }


}
