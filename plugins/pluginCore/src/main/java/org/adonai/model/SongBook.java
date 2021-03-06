package org.adonai.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OleyMa on 01.09.16.
 */
@XmlRootElement
public class SongBook extends WithAdditionals {

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

  public String toString () {
    String asString = "Songbook " + System.identityHashCode(this) + "\n";
    for (Song next: songs) {
      asString += " - " + next.getId() + "-" + next.getTitle() + "(" + System.identityHashCode(next) + ")\n";
    }
    return asString;
  }

}
