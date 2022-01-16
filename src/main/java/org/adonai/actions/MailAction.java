package org.adonai.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;

public class MailAction implements MainAction {
  @Override public String getIconname() {
    return null;
  }

  @Override public String getDisplayName() {
    return "Mail";
  }

  @Override public EventHandler<ActionEvent> getEventHandler(ApplicationEnvironment applicationEnvironment) {
    return null;
  }
}
