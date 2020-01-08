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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.adonai.api.Application;
import org.apache.commons.lang3.StringUtils;
import org.pf4j.CompoundPluginDescriptorFinder;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.pf4j.update.DefaultUpdateRepository;
import org.pf4j.update.PluginInfo;
import org.pf4j.update.UpdateManager;
import org.pf4j.update.UpdateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A boot class that starts the application
 *
 * @author Decebal Suiu
 */
public class Boot {
    private static final Logger logger = LoggerFactory.getLogger(Boot.class);

    public static void main(String[] args) throws MalformedURLException {
        // print logo
        printLogo();

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

        logger.info("Development mode: " + pluginManager.isDevelopment());

        // load the plugins
        pluginManager.loadPlugins();

        logger.info("Found " + pluginManager.getPlugins().size() + " plugins");
        for (PluginWrapper pluginWrapper: pluginManager.getPlugins()) {
            logger.info("Loaded " + pluginWrapper.getPluginId());
            logger.info(pluginWrapper.getPluginPath().toString());
            URL[] urls = ((PluginClassLoader)pluginWrapper.getPluginClassLoader()).getURLs();
            logger.info(Arrays.toString(urls));

            String pluginId = pluginWrapper.getDescriptor().getPluginId();
            logger.info(String.format("Extensions added by plugin '%s':", pluginId));
            Set<String> extensionClassNames = pluginManager.getExtensionClassNames(pluginId);
             for (String extension : extensionClassNames) {
                 logger.info("   " + extension);
             }
        }

        logger.info("Plugindirectory: ");
        logger.info("\t" + System.getProperty("pf4j.pluginsDir") + "\n");


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
            logger.debug("Found {} updates", updates.size());
            for (PluginInfo plugin : updates) {
                logger.debug("Found update for plugin '{}'", plugin.id);
                PluginInfo.PluginRelease lastRelease = updateManager.getLastPluginRelease(plugin.id);
                String lastVersion = lastRelease.version;
                String installedVersion = pluginManager.getPlugin(plugin.id).getDescriptor().getVersion();
                logger.debug("Update plugin '{}' from version {} to version {}", plugin.id, installedVersion, lastVersion);
                boolean updated = updateManager.updatePlugin(plugin.id, lastVersion);
                if (updated) {
                    logger.debug("Updated plugin '{}'", plugin.id);
                } else {
                    logger.error("Cannot update plugin '{}'", plugin.id);
                    systemUpToDate = false;
                }
            }
        } else {
            logger.debug("No updates found");
        }

        // check for available (new) plugins
        if (updateManager.hasAvailablePlugins()) {
            List<PluginInfo> availablePlugins = updateManager.getAvailablePlugins();
            logger.debug("Found {} available plugins", availablePlugins.size());
            for (PluginInfo plugin : availablePlugins) {
                logger.debug("Found available plugin '{}'", plugin.id);
                PluginInfo.PluginRelease lastRelease = updateManager.getLastPluginRelease(plugin.id);
                String lastVersion = lastRelease.version;
                logger.debug("Install plugin '{}' with version {}", plugin.id, lastVersion);
                boolean installed = updateManager.installPlugin(plugin.id, lastVersion);
                if (installed) {
                    logger.debug("Installed plugin '{}'", plugin.id);
                } else {
                    logger.error("Cannot install plugin '{}'", plugin.id);
                    systemUpToDate = false;
                }
            }
        } else {
            logger.debug("No available plugins found");
        }

        if (systemUpToDate) {
            logger.debug("System up-to-date");
        }
         **/

        // enable a disabled plugin
//        pluginManager.enablePlugin("welcome-plugin");

        // start (active/resolved) the plugins

        pluginManager.startPlugins();


        // retrieves the extensions for Greeting extension point
        List<Application> applications = pluginManager.getExtensions(Application.class);
        if (applications.size() != 1) {
            throw new IllegalStateException("Not found exactly one application extension point, but " + applications);
        }
        applications.get(0).run(args);

        // // print extensions from classpath (non plugin)
        // logger.info(String.format("Extensions added by classpath:"));
        // Set<String> extensionClassNames = pluginManager.getExtensionClassNames(null);
        // for (String extension : extensionClassNames) {
        //     logger.info("   " + extension);
        // }

        // print extensions for each started plugin
        List<PluginWrapper> startedPlugins = pluginManager.getStartedPlugins();
        for (PluginWrapper plugin : startedPlugins) {

        }

        // stop the plugins
        pluginManager.stopPlugins();
        /*
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                pluginManager.stopPlugins();
            }

        });
        */
    }

    private static void printLogo() {
        logger.info(StringUtils.repeat("#", 40));
        logger.info(StringUtils.center("ADONAI", 40));
        logger.info(StringUtils.repeat("#", 40));
    }

}
