package org.adonai.model;

import java.io.File;
import java.io.IOException;
import org.adonai.AdonaiProperties;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by OleyMa on 12.09.16.
 */
public class ConfigurationServiceTest {

  private File tmpFile = new File("build/testmodel.xml");

  private AdonaiProperties adonaiProperties = new AdonaiProperties();

  @Before@After
  public void cleanup () {
    if (tmpFile.exists())
      tmpFile.delete();
  }

  @Test
  public void hasNotChanged () {
    ConfigurationService configurationService = new ConfigurationService();
    configurationService.setConfigFile(tmpFile);
    configurationService.set(adonaiProperties.getCurrentTenant(), createDefaultConfiguration());
    Assert.assertFalse (configurationService.hasChanged());

  }

  @Test
  public void hasChanged () {
    ConfigurationService configurationService = new ConfigurationService();
    configurationService.setConfigFile(tmpFile);
    configurationService.set(adonaiProperties.getCurrentTenant(), createDefaultConfiguration());
    Configuration configuration = configurationService.get(adonaiProperties.getCurrentTenant());
    configuration.getSongBooks().get(0).getSongs().get(0).setTitle("Different song");
    Assert.assertTrue (configurationService.hasChanged());
  }

  @Test
  public void hasChangedAndSaved () {
    ConfigurationService configurationService = new ConfigurationService();
    configurationService.setConfigFile(tmpFile);
    configurationService.set(adonaiProperties.getCurrentTenant(), createDefaultConfiguration());
    Configuration configuration = configurationService.get(adonaiProperties.getCurrentTenant());
    configuration.getSongBooks().get(0).getSongs().get(0).setTitle("Different song");
    configurationService.set(adonaiProperties.getCurrentTenant(), configuration);
    Assert.assertFalse (configurationService.hasChanged());
  }

  private Configuration createDefaultConfiguration () {
    Configuration configuration = new Configuration();
    Song song1 = new Song();
    song1.setTitle("Song 1");
    song1.setId(1);
    SongPart part = new SongPart();
    part.setSongPartType(SongPartType.VERS);
    part.getLines().add(new Line("First line of song 1"));
    part.getLines().add(new Line("Second line of song 1"));
    song1.getSongParts().add(part);
    SongBook songBook = new SongBook();
    songBook.getSongs().add(song1);
    configuration.getSongBooks().add(songBook);
    return configuration;

  }


  @Test
  public void save () throws IOException {

    ConfigurationService configurationService = new ConfigurationService();
    configurationService.setConfigFile(tmpFile);
    Configuration configuration = configurationService.get(adonaiProperties.getCurrentTenant());

    Song song1 = new Song();
    song1.setTitle("Song 1");
    song1.setId(1);
    SongPart part = new SongPart();
    part.setSongPartType(SongPartType.VERS);
    part.getLines().add(new Line("First line of song 1"));
    part.getLines().add(new Line("Second line of song 1"));
    song1.getSongParts().add(part);

    Song song2 = new Song();
    song2.setId(2);
    song2.setTitle("Song 2");
    SongPart part2 = new SongPart();
    part2.setSongPartType(SongPartType.VERS);
    part2.getLines().add(new Line("First line of song 2"));
    part2.getLines().add(new Line("Second line of song 2"));
    song2.getSongParts().add(part2);
    SongPart part2Ref = new SongPart();
    part2Ref.setReferencedSongPart(part2.getId());
    song2.getSongParts().add(part2Ref);


    SongBook songBook = new SongBook();
    songBook.getSongs().add(song1);
    songBook.getSongs().add(song2);
    configuration.getSongBooks().add(songBook);

    Session session1 = new Session();
    session1.getSongs().add(song1.getId());

    Session session2 = new Session();
    session2.getSongs().add(song1.getId());
    session2.getSongs().add(song2.getId());

    configuration.getSessions().add(session1);
    configuration.getSessions().add(session2);

    configurationService.save(adonaiProperties.getCurrentTenant());

    String fromText = FileUtils.readFileToString(tmpFile, (String)null);
    Assert.assertNotNull("configuration content is null", fromText);




  }


}
