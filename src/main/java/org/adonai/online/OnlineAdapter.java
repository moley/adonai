package org.adonai.online;

import java.io.File;

public interface OnlineAdapter {

  void upload (final File basePath, final String path, final String credentials);

  File download (final File basePath, final String path, final String credentials);
}
