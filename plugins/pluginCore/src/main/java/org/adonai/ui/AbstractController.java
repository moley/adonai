package org.adonai.ui;

import org.adonai.ApplicationEnvironment;

@Deprecated
public class AbstractController {

  private ApplicationEnvironment applicationEnvironment;

  public ApplicationEnvironment getApplicationEnvironment() {
    return applicationEnvironment;
  }

  public void setApplicationEnvironment(ApplicationEnvironment applicationEnvironment) {
    this.applicationEnvironment = applicationEnvironment;
  }
}
