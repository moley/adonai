package org.adonai;

import java.util.List;
import org.pf4j.PluginManager;

/**
 * wraps environment, which is necessary across the application
 */
public class ApplicationEnvironment {

  /**
   * plugin manager
   */
  private final PluginManager pluginManager;

  /**
   * constructor
   * @param pluginManager  plugin manager
   */
  public ApplicationEnvironment(final PluginManager pluginManager) {
    this.pluginManager = pluginManager;
  }

  /**
   * getter
   * @return plugin manager
   */
  public PluginManager getPluginManager() {
    return pluginManager;
  }

  /**
   * getter
   *
   * @param type type
   *
   * @return extensions of a certain type
   */
  public <T> List<T> getExtensions (Class<T> type) {
    return pluginManager.getExtensions(type);
  }



}
