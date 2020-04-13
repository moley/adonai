package org.adonai.api;

import org.pf4j.ExtensionPoint;

public interface Configuration extends ExtensionPoint {

  /**
   * filename of the mask
   * @return filename
   */
  String getMaskFilename ();
}
