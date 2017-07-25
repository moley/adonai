package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by OleyMa on 01.09.16.
 */
public class Session {

  private Collection<Integer> songs = new ArrayList<Integer>();
  private StringProperty name = new SimpleStringProperty();


  public Collection<Integer> getSongs() {
    return songs;
  }

  public void setSongs(Collection<Integer> songs) {
    this.songs = songs;
  }

  public String getName() {
    return name.getValue();
  }

  public StringProperty getNameProperty () {
    return name;
  }

  public void setName(String name) {
    this.name.setValue(name);
  }

  public String toString () {
    return getName() + "(" + songs.size() + " songs)";
  }
}
