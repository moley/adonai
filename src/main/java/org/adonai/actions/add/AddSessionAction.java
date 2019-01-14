package org.adonai.actions.add;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.adonai.model.Configuration;
import org.adonai.model.SongBook;
import org.adonai.services.SessionService;

public class AddSessionAction implements AddContentHandler {
  @Override
  public void add(Configuration configuration, SongBook songBook, EventHandler<WindowEvent> closeRequest) {
    SessionService sessionService = new SessionService();
    sessionService.newSession(configuration);
  }
}
