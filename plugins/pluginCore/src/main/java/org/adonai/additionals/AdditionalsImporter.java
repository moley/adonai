package org.adonai.additionals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.adonai.AdonaiProperties;
import org.adonai.model.Additional;
import org.adonai.model.Song;
import org.adonai.fx.Consts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalsImporter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalsImporter.class);



  private File additionalsPath;




  public File getAdditionalFile (final Song song, final Additional additional) {

    AdonaiProperties adonaiProperties = new AdonaiProperties();
    File additionalsTypePath = new File (getAdditionalsPath(adonaiProperties.getCurrentTenant()), additional.getAdditionalType().name().toLowerCase());
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
        LOGGER.error("Error refreshing cache: " + e.getLocalizedMessage());
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

  public File getAdditionalsPath(String tenant) {
    if (additionalsPath != null)
      return additionalsPath;
    else
      return Consts.getAdditionalsPath(tenant);
  }

  public void setAdditionalsPath(File additionalsPath) {
    this.additionalsPath = additionalsPath;
  }
}
