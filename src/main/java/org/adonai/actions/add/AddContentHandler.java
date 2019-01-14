package org.adonai.actions.add;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import org.adonai.model.Configuration;
import org.adonai.model.SongBook;

public interface AddContentHandler {

  void add(Configuration configuration, SongBook songBook, EventHandler<WindowEvent> closeRequest);
}
