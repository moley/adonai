package org.adonai.actions.add;

import org.adonai.model.Configuration;
import org.adonai.model.SongBook;

public interface AddContentHandler {

  void add(Configuration configuration, SongBook songBook);
}
