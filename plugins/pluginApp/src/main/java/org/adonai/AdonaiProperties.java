package org.adonai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AdonaiProperties {

  private Properties properties = new Properties();

  private File propertiesFile;

  public final static String PROPERTY_CURRENT_TENANT = "adonai.tenant";

  public AdonaiProperties () {
    propertiesFile = new File (System.getProperty("user.home") + "/.adonai/adonai.properties");

    properties.setProperty(PROPERTY_CURRENT_TENANT, "default");
    if (propertiesFile.exists()) {
      try {
        properties.load(new FileReader(propertiesFile));
      } catch (IOException e) {

      }
    }

  }

  public String getProperty (final String key) {
    return properties.getProperty(key);
  }

  public void setProperty (final String key, final String value) {
    properties.setProperty(key, value);
  }

  public void save () {
    propertiesFile.getParentFile().mkdirs();
    try {
      properties.store(new FileOutputStream(propertiesFile), "Handled by adonai");
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

  }

  public String getCurrentTenant () {
    return properties.getProperty(PROPERTY_CURRENT_TENANT);
  }

  public void setCurrentTenant (final String newTenant) {
    properties.setProperty(PROPERTY_CURRENT_TENANT, newTenant);
  }
}
