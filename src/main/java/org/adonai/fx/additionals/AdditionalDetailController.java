package org.adonai.fx.additionals;

import org.adonai.fx.AbstractController;
import org.adonai.model.Additional;

public abstract class AdditionalDetailController extends AbstractController {

  private Additional additional;

  public Additional getAdditional() {
    return additional;
  }

  public void setAdditional(Additional additional) {
    this.additional = additional;
  }

  protected abstract void save ();
}
