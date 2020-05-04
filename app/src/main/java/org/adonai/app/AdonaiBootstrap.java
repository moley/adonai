/*
 * Copyright (C) 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.adonai.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A boot class that starts the application
 *
 * @author Decebal Suiu
 */
public class AdonaiBootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdonaiBootstrap.class);

    public static void main(String[] args)
        throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LOGGER.info("Starting application in path " + new File("").getAbsolutePath());

        // create the plugin manager
        final PluginManager pluginManager = new DefaultPluginManager() {
          @Override
          protected CompoundPluginDescriptorFinder createPluginDescriptorFinder() {
            return new CompoundPluginDescriptorFinder()
                // Demo is using the Manifest file
                // PropertiesPluginDescriptorFinder is commented out just to avoid error log
                //.add(new PropertiesPluginDescriptorFinder())
                .add(new ManifestPluginDescriptorFinder());
          }
        };
        LOGGER.info("Using development mode: " + pluginManager.isDevelopment());

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
        Method method = corePlugin.getClass().getMethod("execute");
        method.invoke(corePlugin);
    }


}
