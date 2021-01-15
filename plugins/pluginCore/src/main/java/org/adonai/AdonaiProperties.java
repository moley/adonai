package org.adonai;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.adonai.fx.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdonaiProperties {

  private static final Logger LOGGER = LoggerFactory.getLogger(AdonaiProperties.class);





  private static Properties properties;

  private File propertiesFile;

  public final static String PROPERTY_CURRENT_TENANT = "adonai.tenant";
  public final static String PROPERTY_DROPBOX_ACCESSTOKEN = "adonai.dropbox.accesstoken";

  public static void dispose () {
    properties = null;
  }

  public AdonaiProperties () {
    if (properties == null) {
      properties = new Properties();

      File adonaiHome = Consts.getAdonaiHome();
      propertiesFile = new File(adonaiHome, "adonai.properties");
      LOGGER.info("Load adonai properties from " + propertiesFile.getAbsolutePath());

      properties.setProperty(PROPERTY_CURRENT_TENANT, "default");
      if (propertiesFile.exists()) {
        try {
          properties.load(new FileReader(propertiesFile));
        } catch (IOException e) {

        }
      }
    }

  }

  public String getProperty (final String key, final String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  public boolean getPropertyBoolean (final String key, final boolean defaultValue) {
    String prop =  properties.getProperty(key);
    if (prop == null)
      return defaultValue;
    else
      return Boolean.valueOf(prop);
  }

  public String getProperty (final String key) {
    return properties.getProperty(key);
  }

  public void setProperty (final String key, final String value) {
    properties.setProperty(key, value);
  }

  private void save () {
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
    save();
  }

  public String getDropboxAccessToken () {
    return properties.getProperty(PROPERTY_DROPBOX_ACCESSTOKEN);
  }

  public void setDropboxAccessToken (final String accessToken) {
    properties.setProperty(PROPERTY_DROPBOX_ACCESSTOKEN, accessToken);
    save();
  }

  public File getPropertiesFile() {
    return propertiesFile;
  }
}
