package org.adonai.api;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.pf4j.ExtensionPoint;

public interface MainAction extends ExtensionPoint {

  String getIconname ();

  String getDisplayName ();

  EventHandler<ActionEvent> getEventHandler (ApplicationEnvironment applicationEnvironment);

}
