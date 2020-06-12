package org.adonai.fx.main;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import org.adonai.fx.Consts;
import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;
import org.adonai.model.WithAdditionals;

public class ScopeItem  {

  private String name;

  private Node icon;

  private List<Integer> songs = new ArrayList<Integer>();

  private final Session session;

  private final SongBook songBook;

  private final Song song;

  private final ScopeItem parentItem;


  private final String id;

  public ScopeItem (ScopeItem parentItem, Song song) {
    name = song.getId() + " - " + song.getName();
    icon = Consts.createIcon("fas-music", Consts.ICON_SIZE_TOOLBAR);
    this.session = null;
    this.songBook = null;
    this.song = song;
    this.parentItem = parentItem;
    this.id = parentItem.getId() + "_" + song.getId();
  }

  public ScopeItem (Session session) {
    name = "Session '" + session.getName() + "'";
    icon = Consts.createIcon("fas-church", Consts.ICON_SIZE_TOOLBAR);
    songs.addAll(session.getSongs());
    this.session = session;
    this.songBook = null;
    this.song = null;
    this.parentItem = null;
    this.id = "session " + session.getName();
  }

  public ScopeItem (SongBook songBook) {
    name = "SongBook ";
    icon = Consts.createIcon("fas-book", Consts.ICON_SIZE_TOOLBAR);
    for (Song next: songBook.getSongs()) {
      songs.add(next.getId());
    }
    this.songBook = songBook;
    this.session = null;
    this.song = null;
    this.parentItem = null;
    this.id = "songbook";
  }

  public StringProperty nameProperty () {
    if (session != null)
      return session.getNameProperty();
    else
      return null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Integer> getSongs() {
    return songs;
  }

  public String getId() {
    return id;
  }


  public Node getIcon() {
    return icon;
  }

  public void setIcon(Node icon) {
    this.icon = icon;
  }

  public List<Song> resolveSongs (final SongBook songBook) {
    List<Song> resolved = new ArrayList<>();
    for (Integer next: songs) {
      resolved.add(songBook.findSong(next));
    }

    return resolved;
  }

  public WithAdditionals getWithAdditionals () {
    if (getSession() != null)
      return getSession();
    else if (getSongBook() != null)
      return songBook;
    else if (getSong() != null)
      return song;
    else
      throw new IllegalStateException("ScopeItem does not contain any additionalsHolder");
  }

  public Session getSession() {
    return session;
  }

  public SongBook getSongBook() {
    return songBook;
  }

  public String toString () {
    return getName();
  }

  public Song getSong() {
    return song;
  }

  public ScopeItem getParentItem() {
    return parentItem;
  }
}
