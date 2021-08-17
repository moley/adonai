package org.adonai.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.adonai.ApplicationEnvironment;
import org.adonai.api.MainAction;
import org.pf4j.Extension;

@Extension(ordinal=1)
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
