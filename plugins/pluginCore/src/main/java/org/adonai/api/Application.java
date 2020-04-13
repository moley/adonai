package org.adonai.api;

import org.adonai.ApplicationEnvironment;
import org.pf4j.ExtensionPoint;

/**
 * Application extension point
 */
public interface Application extends ExtensionPoint {

  void run (final ApplicationEnvironment pluginManager, final String[] args);
}
