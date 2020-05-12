package org.adonai.plugin.core;

import org.adonai.ApplicationEnvironment;
import org.adonai.CorePluginIF;
import org.adonai.app.JavaFxApplication;
import org.adonai.fx.FxApplication;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorePlugin extends Plugin implements CorePluginIF {

  private static final Logger logger = LoggerFactory.getLogger(CorePlugin.class);


  public CorePlugin(PluginWrapper wrapper) {
    super(wrapper);
  }

  @Override
  public void start() {
    logger.info("CorePlugin.start()");
  }

  public void execute () {

    JavaFxApplication.setApplicationEnvironment(new ApplicationEnvironment(getWrapper().getPluginManager()));
    JavaFxApplication.main(new String [0]);
  }

  public void executeApplication () {
    FxApplication.setApplicationEnvironment(new ApplicationEnvironment(getWrapper().getPluginManager()));
    FxApplication.main(new String [0]);
  }

  @Override
  public void stop() {
    logger.info("CorePlugin.stop()");
  }

  @Override
  public void delete() {
    logger.info("CorePlugin.delete()");
  }
}
