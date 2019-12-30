package org.adonai.plugin.app;

import org.adonai.api.Application;
import org.adonai.ui.JavaFxApplication;

public class AdonaiApplication implements Application {
  @Override public void run(String[] args) {
    JavaFxApplication.main(args);
  }
}
