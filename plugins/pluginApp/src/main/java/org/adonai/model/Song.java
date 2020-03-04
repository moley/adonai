package org.adonai.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * Created by OleyMa on 01.09.16.
 */
public class Song extends AbstractSessionItem implements NamedElement{

  private List<SongPart> parts = new ArrayList<SongPart>();

  private Integer id;

  private List<Additional> additionals = new ArrayList<Additional>();

  private List<SongStructItem> structItems = new ArrayList<SongStructItem>();

  private Status status;

  private String currentKey;

  private String originalKey;

  private User leadVoice;

  private SimpleStringProperty presetProperty = new SimpleStringProperty();

  private SimpleStringProperty titleProperty = new SimpleStringProperty();

  private SimpleObjectProperty<Integer> speedProperty = new SimpleObjectProperty<Integer>();

  public int getIndex (final SongPart songPart) {
    return parts.indexOf(songPart);
  }

  public int getIndex (final SongStructItem songStructItem) {
    return structItems.indexOf(songStructItem);
  }

  public SongPart findSongPartByUUID (final String uuid) {
    for (SongPart next: parts) {
      if (next.getId().equals(uuid))
        return next;
    }

    return null;
  }

  public SongPart getPreviousPart (SongPart songPart) {
    int index = getIndex(songPart);
    return index > 0 ? getSongParts().get(index - 1): null;
  }

  public SongPart getNextPart (SongPart songPart) {
    int index = getIndex(songPart);
    return (index < getSongParts().size() - 1)? getSongParts().get(index + 1): null;
  }

  public SongPart getFirstPart () {
    return parts.get(0);
  }

  public SongPart getLastPart () {
    return parts.get(parts.size() - 1);
  }

  public SongStructItem getPreviousStructItem (SongStructItem songStructItem) {
    int index = getIndex(songStructItem);
    return index > 0 ? getStructItems().get(index - 1): null;
  }

  public SongStructItem getNextStructItem (SongStructItem songStructItem) {
    int index = getIndex(songStructItem);
    return (index < getSongParts().size() - 1)? getStructItems().get(index + 1): null;
  }

  public SongStructItem getFirstStructItem () {
    return structItems.get(0);
  }

  public SongStructItem getLastStructItem () {
    return structItems.get(structItems.size() - 1);
  }


  public List<SongPart> getSongParts() {
    return parts;
  }

  public void setSongParts(List<SongPart> songParts) {
    this.parts = songParts;
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


  @XmlIDREF
  public User getLeadVoice() {
    return leadVoice;
  }

  public void setLeadVoice(User leadVoice) {
    this.leadVoice = leadVoice;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getPreset() {
    return presetProperty.get();
  }

  public SimpleStringProperty presetProperty () {
    return presetProperty;
  }

  public void setPreset(String preset) {
    this.presetProperty.set(preset);
  }

  public Integer getSpeed() {
    return speedProperty.get();
  }

  public void setSpeed (final Integer newSpeed) {
    this.speedProperty.set(newSpeed);
  }

  public SimpleObjectProperty<Integer> speedProperty() {
    if (speedProperty.get() == null)
      speedProperty.set(0);
    return speedProperty;
  }

  public List<SongStructItem> getStructItems() {
    return structItems;
  }

  public void setStructItems(List<SongStructItem> structItems) {
    this.structItems = structItems;
  }

  public SongPart findSongPart (final SongStructItem songStructItem) {
    Collection<String> found = new ArrayList<>();
    for (SongPart next: parts) {
      if (next.getId().equals(songStructItem.getPartId()))
        return next;
      else
        found.add(next.getId() + "\n");
    }
    throw new IllegalStateException("Part with id " + songStructItem.getPartId() + " not found in song " + getId() + "\n(Found ids: " + found + ")");
  }

  public List<SongStructItem> getStructItems (final SongPart songPart) {
    List<SongStructItem> contains = new ArrayList<SongStructItem>();
    for (SongStructItem next: getStructItems()) {
      if (next.getPartId().equals(songPart.getId()))
        contains.add(next);
    }

    return contains;
  }
}
