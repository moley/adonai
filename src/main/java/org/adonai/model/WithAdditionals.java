package org.adonai.model;

import java.util.ArrayList;
import java.util.List;

public class WithAdditionals {

  private List<Additional> additionals = new ArrayList<Additional>();

  public Additional findAdditional (final AdditionalType additionalType) {
    for (Additional next: additionals) {
      if (next.getAdditionalType().equals(additionalType))
        return next;
    }
    return null;
  }

  public List<Additional> getAdditionals () {
    return additionals;
  }

  public Additional getAdditional (final AdditionalType additionalType) {
    for (Additional next: additionals) {
      if (next.getAdditionalType().equals(additionalType))
        return next;
    }
    return null;
  }

  public void setAdditionals (final List<Additional> additionals) {
    this.additionals = additionals;
  }

  public void setAdditional (final Additional additional) {
    for (Additional next: additionals) {
      if (next.getAdditionalType().equals(additional.getAdditionalType())) {
        additionals.remove(next);
        break;
      }
    }

    additionals.add(additional);
  }

}
