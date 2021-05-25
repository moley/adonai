package org.adonai.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.adonai.ApplicationEnvironment;
import org.adonai.export.DefaultExportConfigurationCreator;
import org.adonai.fx.Consts;
import org.adonai.services.SongRepairer;
import org.apache.commons.io.FileUtils;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TenantModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(TenantModel.class);

  private Configuration currentConfiguration;

  private String tenant;

  private String lastSavedConfigurationAsString;

  private File configFile;

  private final ApplicationEnvironment applicationEnvironment;

  public TenantModel(final ApplicationEnvironment applicationEnvironment, final File fromFile) {
    this.applicationEnvironment = applicationEnvironment;
    setConfigFile(fromFile);
    load();
    this.tenant = "default";
  }

  public TenantModel (final ApplicationEnvironment applicationEnvironment, final String tenant) {
    this.tenant = tenant;
    this.applicationEnvironment = applicationEnvironment;
  }

  public File getTenantPath () {
    File adonaiHome = Consts.getAdonaiHome();
    File tenantPath = new File(adonaiHome, "tenant_" + tenant);
    return tenantPath;
  }

  public File getConfigFile () {
    if (configFile == null) {
      File tenantPath = getTenantPath();
      return new File(tenantPath, "config.xml");
    }
    else
      return configFile;
  }

  public String getTenant() {
    return tenant;
  }

  public void setTenant(String tenant) {
    this.tenant = tenant;
  }

  public String getLastSavedConfigurationAsString() {
    return lastSavedConfigurationAsString;
  }

  public void setLastSavedConfigurationAsString(String lastSavedConfigurationAsString) {
    this.lastSavedConfigurationAsString = lastSavedConfigurationAsString;
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
      boolean changed =  ! currentString.equals(lastSavedConfigurationAsString);
      if (changed) {

        DiffMatchPatch dmp = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diff = dmp.diffMain(lastSavedConfigurationAsString, currentString, false);
        LOGGER.info("Model " + tenant + " has changed:");
        for (DiffMatchPatch.Diff nextDiff: diff) {
          if (! nextDiff.operation.equals(DiffMatchPatch.Operation.EQUAL))
            LOGGER.info("-" + nextDiff.operation.name() + "-" + nextDiff.text);

        }

      }
      return changed;
    } catch (JAXBException e) {
      throw new IllegalStateException(e);
    }
  }

  public void save () {
    LOGGER.info("Save " + tenant + " in " + getConfigFile().getAbsolutePath());
    set(get());
  }

  public void load () {
    LOGGER.info("Load " + tenant + " from " + getConfigFile().getAbsolutePath());

    JAXBContext jc = null;
    File configFile = getConfigFile();

    try {
      Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
      System.setProperty("javax.xml.bind.JAXBContextFactory", "com.sun.xml.bind.v2.ContextFactory");
      jc = JAXBContext.newInstance(Configuration.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();

      if (configFile.exists()) {
        currentConfiguration = (Configuration) unmarshaller.unmarshal(configFile);
        LOGGER.info("Configuration " + System.identityHashCode(currentConfiguration) + " loaded from " + configFile.getAbsolutePath());
      }
      else {
        currentConfiguration = new Configuration();
        LOGGER.info("Created new configuration " + System.identityHashCode(currentConfiguration) + " because " + configFile.getAbsolutePath() + " does not exist");
      }

      if (applicationEnvironment.isCreateDefaultExportConfigurations()) {
        DefaultExportConfigurationCreator defaultExportConfigurationCreator = new DefaultExportConfigurationCreator();
        defaultExportConfigurationCreator.createDefaultExportConfigurations(applicationEnvironment, currentConfiguration);
      }

      //Automatic migrations
      SongRepairer songRepairer = new SongRepairer();
      for (SongBook nextSongbook: currentConfiguration.getSongBooks()) {
        for (Song nextSong: nextSongbook.getSongs()) {
          try {
            songRepairer.repairSong(nextSong);
          } catch (Exception e) {
            throw new RuntimeException("Error repairing song " + nextSong.getTitle() + " of tenant " + tenant, e);
          }
        }
      }
      currentConfiguration.setTenant(tenant);

      Marshaller marshaller = jc.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      StringWriter stringWriter = new StringWriter();
      marshaller.marshal(currentConfiguration, stringWriter);
      lastSavedConfigurationAsString = stringWriter.toString();



    } catch (JAXBException e) {
      throw new IllegalStateException("JAXBException reading " + configFile.getAbsolutePath(), e);
    }

  }




  public Configuration get() {
    return currentConfiguration;
  }

  public void set(Configuration configuration) {
    JAXBContext jc = null;
    File configFile = getConfigFile();

    try {
      jc = JAXBContext.newInstance(Configuration.class);
      Marshaller marshaller = jc.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      LOGGER.info("Save configuration " + System.identityHashCode(currentConfiguration) + " to " + configFile.getAbsolutePath());

      if (! configFile.getParentFile().exists())
        configFile.getParentFile().mkdirs();

      if (configFile.exists()) {
        File savedConfigFile = new File (configFile.getParentFile(), "." + configFile.getName() + new Date().toString());
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

  public void setConfigFile(File configFile) {
    this.configFile = configFile;
  }

  public String toString () {
    String asString = "";
    asString += "  File: " + getConfigFile().getAbsolutePath() + "\n";
    asString += "  Configuration: " + currentConfiguration.toString() + "\n";
    return asString;

  }
}
