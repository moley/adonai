package org.adonai.actions.add;

import org.adonai.model.Configuration;
import org.adonai.model.SongBook;
import org.adonai.services.SessionService;

public class AddSessionAction implements AddContentHandler {
  @Override
  public void add(Configuration configuration, SongBook songBook) {
    SessionService sessionService = new SessionService();
    sessionService.newSession(configuration);
  }
}
