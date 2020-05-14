package org.adonai.fx;

import org.adonai.ApplicationEnvironment;

public class AbstractController {

  private ApplicationEnvironment applicationEnvironment;

  public ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }

}
