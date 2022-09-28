package org.adonai.model;

import java.util.Arrays;
import org.adonai.export.ExportConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

  @Test
  public void setAndGetSongBookes () {
    Configuration configuration = new Configuration();
    configuration.setSongBooks(Arrays.asList(new SongBook(), new SongBook()));
    Assert.assertEquals ("Number of songbooks invalid", 2, configuration.getSongBooks().size());

  }

  @Test
  public void setAndGetSessions () {
    Configuration configuration = new Configuration();
    configuration.setSessions(Arrays.asList(new Session(), new Session()));
    Assert.assertEquals ("Number of sessions invalid", 2, configuration.getSessions().size());
  }

  @Test
  public void setAndGetExtensionPaths () {
    Configuration configuration = new Configuration();
    configuration.setMp3ExtensionPath("1");
    Assert.assertEquals ("Number of extensionpaths invalid", "1", configuration.getMp3ExtensionPath());
  }

  @Test
  public void setAndGetExportConfigurations () {
    Configuration configuration = new Configuration();
    configuration.setExportConfigurations(Arrays.asList(new ExportConfiguration(), new ExportConfiguration()));
    Assert.assertEquals ("Number of exportconfs invalid", 2, configuration.getExportConfigurations().size());
  }

  @Test
  public void setAndGetUsers () {
    Configuration configuration = new Configuration();
    configuration.setUsers(Arrays.asList(new User(), new User()));
    Assert.assertEquals ("Number of users invalid", 2, configuration.getUsers().size());
  }
}
