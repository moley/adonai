package org.adonai.actions.openAdditionals;

import org.adonai.model.Song;

import java.util.logging.Logger;

public class OpenAudioAction implements OpenAdditionalHandler {
  private static final Logger LOGGER = Logger.getLogger(OpenAudioAction.class.getName());

  @Override
  public void open(Song song) {
    LOGGER.info("Open audio for song " + song.getId());


  }
}
