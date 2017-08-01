package org.adonai.export;

import org.adonai.SizeInfo;

import java.io.File;

public interface DocumentBuilder {

  void newToken (final ExportToken exportToken);

  SizeInfo getSize (final String text, final ExportTokenType exportTokenType);

  void write (final File outputfile);

  /**
   * gets default configuration for the given exporter
   * @return
   */
  ExportConfiguration getDefaultConfiguration ();

  void openPreview (final File file);
}
