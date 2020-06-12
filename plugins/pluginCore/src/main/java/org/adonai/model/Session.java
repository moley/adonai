package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OleyMa on 01.09.16.
 */
public class Session extends WithAdditionals implements NamedElement {

  private List<Integer> songs = new ArrayList<Integer>();
  private StringProperty name = new SimpleStringProperty();


  public List<Integer> getSongs() {
    return songs;
  }

  public void setSongs(List<Integer> songs) {
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

  @Override public String toString() {
    return "Session{" + "songs=" + songs + ", name=" + name + '}';
  }
}
