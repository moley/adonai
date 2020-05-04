package org.adonai.plugin.publish;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishPlugin extends Plugin {

  private static final Logger log = LoggerFactory.getLogger(PublishPlugin.class);


  public PublishPlugin(PluginWrapper wrapper) {
    super(wrapper);
  }

  @Override
  public void start() {
    log.info("PublishPlugin.start()");
  }

  @Override
  public void stop() {
    log.info("PublishPlugin.stop()");
  }

  @Override
  public void delete() {
    log.info("PublishPlugin.delete()");
  }
}