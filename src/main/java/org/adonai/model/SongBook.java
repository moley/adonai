package org.adonai.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OleyMa on 01.09.16.
 */
@XmlRootElement
public class SongBook {

  private File file;

  private List<Song> songs = new ArrayList<Song>();

  public List<Song> getSongs() {
    return songs;
  }

  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }

  public Song findSong(Integer id) {
    for (Song next: songs) {
      if (next.getId().equals(id))
        return next;
    }
    return null;
  }

  public Song findSongByName(String name) {
    for (Song next: songs) {
      if (next.getName().equalsIgnoreCase(name))
        return next;
    }
    return null;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }
}
