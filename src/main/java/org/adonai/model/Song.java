package org.adonai.model;

import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OleyMa on 01.09.16.
 */
public class Song extends AbstractSessionItem implements NamedElement{

  private List<SongPart> songParts = new ArrayList<SongPart>();

  private Integer id;

  private List<Additional> additionals = new ArrayList<Additional>();


  private String currentKey;

  private String originalKey;

  private Integer transposeInfo;

  private SimpleStringProperty titleProperty = new SimpleStringProperty();

  public int getIndex (final SongPart songPart) {
    return songParts.indexOf(songPart);
  }

  public SongPart findSongPartByUUID (final String uuid) {
    for (SongPart next: songParts) {
      if (next.getId().equals(uuid))
        return next;
    }

    return null;
  }

  public SongPart getPreviousSongPart (SongPart songPart) {
    int index = getIndex(songPart);
    return index > 0 ? getSongParts().get(index - 1): null;
  }

  public SongPart getNextSongPart (SongPart songPart) {
    int index = getIndex(songPart);
    return (index < getSongParts().size() - 1)? getSongParts().get(index + 1): null;
  }

  public SongPart getFirstSongPart () {
    return songParts.get(0);
  }

  public SongPart getLastSongPart () {
    return songParts.get(songParts.size() - 1);
  }


  public List<SongPart> getSongParts() {
    return songParts;
  }

  public void setSongParts(List<SongPart> songParts) {
    this.songParts = songParts;
  }

  public String getTitle() {
    return titleProperty.get();
  }

  public void setTitle(String title) {
    titleProperty.set(title);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String toString () {
    String content = "";
    content += id + "-" + getTitle() + "\n";
    for (SongPart next: songParts) {
      content += next.toString();
      content += "\n";
    }

    return content;
  }

  public String getOriginalKey() {
    return originalKey;
  }

  public void setOriginalKey(String originalKey) {
    this.originalKey = originalKey;
  }

  public String getCurrentKey() {
    return currentKey;
  }

  public void setCurrentKey(String currentKey) {
    this.currentKey = currentKey;
  }

  public SimpleStringProperty titleProperty () {
    return titleProperty;
  }

  public Additional findAdditional (final AdditionalType additionalType) {
    for (Additional next: additionals) {
      if (next.getAdditionalType().equals(additionalType))
        return next;
    }
    return null;
  }

  public List<Additional> getAdditionals () {
    return additionals;
  }

  public void setAdditionals (final List<Additional> additionals) {
    this.additionals = additionals;
  }

  public void setAdditional (final Additional additional) {
    for (Additional next: additionals) {
      if (next.getAdditionalType().equals(additional.getAdditionalType())) {
        additionals.remove(next);
        break;
      }
    }

    additionals.add(additional);
  }

  @Override
  public String getName() {
    return getTitle();
  }

  public Integer getTransposeInfo() {
    return transposeInfo;
  }

  public void setTransposeInfo(Integer transposeInfo) {
    this.transposeInfo = transposeInfo;
  }
}
