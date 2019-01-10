package org.adonai.additionals;

import org.adonai.model.Additional;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AdditionalsImporter {

  private File getAdditionalFile (final Additional additional) {
    File additionalsPath = Consts.ADDITIONALS_PATH;
    File additionalsTypePath = new File (additionalsPath, additional.getAdditionalType().name().toLowerCase());
    additionalsTypePath.mkdirs();
    File additionalPath = new File (additionalsTypePath, additional.getLink().replace(File.separator, "#"));
    return additionalPath;
  }

  /**
   * gets the additional file
   * @param additional
   */
  public File getAdditional (final Additional additional) throws IOException {
    File additionalFile = getAdditionalFile(additional);
    if (! additionalFile.exists()) {
      FileUtils.copyFile(new File (additional.getLink()), additionalFile);
    }
    return additionalFile;
  }

  public void removeAdditional (final Additional additional) throws FileNotFoundException {
    File additionalFile = getAdditionalFile(additional);
    if (! additionalFile.exists())
      throw new FileNotFoundException("File " + additionalFile.getAbsolutePath() + " does not exist");
    else
      additionalFile.delete();

  }
}
