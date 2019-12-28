package org.adonai;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AdonaiProperties {

  private Properties properties = new Properties();

  public AdonaiProperties () {
    File adonaiProperties = new File (System.getProperty("user.home") + "/.adonai/adonai.properties");
    if (adonaiProperties.exists()) {
      try {
        properties.load(new FileReader(adonaiProperties));
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
}
