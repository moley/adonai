package org.adonai.importer;

import java.util.ArrayList;
import java.util.List;
import org.adonai.model.Song;

public class WordImporterItem {

  private String name;

  private int number;

  private List<String> content = new ArrayList<String>();

  private Song song;

  private boolean exists;

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public List<String> getContent() {
    return content;
  }

  public void setContent(List<String> content) {
    this.content = content;
  }

  public Song getSong() {
    return song;
  }


  public void setSong(Song song) {
    this.song = song;
  }

  public boolean isExists() {
    return exists;
  }

  public void setExists(boolean exists) {
    this.exists = exists;
  }
}
