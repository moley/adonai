package org.adonai.online;

import java.io.File;

public interface OnlineAdapter {

  void upload (final File configFile);

  File download (final File folder);
}
