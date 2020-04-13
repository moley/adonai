package org.adonai.plugin.publish;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class PublishPlugin extends Plugin {

  //private static final Logger logger = LoggerFactory.getLogger(AppPlugin.class);


  public PublishPlugin(PluginWrapper wrapper) {
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