package org.adonai.actions;

import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.services.SessionService;

public class AddSessionAction {

  public Session add(Configuration configuration) {
    SessionService sessionService = new SessionService();
    return sessionService.newSession(configuration);
  }
}
