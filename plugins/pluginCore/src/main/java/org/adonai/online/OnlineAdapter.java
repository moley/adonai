package org.adonai.online;

import java.io.File;

public interface OnlineAdapter {

  String upload (final File configFile, final String path, final String credentials);

  File download (final File folder, final String credentials);
}
