package org.adonai.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.pf4j.PropertiesPluginDescriptorFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args)
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    LOGGER.info("Starting application in path " + new File("").getAbsolutePath());

    if (new File ("../build").exists()) {
      System.setProperty("pf4j.pluginsDir", new File ("../build/plugins").getAbsolutePath());
    }

    // create the plugin manager
    final PluginManager pluginManager = new DefaultPluginManager() {
      @Override
      protected CompoundPluginDescriptorFinder createPluginDescriptorFinder() {
        return new CompoundPluginDescriptorFinder()
            // Demo is using the Manifest file
            // PropertiesPluginDescriptorFinder is commented out just to avoid error log
            .add(new PropertiesPluginDescriptorFinder())
            .add(new ManifestPluginDescriptorFinder());
      }

      @Override
      protected PluginWrapper loadPluginFromPath(Path pluginPath) {
        LOGGER.info("Load plugin from path " + pluginPath.toString());
        PluginWrapper pluginWrapper = super.loadPluginFromPath(pluginPath);
        return pluginWrapper;
      }
    };
    LOGGER.info("Using development mode: " + pluginManager.isDevelopment());
    LOGGER.info("Using pluginsroot: " + pluginManager.getPluginsRoot().toString());

    // load the plugins
    pluginManager.loadPlugins();

    LOGGER.info("Found " + pluginManager.getPlugins().size() + " plugins");
    for (PluginWrapper pluginWrapper: pluginManager.getPlugins()) {
      LOGGER.info("Loaded " + pluginWrapper.getPluginId());
      LOGGER.info(pluginWrapper.getPluginPath().toString());
      URL[] urls = ((PluginClassLoader)pluginWrapper.getPluginClassLoader()).getURLs();
      LOGGER.info(Arrays.toString(urls));

      String pluginId = pluginWrapper.getDescriptor().getPluginId();
      LOGGER.info(String.format("Extensions added by plugin '%s':", pluginId));
      Set<String> extensionClassNames = pluginManager.getExtensionClassNames(pluginId);
      for (String extension : extensionClassNames) {
        LOGGER.info("   " + extension);
      }
    }

    LOGGER.info("Plugindirectory: ");
    LOGGER.info("\t" + System.getProperty("pf4j.pluginsDir") + "\n");


    File plugins = new File ("plugins");
    if (! plugins.exists())
      plugins.mkdirs();

    // create update manager
    /** TODO
     UpdateRepository updateRepository = new DefaultUpdateRepository("default", new URL("https://raw.githubusercontent.com/moley/adonai_plugin/master/"));
     UpdateManager updateManager = new UpdateManager(pluginManager, Arrays.asList(updateRepository));

     // >> keep system up-to-date <<
     boolean systemUpToDate = true;

     // check for updates
     if (updateManager.hasUpdates()) {
     List<PluginInfo> updates = updateManager.getUpdates();
     LOGGER.debug("Found {} updates", updates.size());
     for (PluginInfo plugin : updates) {
     LOGGER.debug("Found update for plugin '{}'", plugin.id);
     PluginInfo.PluginRelease lastRelease = updateManager.getLastPluginRelease(plugin.id);
     String lastVersion = lastRelease.version;
     String installedVersion = pluginManager.getPlugin(plugin.id).getDescriptor().getVersion();
     LOGGER.debug("Update plugin '{}' from version {} to version {}", plugin.id, installedVersion, lastVersion);
     boolean updated = updateManager.updatePlugin(plugin.id, lastVersion);
     if (updated) {
     LOGGER.debug("Updated plugin '{}'", plugin.id);
     } else {
     LOGGER.error("Cannot update plugin '{}'", plugin.id);
     systemUpToDate = false;
     }
     }
     } else {
     LOGGER.debug("No updates found");
     }

     // check for available (new) plugins
     if (updateManager.hasAvailablePlugins()) {
     List<PluginInfo> availablePlugins = updateManager.getAvailablePlugins();
     LOGGER.debug("Found {} available plugins", availablePlugins.size());
     for (PluginInfo plugin : availablePlugins) {
     LOGGER.debug("Found available plugin '{}'", plugin.id);
     PluginInfo.PluginRelease lastRelease = updateManager.getLastPluginRelease(plugin.id);
     String lastVersion = lastRelease.version;
     LOGGER.debug("Install plugin '{}' with version {}", plugin.id, lastVersion);
     boolean installed = updateManager.installPlugin(plugin.id, lastVersion);
     if (installed) {
     LOGGER.debug("Installed plugin '{}'", plugin.id);
     } else {
     LOGGER.error("Cannot install plugin '{}'", plugin.id);
     systemUpToDate = false;
     }
     }
     } else {
     LOGGER.debug("No available plugins found");
     }

     if (systemUpToDate) {
     LOGGER.debug("System up-to-date");
     }
     **/

    // enable a disabled plugin
    //        pluginManager.enablePlugin("welcome-plugin");

    // start (active/resolved) the plugins

    pluginManager.startPlugins();


    //start application
    Object corePlugin = pluginManager.getPlugin("adonai-core-plugin").getPlugin();
    Method method = corePlugin.getClass().getMethod("executeApplication");
    method.invoke(corePlugin);
  }
}
