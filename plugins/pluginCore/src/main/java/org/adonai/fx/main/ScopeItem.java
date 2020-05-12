package org.adonai.fx.main;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import org.adonai.fx.Consts;
import org.adonai.model.Session;
import org.adonai.model.Song;
import org.adonai.model.SongBook;

public class ScopeItem  {

  private String name;

  private Node icon;

  private List<Integer> songs = new ArrayList<Integer>();

  public ScopeItem (Session session) {
    name = session.getName();
    icon = Consts.createIcon("fas-church", Consts.ICON_SIZE_TOOLBAR);
    songs.addAll(session.getSongs());
  }

  public ScopeItem (SongBook songBook) {
    name = "SongBook ";
    icon = Consts.createIcon("fas-book", Consts.ICON_SIZE_TOOLBAR);
    for (Song next: songBook.getSongs()) {
      songs.add(next.getId());
    }
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

  public Node getIcon() {
    return icon;
  }

  public void setIcon(Node icon) {
    this.icon = icon;
  }
}
