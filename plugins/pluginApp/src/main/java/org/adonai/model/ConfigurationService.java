package org.adonai.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.adonai.export.DefaultExportConfigurationCreator;
import org.adonai.services.SongRepairer;
import org.adonai.ui.Consts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by OleyMa on 03.09.16.
 */
public class ConfigurationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationService.class);



  private File configFile;


  private Configuration currentConfiguration;

  private String lastSavedConfigurationAsString;


  public void save (String tenant) {
    set(tenant, get(tenant));
  }

  public void setConfigFile (final File configFile) {
    this.configFile = configFile;
  }

  public File getConfigFile (String tenant){
    if (configFile == null) {
      File tenantPath = new File (Consts.getAdonaiHome(), "tenant_" + tenant);
      configFile = new File(tenantPath, "config.xml");
      LOGGER.info("set configFile (" + configFile.getAbsolutePath() + ")");
    }
    return configFile;

  }



  public boolean hasChanged () {
    JAXBContext jc = null;
    try {

      jc = JAXBContext.newInstance(Configuration.class);
      Marshaller marshaller = jc.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      StringWriter stringWriter = new StringWriter();
      marshaller.marshal(currentConfiguration, stringWriter);
      String currentString = stringWriter.toString();
      return ! currentString.equals(lastSavedConfigurationAsString);
    } catch (JAXBException e) {
      throw new IllegalStateException(e);
    }
  }

  public Configuration get(String tenant) {
    if (tenant == null)
      throw new IllegalArgumentException("Parameter 'tenant' must not be null");

    JAXBContext jc = null;
    try {
      jc = JAXBContext.newInstance(Configuration.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();

      File configFile = getConfigFile(tenant);
      //unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
      if (configFile.exists()) {
        currentConfiguration = (Configuration) unmarshaller.unmarshal(configFile);
        currentConfiguration.setLog("Loaded from " + configFile.getAbsolutePath());
        LOGGER.info("Configuration " + System.identityHashCode(currentConfiguration) + " loaded from " + configFile.getAbsolutePath());
      }
      else {
        currentConfiguration = new Configuration();
        LOGGER.info("Created new configuration " + System.identityHashCode(currentConfiguration) + " because " + configFile.getAbsolutePath() + " does not exist");
      }

      DefaultExportConfigurationCreator defaultExportConfigurationCreator = new DefaultExportConfigurationCreator();
      defaultExportConfigurationCreator.createDefaultExportConfigurations(currentConfiguration);

      SongRepairer songRepairer = new SongRepairer();
      for (SongBook nextSongbook: currentConfiguration.getSongBooks()) {
        for (Song nextSong: nextSongbook.getSongs()) {
          songRepairer.repairSong(nextSong);
        }
      }

      Marshaller marshaller = jc.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      StringWriter stringWriter = new StringWriter();
      marshaller.marshal(currentConfiguration, stringWriter);
      lastSavedConfigurationAsString = stringWriter.toString();

    } catch (JAXBException e) {
      throw new IllegalStateException("JAXBException reading " + configFile.getAbsolutePath(), e);
    }

    return currentConfiguration;

  }

  public void set(String tenant, Configuration configuration) {
    JAXBContext jc = null;
    try {
      jc = JAXBContext.newInstance(Configuration.class);
      Marshaller marshaller = jc.createMarshaller();
      //marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
//      marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      LOGGER.info("Save configuration " + System.identityHashCode(currentConfiguration) + " to " + configFile.getAbsolutePath());

      File configFile = getConfigFile(tenant);

      if (! configFile.getParentFile().exists())
        configFile.getParentFile().mkdirs();

      if (configFile.exists()) {
        File savedConfigFile = new File (getConfigFile(tenant).getParentFile(), configFile.getName() + new Date().toString());
        FileUtils.copyFile(configFile, savedConfigFile);

      }

      marshaller.marshal(configuration, configFile);
      StringWriter stringWriter = new StringWriter();
      marshaller.marshal(configuration, stringWriter);
      lastSavedConfigurationAsString = stringWriter.toString();
      currentConfiguration = configuration;
    } catch (JAXBException | IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public void close () {
    currentConfiguration = null;
  }


}
