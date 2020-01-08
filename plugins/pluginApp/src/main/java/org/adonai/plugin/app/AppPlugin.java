package org.adonai.plugin.app;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class AppPlugin extends Plugin {

  //private static final Logger logger = LoggerFactory.getLogger(AppPlugin.class);


  public AppPlugin(PluginWrapper wrapper) {
    super(wrapper);
  }

  @Override
  public void start() {
    //logger.info("AppPlugin.start()");
  }

  @Override
  public void stop() {
    //logger.info("AppPlugin.stop()");
  }

  @Override
  public void delete() {
    //logger.info("AppPlugin.delete()");
  }
}
