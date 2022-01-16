package org.adonai.api;

import org.adonai.ApplicationEnvironment;

/**
 * Application extension point
 */
public interface Application  {

  void run (final ApplicationEnvironment pluginManager, final String[] args);
}
