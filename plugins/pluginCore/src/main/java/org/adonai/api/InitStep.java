package org.adonai.api;

import org.adonai.ApplicationEnvironment;
import org.pf4j.ExtensionPoint;

public interface InitStep extends ExtensionPoint {

  void execute (ApplicationEnvironment applicationEnvironment);

  boolean isExecuted (final ApplicationEnvironment applicationEnvironment);
}
