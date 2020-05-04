package org.adonai.api;

import javafx.scene.control.Button;
import org.adonai.ApplicationEnvironment;
import org.pf4j.ExtensionPoint;

public interface MainAction extends ExtensionPoint {

  Button createButton (final ApplicationEnvironment applicationEnvironment);
}
