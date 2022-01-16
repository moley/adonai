package org.adonai.fx.main;

import java.util.ArrayList;
import java.util.List;
import org.adonai.model.Configuration;
import org.adonai.model.Session;
import org.adonai.model.SongBook;

public class ScopeItemProvider {

  public List<ScopeItem> getScopeItems (final Configuration configuration) {

    List<ScopeItem> items = new ArrayList<ScopeItem>();
    for (SongBook next: configuration.getSongBooks()) {
      items.add(new ScopeItem(next));
    }

    for (Session nextSession: configuration.getSessions()) {
      items.add(new ScopeItem(nextSession));
    }

    return items;

  }
}
