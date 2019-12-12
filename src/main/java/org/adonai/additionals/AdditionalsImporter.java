package org.adonai.additionals;

import org.adonai.model.Additional;
import org.adonai.model.Song;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalsImporter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalsImporter.class);


  public File getAdditionalFile (final Song song, final Additional additional) {
    File additionalsPath = Consts.ADDITIONALS_PATH;

    File additionalsTypePath = new File (additionalsPath, additional.getAdditionalType().name().toLowerCase());
    String suffix = additional.getLink().substring(additional.getLink().indexOf("."));

    return new File (additionalsTypePath, song.getId().toString() + suffix);
  }

  public void refreshCache (final Song song, final Additional additional, boolean clean) {
    File additionalFile = getAdditionalFile(song, additional);
    additional.setCacheLink(additionalFile.getAbsolutePath());
    if (clean || ! additionalFile.exists()) {

      try {
        LOGGER.info("Refresh cache (" + additional.getLink() + "->" + additionalFile.getAbsolutePath());
        FileUtils.copyFile(new File (additional.getLink()), additionalFile);
      } catch (IOException e) {
        throw new IllegalStateException("Error copying " + additional.getLink() + " to cache " + additionalFile.getAbsolutePath(), e);
      }
    }
  }


  public void removeAdditional (final Song song, final Additional additional) throws FileNotFoundException {
    File additionalFile = getAdditionalFile(song, additional);
    if (! additionalFile.exists())
      throw new FileNotFoundException("File " + additionalFile.getAbsolutePath() + " does not exist");
    else
      additionalFile.delete();

  }
}
