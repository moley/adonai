package org.adonai.plugin.app;

import org.adonai.api.Application;
import org.adonai.ui.JavaFxApplication;
import org.pf4j.Extension;

@Extension(ordinal=1)
public class AdonaiApplication implements Application {
  @Override public void run(String[] args) {
    JavaFxApplication.main(args);
  }
}
