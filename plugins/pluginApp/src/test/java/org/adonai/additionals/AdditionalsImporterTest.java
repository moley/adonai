package org.adonai.additionals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.adonai.model.Additional;
import org.adonai.model.AdditionalType;
import org.adonai.model.Song;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class AdditionalsImporterTest {

  private File originalFile = new File("build/some.mp3");

  private Song getSong () {
    Song song = new Song();
    Additional additional = new Additional();
    additional.setAdditionalType(AdditionalType.AUDIO);
    additional.setLink(originalFile.getAbsolutePath());
    song.getAdditionals().add(additional);
    song.setId(Integer.valueOf("12"));
    song.setTitle("This song");
    return song;
  }

  @Test
  public void getAdditionalFile () {
    File additionalsPath = new File ("build/additionalspath");
    AdditionalsImporter additionalsImporter = new AdditionalsImporter();
    additionalsImporter.setAdditionalsPath(additionalsPath);
    File additionalFile = additionalsImporter.getAdditionalFile(getSong(), getSong().getAdditionals().get(0));
    Assert.assertEquals ("AdditionalFile invalid", new File (additionalsPath, "audio/12.mp3").getAbsolutePath(), additionalFile.getAbsolutePath()  );

  }

  @Test
  public void refreshCacheNoCacheExists () throws IOException {
    originalFile.createNewFile();
    Song song = getSong();
    File additionalsPath = new File ("build/additionalspath");
    AdditionalsImporter additionalsImporter = new AdditionalsImporter();
    additionalsImporter.setAdditionalsPath(additionalsPath);
    File cacheFile = additionalsImporter.getAdditionalFile(song, song.getAdditionals().get(0));
    if (cacheFile.exists())
      cacheFile.delete();
    Assert.assertFalse (cacheFile.exists());
    additionalsImporter.refreshCache(song, song.getAdditionals().get(0), true);
    Assert.assertTrue ("Cachefile " + cacheFile.getAbsolutePath() + " does not exist after refresh", cacheFile.exists());

    Assert.assertEquals ("CacheLink invalid", cacheFile.getAbsolutePath(), song.getAdditionals().get(0).getCacheLink());
  }

  @Test
  public void refreshCacheExistsNoClean () throws IOException, InterruptedException {
    originalFile.createNewFile();
    Song song = getSong();
    File additionalsPath = new File ("build/additionalspath");
    AdditionalsImporter additionalsImporter = new AdditionalsImporter();
    additionalsImporter.setAdditionalsPath(additionalsPath);
    File cacheFile = additionalsImporter.getAdditionalFile(song, song.getAdditionals().get(0));
    if (! cacheFile.exists()) {
      cacheFile.getParentFile().mkdirs();
      cacheFile.createNewFile();
    }
    long lastModified = cacheFile.lastModified();

    Thread.sleep(20);

    Assert.assertTrue (cacheFile.exists());
    additionalsImporter.refreshCache(song, song.getAdditionals().get(0), false);
    Assert.assertTrue ("Cachefile " + cacheFile.getAbsolutePath() + " does not exist after refresh", cacheFile.exists());

    Assert.assertEquals("NoClean must not overwrite", lastModified, cacheFile.lastModified());
  }

  @Test
  public void refreshCacheExistsClean () throws IOException, InterruptedException {
    originalFile.createNewFile();
    Song song = getSong();
    File additionalsPath = new File ("build/additionalspath");
    AdditionalsImporter additionalsImporter = new AdditionalsImporter();
    additionalsImporter.setAdditionalsPath(additionalsPath);
    File cacheFile = additionalsImporter.getAdditionalFile(song, song.getAdditionals().get(0));
    if (! cacheFile.exists()) {
      cacheFile.getParentFile().mkdirs();
      cacheFile.createNewFile();
    }

    FileUtils.write(originalFile, "newContent", Charset.defaultCharset());

    long lastModified = cacheFile.lastModified();

    Thread.sleep(20);

    Assert.assertTrue (cacheFile.exists());
    additionalsImporter.refreshCache(song, song.getAdditionals().get(0), true);
    Assert.assertTrue ("Cachefile " + cacheFile.getAbsolutePath() + " does not exist after refresh", cacheFile.exists());

    Assert.assertNotEquals("Clean must overwrite (" + lastModified + "-" + cacheFile.lastModified() + ")", lastModified, cacheFile.lastModified());
  }

  @Test
  public void removeAdditional () throws IOException {
    originalFile.createNewFile();
    Song song = getSong();
    File additionalsPath = new File ("build/additionalspath");
    AdditionalsImporter additionalsImporter = new AdditionalsImporter();
    additionalsImporter.setAdditionalsPath(additionalsPath);
    File cacheFile = additionalsImporter.getAdditionalFile(song, song.getAdditionals().get(0));
    if (! cacheFile.exists()) {
      cacheFile.getParentFile().mkdirs();
      cacheFile.createNewFile();
    }

    Assert.assertTrue (cacheFile.exists());
    additionalsImporter.removeAdditional(song, song.getAdditionals().get(0));
    Assert.assertFalse ("Cachefile " + cacheFile.getAbsolutePath() + " still exists after removal", cacheFile.exists());

  }
}
