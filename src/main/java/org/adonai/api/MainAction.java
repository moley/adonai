package org.adonai.api;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;

public interface MainAction  {

  String getIconname ();

  String getDisplayName ();

  EventHandler<ActionEvent> getEventHandler (ApplicationEnvironment applicationEnvironment);

}
