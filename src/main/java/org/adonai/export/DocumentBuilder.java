package org.adonai.export;

import org.adonai.SizeInfo;

import java.io.File;

public interface DocumentBuilder {

  public void newToken (final ExportToken exportToken);

  public SizeInfo getSize (final String text, final ExportTokenType exportTokenType);

  public void write (final File outputfile);

  /**
   * gets default configuration for the given exporter
   * @return
   */
  ExportConfiguration getDefaultConfiguration ();
}
