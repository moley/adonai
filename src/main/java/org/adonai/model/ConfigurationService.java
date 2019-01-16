package org.adonai.model;

import org.adonai.export.DefaultExportConfigurationCreator;
import org.adonai.ui.Consts;
import org.adonai.ui.editor.SongRepairer;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by OleyMa on 03.09.16.
 */
public class ConfigurationService {

  private static final Logger LOGGER = Logger.getLogger(ConfigurationService.class.getName());



  private File configFile;


  private static Configuration currentConfiguration;

  public void save () {
    set(get());
  }

  public void setConfigFile (final File configFile) {
    this.configFile = configFile;
  }

  public File getConfigFile ( ){
    if (configFile == null) {

      configFile = new File(Consts.LEGUAN_HOME, "config.xml");
      LOGGER.info("configFile = " + configFile.getAbsolutePath());
    }
    return configFile;

  }

  public Configuration newInstance () {
    currentConfiguration = new Configuration();
    return currentConfiguration;
  }

  public Configuration get() {
    if (currentConfiguration != null)
      return currentConfiguration;

    JAXBContext jc = null;
    try {
      jc = JAXBContext.newInstance(Configuration.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();

      File configFile = getConfigFile();
      //unmarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
      if (configFile.exists())
        currentConfiguration = (Configuration) unmarshaller.unmarshal(configFile);
      else
        currentConfiguration =  new Configuration();

      DefaultExportConfigurationCreator defaultExportConfigurationCreator = new DefaultExportConfigurationCreator();
      defaultExportConfigurationCreator.createDefaultExportConfigurations(currentConfiguration);

      SongRepairer songRepairer = new SongRepairer();
      for (SongBook nextSongbook: currentConfiguration.getSongBooks()) {
        for (Song nextSong: nextSongbook.getSongs()) {
          songRepairer.repairSong(nextSong);
        }
      }

    } catch (JAXBException e) {
      throw new IllegalStateException(e);
    }

    return currentConfiguration;

  }

  public void set(Configuration configuration) {
    JAXBContext jc = null;
    try {
      jc = JAXBContext.newInstance(Configuration.class);
      Marshaller marshaller = jc.createMarshaller();
      //marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
//      marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      LOGGER.info("Save configuration " + getConfigFile().getAbsolutePath());

      if (! getConfigFile().getParentFile().exists())
        getConfigFile().getParentFile().mkdirs();

      if (getConfigFile().exists()) {
        File savedConfigFile = new File (getConfigFile().getParentFile(), getConfigFile().getName() + new Date().toString());
        FileUtils.copyFile(getConfigFile(), savedConfigFile);

      }

      marshaller.marshal(configuration, getConfigFile());
    } catch (JAXBException | IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public void close () {
    currentConfiguration = null;
  }


}
