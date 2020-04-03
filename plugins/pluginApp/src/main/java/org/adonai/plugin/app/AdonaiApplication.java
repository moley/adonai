package org.adonai.plugin.app;

import java.io.File;
import org.adonai.api.Application;
import org.adonai.ApplicationEnvironment;
import org.adonai.ui.JavaFxApplication;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension(ordinal=1)
public class AdonaiApplication implements Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdonaiApplication.class);

  @Override public void run(ApplicationEnvironment applicationEnvironment, String[] args) {
    JavaFxApplication.setApplicationEnvironment(applicationEnvironment);
    JavaFxApplication.main(args);
  }

  public static void main(String[] args) {
    LOGGER.info("Starting application in path " + new File("").getAbsolutePath());
    AdonaiApplication adonaiApplication = new AdonaiApplication();
    adonaiApplication.run(null, args);
  }

}
