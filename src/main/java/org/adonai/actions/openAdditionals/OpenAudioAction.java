package org.adonai.actions.openAdditionals;

import org.adonai.model.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenAudioAction implements OpenAdditionalHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(OpenAudioAction.class);

  @Override
  public void open(Song song) {
    LOGGER.info("Open audio for song " + song.getId());


  }
}
