package org.adonai.api;

import org.pf4j.ExtensionPoint;

/**
 * Application extension point
 */
public interface Application extends ExtensionPoint {

  void run (final String[] args);
}
