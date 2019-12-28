package org.adonai.online;

import java.io.File;

public interface OnlineAdapter {

  String upload (final File configFile, final String path);

  File download (final File folder);
}
