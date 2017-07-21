package org.adonai.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by OleyMa on 01.09.16.
 */
public class Session {

  private Collection<Integer> songs = new ArrayList<Integer>();
  private String name;


  public Collection<Integer> getSongs() {
    return songs;
  }

  public void setSongs(Collection<Integer> songs) {
    this.songs = songs;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String toString () {
    return name + "(" + songs.size() + " songs)";
  }
}
