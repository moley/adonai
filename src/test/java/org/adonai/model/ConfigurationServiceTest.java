package org.adonai.model;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by OleyMa on 12.09.16.
 */
public class ConfigurationServiceTest {

  private File tmpFile = new File("build/testmodel.xml");

  @Before@After
  public void cleanup () {
    if (tmpFile.exists())
      tmpFile.delete();
  }


  @Test
  public void save () throws IOException {

    ConfigurationService configurationService = new ConfigurationService();
    configurationService.setConfigFile(tmpFile);
    Configuration configuration = configurationService.get();

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

    configurationService.save();

    String fromText = FileUtils.readFileToString(tmpFile, (String)null);


    System.out.println (fromText);

  }


}
