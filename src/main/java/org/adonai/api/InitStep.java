package org.adonai.api;

import org.adonai.ApplicationEnvironment;

public interface InitStep  {

  void execute (ApplicationEnvironment applicationEnvironment);

  boolean isExecuted (final ApplicationEnvironment applicationEnvironment);
}
